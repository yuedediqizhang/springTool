package com.example.jpahibernatedemo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class BxMdmDeptRequest {


    @JsonProperty("data")
    private DataDTO data = new DataDTO();

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("dataInfo")
        private List<DataInfoDTO> dataInfo = new ArrayList<>();
        @JsonProperty("puuId")
        private String puuId;

        @NoArgsConstructor
        @Data
        public static class DataInfoDTO {
            @JsonProperty("deptId")
            private String deptId;
            @JsonProperty("parentId")
            private String parentId;
            @JsonProperty("ancestors")
            private String ancestors;
            @JsonProperty("orderNum")
            private String orderNum;
            @JsonProperty("deptCode")
            private String deptCode;
            @JsonProperty("grade")
            private String grade;
            @JsonProperty("deptName")
            private String deptName;
            @JsonProperty("managerId")
            private String managerId;
            @JsonProperty("managerName")
            private String managerName;
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("email")
            private String email;
            @JsonProperty("address")
            private String address;
            @JsonProperty("description")
            private String description;
            @JsonProperty("status")
            private String status;
            @JsonProperty("delFlag")
            private String delFlag;
            @JsonProperty("version")
            private String version;
            @JsonProperty("uuid")
            private String uuid;
        }
    }
}

