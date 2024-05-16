package com.example.myspringbootsecurity.dao;

import com.example.myspringbootsecurity.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {

    @Select("select * from permission")
    List<Permission> findAllPermission();

}
