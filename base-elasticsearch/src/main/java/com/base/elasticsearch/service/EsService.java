package com.base.elasticsearch.service;

import cn.hutool.core.util.StrUtil;
import com.base.elasticsearch.annotation.ESLikeSearch;
import com.base.elasticsearch.model.BasePageForm;
import com.base.elasticsearch.model.ESRequestEntity;
import com.base.elasticsearch.model.P;
import com.base.elasticsearch.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EsService {
    private static final String SEARCH_TITLE="searchTitle";
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     */
    @SneakyThrows
    public Boolean createIndex(String indexName) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // request.settings()  可以设置分片规则
        // request.mapping() 可以设置映射字段
        CreateIndexResponse indexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return indexResponse.isAcknowledged();
    }

    /**
     * 删除索引
     */
    @SneakyThrows
    public Boolean deleteIndex(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) {
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 文档数据插入(插入前会自动生成index)
     * 要在实体类的要进行匹配的字段上使用@ESLikeSearch，并指定id的字段名，
     * 不同类型的实体存储在同一个index的时候，需要指定不同的scene，
     * eg：
     * @ESLikeSearch(scene = Constants.SOURCE_TYPE_KNOWLEDGE,fieldIdName = "knowledgeId")
     * private String title;
     */
    public String insertObject(String indexName, Object t) throws IOException {
        //将传入的对象都转成对应的实体
        ESRequestEntity esRequestEntity = this.initESRequestEntity(t);
        IndexRequest indexRequest = new IndexRequest(indexName);
        // 不设置id 会自动使用es默认的
        if (StrUtil.isNotBlank(esRequestEntity.getServiceId())) {
            indexRequest.id(esRequestEntity.getScene()+esRequestEntity.getServiceId());
        }
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(esRequestEntity);
        indexRequest.source(userJson, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }


    /**
     * 更新- es有则更新，无则写入
     * 可以接受 String、Map、XContentBuilder 或 Object 键对
     */
    @SneakyThrows
    public String update(String indexName, String id, String jsonStr) {
        String searchById = searchById(indexName, id);
        if (StrUtil.isBlank(searchById)) {
            return null;
        }
        UpdateRequest updateRequest = new UpdateRequest(indexName, id).doc(jsonStr, XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return update.getId();
    }

    /**
     * 根据id进行删除
     */
    @SneakyThrows
    public String delete(String indexName, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return delete.getId();
    }

    /**
     * 根据id进行批量删除
     */
    @SneakyThrows
    public List<String> deleteBatch(String indexName, List<String> ids) {
        List<String> deleteList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        for (String id : ids) {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            deleteList.add(restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT).getId());
        }
        return deleteList;
    }

    /**
     * 根据id进行查询
     */
    @SneakyThrows
    public String searchById(String indexName, String id) {
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }


    /**
     * 1, id查询: QueryBuilders.idsQuery().ids(id)
     * 精确查询 QueryBuilders.termQuery("userName.keyword", "王五")  .keyword 值是中文时需要，非中文时不需要
     * 范围查询：QueryBuilders.rangeQuery().form().to().includeLower(false).includeUpper(false) 默认是true包含头尾，设置false去掉头尾
     * 匹配所有：QueryBuilders.matchAllQuery()
     * 模糊查询：QueryBuilders.fuzzyQuery()
     * 全文检索，会进行分词,多个字段检索：QueryBuilders.multiMatchQuery("kimchy", "name", "description") 查询name或description包含kimchy
     * 全文检索，会进行分词,单字段检索：QueryBuilders.matchQuery(name", "kimchy") 查询name包含kimchy
     * 通配符查询, 支持*，匹配任何字符序列, 包括空，避免* 开始 QueryBuilders.wildcardQuery("user", "ki*hy")
     * 跨度查询：QueryBuilders.span………
     * 2，组合查询:BoolQueryBuilder  must：and  mustNot:not  should:or  in：termsQuery传list
     * QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("name", Lists.newArrayList())).mustNot(QueryBuilders.……);
     * 过滤器查询：在原本查询结果的基础上对数据进行筛选，不会计算分值,所以效率比must更高
     * QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("userName", "王五"))
     * 3, 查询部分字段: SearchSourceBuilder().fetchSource(new String[]{"userName", "age"}, new String[]{}) 查询userName和age字段
     * 4, 权重计算，权重越高月靠前:  给name精确查询提高权重为2 QueryBuilders.termQuery("name", "kimchy").boost(2.0f)
     * 高于设定分数, 不计算相关性查询: QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "kimchy")).boost(2.0f);
     * 5，Nested&Join父子类型：得检索效率慢，不建议在ES做Join操作
     * 父子查询：QueryBuilders.hasChildQuery("tweet", QueryBuilders.termQuery("user", "kimchy")).scoreMode("max")
     * 嵌套查询, 内嵌文档查询 QueryBuilders.nestedQuery("location", QueryBuilders.boolQuery()
     * .must(QueryBuilders.matchQuery("location.lat", 0.962590433140581))
     * .must(QueryBuilders.rangeQuery("location.lon").lt(36.0000).gt(0.000))).scoreMode("total")
     * 6, 排序：在查询的结果上进行二次排序，date、float 等类型添加排序，text类型的字段不允许排序 SearchSourceBuilder.sort()
     */


    /**
     * 1，from-size浅分页适合数据量不大的情况（官网推荐是数据少于10000条），可以跳码进行查询
     * 2，scroll 是一种滚屏形式的分页检索，scroll查询是很耗性能的方式，不建议在实时查询中运用
     */
    @SneakyThrows
    public P<Map<String, Object>> fromSizePage(String indexName, BasePageForm basePageForm) {
        int from = basePageForm.getPageSize() * (basePageForm.getPageNum() - 1);
        BoolQueryBuilder queryBuilder =new BoolQueryBuilder();
        if(StringUtils.isNotEmpty(basePageForm.getScene())){
            queryBuilder.filter(QueryBuilders.termQuery("scene", basePageForm.getScene()));
        }
        if(StringUtils.isNotEmpty(basePageForm.getType())){
            queryBuilder.filter(QueryBuilders.termQuery("data.type", basePageForm.getType()));
        }

        if(CollectionUtils.isNotEmpty(basePageForm.getCategoryIds())){
            BoolQueryBuilder shouldBuilder=QueryBuilders.boolQuery().should(QueryBuilders.termsQuery("data.categoryId",basePageForm.getCategoryIds()));
            queryBuilder.must(shouldBuilder);
        }

        if(StringUtils.isNotEmpty(basePageForm.getTitle())){
            queryBuilder.must(QueryBuilders.matchQuery(SEARCH_TITLE,basePageForm.getTitle()));
        }


        // 构建分页搜寻器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder)
                .from(from)
                .size(basePageForm.getPageSize());
//        if (StrUtil.isNotBlank(basePageForm.getOrderBy())) {
            sourceBuilder.sort("data.updateTime.keyword", basePageForm.getOrderType() ? SortOrder.ASC : SortOrder.DESC);
//        }

        //高亮显示设置
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field(SEARCH_TITLE);
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        sourceBuilder.highlighter(highlightBuilder);

        SearchRequest request = new SearchRequest(indexName).source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return handleResponse(response, basePageForm.getPageNum(), basePageForm.getPageSize());
    }

    /**
     * 使用分组函数统一查询所有资源数据
     */
    @SneakyThrows
    public Map<String, Object> findAllScene(String indexName, BasePageForm basePageForm) {
        int from = basePageForm.getPageSize() * (basePageForm.getPageNum() - 1);
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(SEARCH_TITLE, basePageForm.getTitle());
        // 构建分页搜寻器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder)
                .from(from)
                .size(basePageForm.getPageSize());
        if (StrUtil.isNotBlank(basePageForm.getOrderBy())) {
            sourceBuilder.sort(basePageForm.getOrderBy(), basePageForm.getOrderType() ? SortOrder.ASC : SortOrder.DESC);
        }
        TopHitsAggregationBuilder topHits = AggregationBuilders.topHits("topHits");
        topHits.from(from);
        topHits.size(basePageForm.getPageSize());
        TermsAggregationBuilder terms = AggregationBuilders.terms("scene.keyword").field("scene.keyword");
        terms.subAggregation(topHits);
        sourceBuilder.aggregation(terms);
        SearchRequest request = new SearchRequest(indexName).source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        Map<String, Object> objects = handleTopHitResult(response);
        return handleTopHitResult(response);
    }
    /**
     * 分页返回值处理
     */
    @SneakyThrows
    private P<Map<String, Object>> handleResponse(SearchResponse response, int pageNum, int pageSize) {
        SearchHit[] hits = response.getHits().getHits();
        List<Map<String, Object>> result = Arrays.stream(hits).map(h -> {
            Map<String, Object> sourceAsMap = h.getSourceAsMap();
            sourceAsMap.put("id", h.getId());
            if(h.getHighlightFields()!=null && h.getHighlightFields().get(SEARCH_TITLE)!=null){//处理高亮的返回值
                sourceAsMap.put(SEARCH_TITLE,h.getHighlightFields().get(SEARCH_TITLE).fragments()[0].string());
            }
            return sourceAsMap;
        }).collect(Collectors.toList());
        return new P<>(result, response.getHits().getTotalHits().value, pageSize, pageNum);
    }

    /**
     * search_after 适用于深度分页+ 排序，分页是根据上一页最后一条数据来定位下一页的位置，所以无法跳页请求，性能最好
     */
    @SneakyThrows
    public P<Map<String, Object>> searchAfterPage(String indexName, QueryBuilder queryBuilder, BasePageForm basePageForm) {
        // 构建分页搜寻器
        // searchAfter需要将from设置为0或-1，当然也可以不写

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder)
                .from(0)
                .size(basePageForm.getPageSize());
        if (StrUtil.isNotBlank(basePageForm.getOrderBy())) {
            sourceBuilder.sort(basePageForm.getOrderBy(), basePageForm.getOrderType() ? SortOrder.ASC : SortOrder.DESC);
        }
        if (null != basePageForm.getSortCursor() && basePageForm.getSortCursor().length > 0) {
            sourceBuilder.searchAfter(basePageForm.getSortCursor());
        }
        SearchRequest request = new SearchRequest(indexName).source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        Object[] sortCursor = hits[hits.length - 1].getSortValues();
        P<Map<String, Object>> page = handleResponse(response, basePageForm.getPageNum(), basePageForm.getPageSize());
        page.setSortCursor(sortCursor);
        return page;
    }



    /**
     * 聚合查询: todo 字段类型是text就不支持聚合排序
     * 桶（bucket）: 满足特定条件的文档的集合  GROUP BY userName
     * 指标（metric）: 对桶内的文档进行聚合分析的操作 COUNT(userName)
     * select age, createTime, SUM(age), AVG(age),MIN(age),COUNT(age) from user_index GROUP BY age, createTime
     */
    @SneakyThrows
    public List<Object> aggregateQuery(String indexName, List<String> fieldList, TermsAggregationBuilder aggregation) {
        if (CollectionUtils.isEmpty(fieldList)) {
            return Lists.newArrayList();
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().aggregation(aggregation);
        SearchRequest request = new SearchRequest(indexName).source(searchSourceBuilder);
        request.allowPartialSearchResults(true);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return handleResult(response);
    }

    /**
     * 对聚合结果进行封装
     */
    private static List<Object> handleResult(SearchResponse agg) {
        Map<String, Aggregation> aggregations = agg.getAggregations().asMap();
        List<Object> objects = Lists.newArrayList();
        // 第一层分组统计
        aggregations.forEach((k, v) -> {
            Map<String, Object> group = new HashMap<>();
            parseAggs(k, v, group, objects);
        });
        return objects;
    }

    private static Map<String, Object> handleTopHitResult(SearchResponse agg) {
        List<Aggregation> aggregations = agg.getAggregations().asList();
        Map<String, Object> map=new HashMap<>();
        for (Aggregation aggregation : aggregations) {
            if (aggregation instanceof Terms) {
                for (Terms.Bucket bucket : ((Terms) aggregation).getBuckets()) {
                    Aggregations aggregations1 = bucket.getAggregations();
                    for (Aggregation aggregation1 : aggregations1) {
                        if (aggregation1 instanceof TopHits) {
                            SearchHit[] hits = ((TopHits) aggregation1).getHits().getHits();
                            List<Map<String, Object>> result = Arrays.stream(hits).map(h -> {
                                Map<String, Object> sourceAsMap = h.getSourceAsMap();
                                sourceAsMap.put("id", h.getId());
                                return sourceAsMap;
                            }).collect(Collectors.toList());
                            map.put(bucket.getKey().toString(),result);
                        }
                    }
                }
            }
        }

        return map;
    }

    /**
     * 解析聚合结果
     */
    private static void parseAggs(String key, Aggregation value, Map<String, Object> group, List<Object> objects) {
        if (value instanceof Terms) {
            for (Terms.Bucket bucket : ((Terms) value).getBuckets()) {
                Set<Map.Entry<String, Aggregation>> entries = bucket.getAggregations().asMap().entrySet();
                group.put(key, bucket.getKeyAsString());
                for (Map.Entry<String, Aggregation> entry : entries) {
                    if (entry.getValue() instanceof Terms) {
                        parseAggs(entry.getKey(), entry.getValue(), group, objects);
                    } else {
                        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                        bucket.getAggregations().asMap().forEach((k2, v2) -> map.put(k2, getValue(v2)));
                        map.putAll(group);
                        objects.add(map);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 取值
     */
    private static String getValue(Aggregation agg) {
        switch (agg.getType()) {
            case "avg":
                return String.valueOf(((Avg) agg).getValue());
            case "sum":
                return String.valueOf(((Sum) agg).getValue());
            case "value_count":
                return String.valueOf(((ValueCount) agg).getValue());
            case "min":
                return String.valueOf(((Min) agg).getValue());
            case "max":
                return String.valueOf(((Max) agg).getValue());
            case "cardinality":
                return String.valueOf(((Cardinality) agg).getValue());
            default:
                return String.valueOf(agg);
        }
    }

    private ESRequestEntity initESRequestEntity(Object obj) {
        Class<?> objClass = obj.getClass();
        ESRequestEntity esRequestEntity = new ESRequestEntity();
        esRequestEntity.setData(obj);
        esRequestEntity.setClassName(objClass.getName());

        //处理ESLikeSearch注解的参数
        Field[] declaredFields = objClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(ESLikeSearch.class)) {
                declaredField.setAccessible(true);
                try {
                    Object title = declaredField.get(obj);
                    if (title != null) {
                        esRequestEntity.setSearchTitle(title.toString());
                    }
                    //获取注解对象的主键值
                    ESLikeSearch annotation = declaredField.getAnnotation(ESLikeSearch.class);
                    String scene = annotation.scene();
                    esRequestEntity.setScene(scene);
                    String idName = annotation.fieldIdName();
                    if (StringUtils.isEmpty(idName)) {//没设置id，用es生成的主键
                        return esRequestEntity;
                    }
                    //获取主键的值
                    String firstChar = idName.substring(0, 1);
                    Method declaredMethod = objClass.getDeclaredMethod("get" + idName.replaceFirst(firstChar, firstChar.toUpperCase()), null);
                    Object idValue = declaredMethod.invoke(obj, null);
                    esRequestEntity.setServiceId(idValue == null ? "" : idValue.toString());
                } catch (Exception e) {

                }
            }
        }
        return esRequestEntity;
    }
}
