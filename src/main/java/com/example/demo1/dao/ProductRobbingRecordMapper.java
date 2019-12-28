package com.example.demo1.dao;


import com.example.demo1.entity.ProductRobbingRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductRobbingRecordMapper {

    int insertSelective(ProductRobbingRecord record);

}