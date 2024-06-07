package com.example.jpahibernatedemo.controller;

import com.example.jpahibernatedemo.testEntity.BxMdmCustomer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @GetMapping("/customer")
    public List<BxMdmCustomer> customer() {
        BxMdmCustomer bxMdmCustomer = new BxMdmCustomer();
        bxMdmCustomer
                .setFcustId("客户id")
                .setFname("名称")
                .setFnumber("编码")
                .setFshortName("简称")
                .setCreateOrgidCode("创建组织编码")
                .setUseOrgidCode("使用组织编码")
                .setCountryCode("城市编码")
                .setFinvoiceTitle("发票抬头")
                .setFlsGroup("集团客户")
                .setFlsDelPayer("默认付款方")
                .setFcustTypeCode("客户类型编码")
                .setFtradingCurridCode("结算币别编码")
                .setFinvoiceType("发票类型")
                .setFtaxtypeCode("税类别编码")
                .setFpriority("客户优先级")
                .setFtaxRateCode("默认税率编码")
                .setFiscreditCheckCode("启用信用管理")
                .setFlsTrade("是否交易客户");
        return Collections.singletonList(bxMdmCustomer);
    }

}
