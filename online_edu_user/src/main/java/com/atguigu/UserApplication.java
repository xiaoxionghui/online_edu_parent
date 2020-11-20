package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName VideoApplication
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/11 18:54
 * @Version V1.0
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.atguigu.edu.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }


}
