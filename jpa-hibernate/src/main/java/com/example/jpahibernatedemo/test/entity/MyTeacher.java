package com.example.jpahibernatedemo.test.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "my_teacher")
public class MyTeacher {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(mappedBy = "myTeachers", fetch = FetchType.LAZY)
    private Set<MyClass> myClasses = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "myTeachers", fetch = FetchType.LAZY)
    private Set<MyStudent> myStudents = new LinkedHashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}