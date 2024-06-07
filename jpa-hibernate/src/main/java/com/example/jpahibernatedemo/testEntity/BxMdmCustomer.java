package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

 /**
 * ;
 * @author : http://www.chiner.pro
 * @date : 2024-6-4
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BxMdmCustomer {
    /** 客户id */

    private String fcustId ;
    /** 创建人 */

    private Integer createBy ;
    /** 创建时间 */

    private Date createTime ;
    /** 更新人 */

    private Integer updateBy ;
    /** 更新时间 */

    private Date updateTime ;
    /** 编码 */

    private String fnumber ;
    /** 名称 */

    private String fname ;
    /** 简称 */

    private String fshortName ;
    /** 创建组织编码 */

    private String createOrgidCode ;
    /** 使用组织编码 */

    private String useOrgidCode ;
    /** 国家编码 */

    private String countryCode ;
    /** 发票抬头 */

    private String finvoiceTitle ;
    /** 集团客户 */

    private String flsGroup ;
    /** 默认付款方 */

    private String flsDelPayer ;
    /** 客户类别编码 */

    private String fcustTypeCode ;
    /** 结算币别编码 */

    private String ftradingCurridCode ;
    /** 发票类型 */

    private String finvoiceType ;
    /** 税类别编码 */

    private String ftaxtypeCode ;
    /** 客户优先级 */

    private String fpriority ;
    /** 默认税率编码 */

    private String ftaxRateCode ;
    /** 启用信用管理 */

    private String fiscreditCheckCode ;
    /** 是否交易客户 */

    private String flsTrade ;

}