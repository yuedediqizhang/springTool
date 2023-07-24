package com.example.myspringbootsecurity.entity;

import lombok.*;

@Data
public class Permission {

    private Long id;
    private String url;
    private String name;
    private String description;
    private Long pid;

}