package com.sly.demo.redis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author sly
 */
@SpringBootApplication
@MapperScan("com.sly.demo.redis.mapper")
public class DemoRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRedisApplication.class, args);
    }

}
