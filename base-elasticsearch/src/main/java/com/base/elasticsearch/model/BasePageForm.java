package com.base.elasticsearch.model;

import lombok.Data;

import java.util.List;

@Data
public class BasePageForm {
    private String scene;
    private String title;
    private String type;
    private List<Long> categoryIds;

    private int pageSize ;

    private int pageNum ;

    private String orderBy;

    private Boolean orderType = false;

    private Object[] sortCursor;

    private boolean isAll = false;
}