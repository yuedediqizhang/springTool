package com.example.jpahibernatedemo.test.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "my_class")
public class MyClass {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "`describe`")
    private String describe;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<MyTeacher> myTeachers = new LinkedHashSet<>();


    @OneToMany(mappedBy = "myClass", fetch = FetchType.LAZY)
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}