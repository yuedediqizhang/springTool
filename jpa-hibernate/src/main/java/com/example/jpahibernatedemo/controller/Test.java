package com.example.jpahibernatedemo.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.jpahibernatedemo.HttpMd5PostUtils;
import com.example.jpahibernatedemo.testEntity.BxMdmDeptModel;
import com.example.jpahibernatedemo.testEntity.BxMdmUserModel;
import com.example.jpahibernatedemo.testEntity.ReceiveReqModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class Test {



    String deptUrl = "http://localhost/dev-api/api/mdm/receiveMdmDept";
    String userUrl = "http://localhost/dev-api/api/mdm/receiveMdmUser";

    String appKey = "9152623563356334";
    String appSecret = "sPTrAYwBTRTfEzwyBskQSsBE";



    @GetMapping("/testDept")
    public HttpMd5PostUtils.Result testDept() {
        ReceiveReqModel<BxMdmDeptModel> receiveReqModel = new ReceiveReqModel<>();
        receiveReqModel.setPuuId(UUID.randomUUID().toString());
        receiveReqModel.getDataInfo().add(JSONObject.parseObject(dj, BxMdmDeptModel.class));
        String jsonString = JSONObject.toJSONString(receiveReqModel);
        return HttpMd5PostUtils.doPost(deptUrl, jsonString, appKey, appSecret);
    }


    @GetMapping("/testUser")
    public HttpMd5PostUtils.Result testUser() {
        ReceiveReqModel<BxMdmUserModel> receiveReqModel = new ReceiveReqModel<>();
        receiveReqModel.setPuuId(UUID.randomUUID().toString());
        receiveReqModel.getDataInfo().add(JSONObject.parseObject(uj, BxMdmUserModel.class));
        String jsonString = JSONObject.toJSONString(receiveReqModel);
        return HttpMd5PostUtils.doPost(userUrl, jsonString, appKey, appSecret);
    }

    String dj = "{\n" +
            "  \"deptId\": null,\n" +
            "  \"deptCode\": 9999,\n" +
            "  \"parentId\": 0,\n" +
            "  \"grade\": null,\n" +
            "  \"deptName\": \"測試\",\n" +
            "  \"orderNum\": 0,\n" +
            "  \"managerId\": \"1111111\",\n" +
            "  \"managerName\": \"測試人員\",\n" +
            "  \"phone\": \"15754087395\",\n" +
            "  \"email\": \"111@qq.com\",\n" +
            "  \"address\": \"ceshi \",\n" +
            "  \"description\": \"8888888 \",\n" +
            "  \"delFlag\": null,\n" +
            "  \"createBy\": null,\n" +
            "  \"createTime\": null,\n" +
            "  \"updateBy\": null,\n" +
            "  \"updateTime\": null,\n" +
            "  \"version\": null,\n" +
            "  \"status\": \"0\"\n" +
            "}";


    String uj = "{\n" +
            "  \"userId\": null,\n" +
            "  \"userNo\": \"9999999\",\n" +
            "  \"deptId\": null,\n" +
            "  \"deptName\": 8888888,\n" +
            "  \"deptSalary\": \"不知道\",\n" +
            "  \"currentDept\": \"不知道\",\n" +
            "  \"userName\": \"测试接收用户\",\n" +
            "  \"oldUserName\": \"小王\",\n" +
            "  \"uuid\": \""+UUID.randomUUID()+"\",\n" +
            "  \"cardNo\": \"152316190304136607\",\n" +
            "  \"email\": \"999999@qq\\\\.com\",\n" +
            "  \"nationality\": \"哈萨克斯坦族\",\n" +
            "  \"sectorCompany\": \"不知道\",\n" +
            "  \"unitName\": \"你猜\",\n" +
            "  \"phonenumber\": \"18940235147\",\n" +
            "  \"homePhone\": \"6666666\",\n" +
            "  \"homeAddress\": \"1111111111111111111111111111111111111111111\",\n" +
            "  \"birthday\": \"2022-06-01\",\n" +
            "  \"nativePlace\": \"沈阳\",\n" +
            "  \"residenceType\": \"非农\",\n" +
            "  \"residenceAddress\": \"11111111111111111111111111111111111111111\",\n" +
            "  \"postTime\": \"2024-06-01\",\n" +
            "  \"type\": \"测试类别\",\n" +
            "  \"politicalLandscape\": \"民进党员\",\n" +
            "  \"currentWorkTime\": \"2024-06-01\",\n" +
            "  \"firstWorkTime\": \"2000-06-01\",\n" +
            "  \"highestQualification\": \"你猜\",\n" +
            "  \"companyAddress\": null,\n" +
            "  \"partyDate\": \"2024-04-01\",\n" +
            "  \"firstLevelDept\": \"采供部\",\n" +
            "  \"secondLevelDept\": \"不知道\",\n" +
            "  \"emergencyContactName\": \"谁\",\n" +
            "  \"emergencyContactPhone\": \"11111111\",\n" +
            "  \"maritalStatus\": \"1\",\n" +
            "  \"identity\": \"群众\",\n" +
            "  \"age\": \"16\",\n" +
            "  \"sex\": \"0\",\n" +
            "  \"avatar\": null,\n" +
            "  \"password\": null,\n" +
            "  \"status\": null,\n" +
            "  \"loginIp\": null,\n" +
            "  \"loginDate\": null,\n" +
            "  \"delFlag\": null,\n" +
            "  \"createBy\": null,\n" +
            "  \"createTime\": null,\n" +
            "  \"updateBy\": null,\n" +
            "  \"updateTime\": null,\n" +
            "  \"remark\": null,\n" +
            "  \"version\": null,\n" +
            "  \"educationModels\": [\n" +
            "    {\n" +
            "      \"school\": \"佛罗里达不养闲人大学\",\n" +
            "      \"specialty\": \"搞笑\",\n" +
            "      \"startDate\": \"2024-06-01\",\n" +
            "      \"endDate\": \"2024-06-01\",\n" +
            "      \"qualification\": \"无\",\n" +
            "      \"degree\": \"无\",\n" +
            "      \"highestQualification\": \"Y\",\n" +
            "      \"highestDegree\": \"Y\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"workRecordModels\": [\n" +
            "    {\n" +
            "      \"unit\": \"佛罗里达搞事部\",\n" +
            "      \"post\": \"沙雕队长\",\n" +
            "      \"startDate\": \"2024-06-01\",\n" +
            "      \"endDate\": \"2024-06-01\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"familyMemeberModels\": [\n" +
            "    {\n" +
            "      \"name\": \"你是谁\",\n" +
            "      \"relation\": \"2\",\n" +
            "      \"politicalLandscape\": \"无\",\n" +
            "      \"unit\": \"佛罗里达搞事公司\",\n" +
            "      \"occupation\": \"无\",\n" +
            "      \"post\": \"无\",\n" +
            "      \"phone\": \"66666666\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"postModels\": [\n" +
            "    {\n" +
            "      \"name\": \"无\",\n" +
            "      \"sequence\": \"1111333111\",\n" +
            "      \"subsequence\": \"1111333111\",\n" +
            "      \"auxiliarySequence\": \"111333111\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"contractModels\": [\n" +
            "    {\n" +
            "      \"mainUnit\": \"用人合同\",\n" +
            "      \"termType\": \"1\",\n" +
            "      \"startDate\": \"2024-06-01\",\n" +
            "      \"endDate\": \"2024-06-01\",\n" +
            "      \"type\": \"1\",\n" +
            "      \"userType\": \"6666666666666\",\n" +
            "      \"contractCreateTime\": \"2024-06-01\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"rankModels\": [\n" +
            "    {\n" +
            "      \"company\": \"你猜\",\n" +
            "      \"deptName\": \"佛罗里达不养闲人部门\",\n" +
            "      \"rankLevel\": \"管事\",\n" +
            "      \"treatmentLevel\": \"顶级\",\n" +
            "      \"startDate\": \"2024-06-01\",\n" +
            "      \"endDate\": \"2024-06-01\",\n" +
            "      \"sidelineStartDate\": \"2024-06-01\",\n" +
            "      \"headquartersRankLevel\": \"搞事达人\",\n" +
            "      \"sectorCompanyLevel\": \"无\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"certificateModels\": [\n" +
            "    {\n" +
            "      \"soqCertificate\": \"耗子尾汁\",\n" +
            "      \"soqValidityPeriod\": \"66666666\",\n" +
            "      \"soqStartDate\": \"2024-06-01\",\n" +
            "      \"soqEndDate\": \"2024-06-14\",\n" +
            "      \"soqAssessDate\": \"2024-06-07\",\n" +
            "      \"ptqCertificate\": \"年轻人不讲武德\",\n" +
            "      \"ptqLevel\": \"大佐\",\n" +
            "      \"ptqAssessDate\": \"2024-06-01\",\n" +
            "      \"ptqGainDate\": \"2024-06-01\",\n" +
            "      \"technicalTitle\": \"6666666\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";





}
