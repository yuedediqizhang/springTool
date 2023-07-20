package com.example.jpahibernatedemo.test.dao;

import com.example.jpahibernatedemo.test.entity.MyStudent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyStudentRepository extends PagingAndSortingRepository<MyStudent, Integer> {

    @Query("select m from MyStudent m where m.name = :name order by m.id DESC")
    List<MyStudent> findByNameOrderByIdDesc(@Param("name") String name, Pageable pageable);



    @Query("select count(distinct m) from MyStudent m where m.name = :name")
    long countDistinctByName(@Param("name") String name);





}