package com.base.elasticsearch.controller;

import com.alibaba.fastjson2.JSON;
import com.base.elasticsearch.model.User;
import com.base.elasticsearch.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/ceshi")
@RestController
@Slf4j
public class TestESController {

    private static String indexName = "source_index";

    @Resource
    private EsService esUtil;

    @GetMapping("/createIndex")
    public Boolean create(@RequestParam("indexName") String indexName) {
        return esUtil.createIndex(indexName);
    }


    @GetMapping("deleteIndex")

    public Boolean deleteIndex(@RequestParam("indexName") String indexName) {
        return esUtil.deleteIndex(indexName);
    }



    @GetMapping("update")
    public String update(@RequestParam("id") String id,@RequestParam("id") String username) {
        User user = new User();
        user.setUserName(username);
        return esUtil.update(indexName, id, JSON.toJSONString(user));
    }

    @GetMapping("delete")
    public String delete(@RequestParam("id") String id) {
        return esUtil.delete(indexName, id);
    }

    @GetMapping("deleteBatch")
    public List<String> deleteBatch(@RequestParam("ids") List<String> ids) {
        return esUtil.deleteBatch(indexName, ids);
    }

    @GetMapping("searchById")
    public String searchById(@RequestParam("id") String id) {
        return esUtil.searchById(indexName, id);
    }


   /* @GetMapping("fromSizePage")
    public P<Map<String, Object>> fromSizePage( ) {
        BasePageForm basePageForm=new BasePageForm();
        basePageForm.setTitle("李");
        basePageForm.setScene("1");
        basePageForm.setPageNum(1);
        basePageForm.setPageSize(10);
        return esUtil.fromSizePage(indexName, basePageForm);
    }
*/

    @GetMapping("sinFieldsAggregateQuery")
    public List<Object> sinFieldsAggregateQuery() {
        // 需要分组的字段，可以随意指定
        List<String> fieldList = new ArrayList<>();
        fieldList.add("userName.keyword");
        TermsAggregationBuilder termsAge = AggregationBuilders.terms(fieldList.get(0)).field(fieldList.get(0));
//                .subAggregation(AggregationBuilders.avg("avg").field(fieldList.get(0)))
//                .subAggregation(AggregationBuilders.sum("sum").field(fieldList.get(0)))
//                .subAggregation(AggregationBuilders.min("min").field(fieldList.get(0)))
//                .subAggregation(AggregationBuilders.count("count").field(fieldList.get(0)))
//                .subAggregation(AggregationBuilders.filters("userName","1").field(fieldList.get(0)));
        return esUtil.aggregateQuery(indexName, fieldList, termsAge);
    }

    @GetMapping("multipleFieldsAggregateQuery")
    public List<Object> multipleFieldsAggregateQuery() {
        // 需要分组的字段，可以随意指定
        List<String> fieldList = new ArrayList<>();
        fieldList.add("age");
        TermsAggregationBuilder termsAge = AggregationBuilders.terms(fieldList.get(0)).field(fieldList.get(0));
        TermsAggregationBuilder termsCreateTime = AggregationBuilders.terms(fieldList.get(1)).field(fieldList.get(1))
                .subAggregation(AggregationBuilders.avg("avg").field(fieldList.get(0)))
                .subAggregation(AggregationBuilders.sum("sum").field(fieldList.get(0)))
                .subAggregation(AggregationBuilders.min("min").field(fieldList.get(0)))
                .subAggregation(AggregationBuilders.count("count").field(fieldList.get(0)))
                .subAggregation(AggregationBuilders.cardinality("cardinality").field(fieldList.get(0)));
        return esUtil.aggregateQuery(indexName, fieldList, termsAge.subAggregation(termsCreateTime));
    }
}