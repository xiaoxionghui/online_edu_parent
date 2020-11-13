package com.atguigu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName EduConfig
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/2 18:14
 * @Version V1.0
 **/
@Configuration
public class EduConfig {
    /**
     * 逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

    /**
     * 分页查询插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
