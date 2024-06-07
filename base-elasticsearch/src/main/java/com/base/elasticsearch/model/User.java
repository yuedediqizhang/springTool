package com.base.elasticsearch.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class User {

    public User(){
    }
    private static final long serialVersionUID = -1479160581341748052L;

    /*ik_smart 粗精度，ik_max_word 细精度*/
    private String userName;

    private Integer age;

    private String testNumber;

    private String id;

    private String email;


    private long updateTime;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthDay;


    private long createTime;
    public  User(long  createTime,String userName,int age){
        this.createTime=createTime;
        this.userName=userName;
        this.age=age;
    }

}
