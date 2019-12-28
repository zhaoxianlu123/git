package com.example.demo1.service.impl;

import com.example.demo1.dao.UserMapper1;
import com.example.demo1.entity.Student;
import com.example.demo1.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private UserMapper1 userMapper1;
    @Override
    public List<Student> findAll() {

        return userMapper1.findAll();
    }
}
