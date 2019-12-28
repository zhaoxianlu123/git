package com.example.demo1.dao;

import com.example.demo1.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper1 {
    public List<Student> findAll();
}
