package com.example.jpahibernatedemo.test.dao;

import com.example.jpahibernatedemo.test.entity.MyTeacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyTeacherRepository extends CrudRepository<MyTeacher, Integer> {
    @Query("select m from MyTeacher m where m.id = :id order by m.id DESC")
    List<MyTeacher> findByIdOrderByIdDesc(@Param("id") Integer id);

}