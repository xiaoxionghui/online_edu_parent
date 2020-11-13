package com.atguigu;

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
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class,args);
    }


}
