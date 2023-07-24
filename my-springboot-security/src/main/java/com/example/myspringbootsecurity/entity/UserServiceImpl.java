package com.example.myspringbootsecurity.entity;

import com.example.myspringbootsecurity.dao.UserMapper;
import com.example.myspringbootsecurity.dao.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
// 继承UserDetailsService，实现SpringSecurity的UserDetailsService接口，用于封装用户信息
@Slf4j
@Service
public class UserServiceImpl implements UserService<User>, UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据用户名称查询到user用户 
        User userDetails = userMapper.findByUsername(username);
        if (userDetails == null) {
            return null;
        }
        // 2.查询该用户对应的权限 
        List<Permission> permissionList = userMapper.findPermissionByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        permissionList.forEach((a) -> grantedAuthorities.add(new SimpleGrantedAuthority(a.getName())));
        log.info(">>permissionList:{}<<", permissionList);
        // 设置权限 
        userDetails.setAuthorities(grantedAuthorities);
        return userDetails;
    }
}