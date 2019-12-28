package com.example.demo1.entity;


import java.io.Serializable;

public class Student implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Integer score;
    private String class1;
    private String school;


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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Student(){}
    public Student(Integer id,String name,Integer age,Integer score,String class1,String school){
        this.id=id;
        this.name=name;
        this.age=age;
        this.score=score;
        this.class1=class1;
        this.school=school;
    }
}
