package com.example.demo1.dao;

import com.example.demo1.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ProductMapper {


    Product selectByProductNo(@Param("productNo") String productNo);

    int updateTotal(Product record);
}