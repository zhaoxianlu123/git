package com.example.demo1.controller;

import com.example.demo1.entity.Student;
import com.example.demo1.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class StudentController {


    @Autowired
    RedisConfig redisConfig;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IStudentService studentService;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @RequestMapping("/springboot")
    @ResponseBody
    public List<Student> findAll() {
        List<Student> all = studentService.findAll();
        return all;
    }

    @RequestMapping("/redis")
    public String testRedis() {
        //StringRedisTemplate redisTemplate = (StringRedisTemplate) redisConfig.getRedisTemplate();
        //ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set("name", "forezp");
        System.out.println(stringValueOperations.get("name"));
        return "redis------->  存储获取成功";
    }

    @RequestMapping("/sendMail")
    public void sendTxtMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(new String[]{"alibabachina@126.com"});
        simpleMailMessage.setFrom("alibabachina@126.com");
        simpleMailMessage.setSubject("Spring Boot Mail 邮件测试【文本】");
        simpleMailMessage.setText("这里是一段简单文本。");
        // 发送邮件
        mailSender.send(simpleMailMessage);

        System.out.println("邮件已发送");
    }

}
