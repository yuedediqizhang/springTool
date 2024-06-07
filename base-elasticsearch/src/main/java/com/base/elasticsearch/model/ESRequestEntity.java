package com.base.elasticsearch.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class ESRequestEntity implements Serializable {
    //业务主键的值，最终es中的主键为 scene 拼接serviceId
    private String serviceId;
    //用于模糊检索的字段
    private String searchTitle;
    //场景
    private String scene;
    //整个对象
    private Object data;

    private String className;
}
