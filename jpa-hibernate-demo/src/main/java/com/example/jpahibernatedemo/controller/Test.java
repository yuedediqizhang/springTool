package com.example.jpahibernatedemo.controller;

import com.example.jpahibernatedemo.test.dao.MyClassRepository;
import com.example.jpahibernatedemo.test.dao.MyStudentRepository;
import com.example.jpahibernatedemo.test.dao.MyTeacherRepository;
import com.example.jpahibernatedemo.test.entity.MyStudent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class Test {

    @Resource(name = "myClassRepository", type = MyClassRepository.class)
    MyClassRepository myClassRepository;

    @Resource(name = "myStudentRepository", type = MyStudentRepository.class)
    MyStudentRepository myStudentRepository;

    @Resource(name = "myTeacherRepository", type = MyTeacherRepository.class)
    MyTeacherRepository myTeacherRepository;

    @GetMapping("/addStudent")
    public void addStudent() {
        MyStudent myStudent = new MyStudent();
        myStudent.setName("张三");
        myStudent.setAge(1);
        myStudentRepository.save(myStudent);
    }


    @GetMapping("/findStudent")
    public void findStudent() {
        MyStudent myStudent = myStudentRepository.findById(1).orElse(new MyStudent());
        System.out.println(myStudent);
    }


    @GetMapping("/findStudentByPage")
    public void findStudentByPage() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        List<MyStudent> myStudents = myStudentRepository.findByNameOrderByIdDesc("张三", firstPageWithTwoElements);
        System.out.println(myStudents.get(0));
    }
}
