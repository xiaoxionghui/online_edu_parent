package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName ServiceApplication
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/2 8:46
 * @Version V1.0
 **/
@MapperScan(basePackages = "com.atguigu.edu.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class,args);
    }
}
