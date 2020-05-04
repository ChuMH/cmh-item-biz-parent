package com.cmh.item.biz.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


/**
 * 作者：初明昊
 * 时间：2019/12/8 14:51
 * 描述：启动类
 */
@SpringBootApplication
@ComponentScan("com.cmh.item.biz")
@EnableNeo4jRepositories
@MapperScan("com.cmh.item.biz.dao.db.mapper")
//@EnableElasticsearchRepositories(basePackages = "com.cmh")
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
