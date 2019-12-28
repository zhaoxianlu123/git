package com.example.demo1.service;


import com.example.demo1.dao.ProductMapper;
import com.example.demo1.dao.ProductRobbingRecordMapper;
import com.example.demo1.entity.Product;
import com.example.demo1.entity.ProductRobbingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/8/25.
 */

public interface IConcurrencyService {



    /**
     * 处理抢单
     * @param mobile
     */
    public void manageRobbing(String mobile);
}

















