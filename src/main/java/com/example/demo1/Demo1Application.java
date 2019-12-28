package com.example.demo1;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaServer
@EnableHystrix
@ComponentScan(basePackages = {"com.example"})
@EnableZuulProxy
public class Demo1Application {

    public static void main(String[] args) {
        //SpringApplication.run(Demo1Application.class, args);
        new SpringApplicationBuilder(Demo1Application.class).web(true).run(args);
    }

}
