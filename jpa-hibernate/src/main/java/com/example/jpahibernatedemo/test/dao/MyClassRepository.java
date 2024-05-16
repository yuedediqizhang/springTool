package com.example.jpahibernatedemo.test.dao;

import com.example.jpahibernatedemo.test.entity.MyClass;
import org.springframework.data.repository.CrudRepository;


public interface MyClassRepository extends CrudRepository<MyClass, Integer> {


}