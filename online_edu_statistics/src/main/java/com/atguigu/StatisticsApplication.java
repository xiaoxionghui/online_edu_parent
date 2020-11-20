package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName VideoApplication
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/11 18:54
 * @Version V1.0
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.atguigu.edu.mapper")
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }


}
