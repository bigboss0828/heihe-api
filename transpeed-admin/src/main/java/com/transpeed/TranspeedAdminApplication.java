package com.transpeed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author hanshuai
 * @title: TranspeedAdminApplication
 * @description: 启动类
 * @date 2023/5/25 15:03
 */
@EnableAspectJAutoProxy
@MapperScan(basePackages = {
        "com.transpeed.system.mapper",
        "com.transpeed.park.mapper",
        "com.transpeed.personnel.mapper",
        "com.transpeed.car.mapper",
        "com.transpeed.record.mapper",
})
@SpringBootApplication
public class TranspeedAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(TranspeedAdminApplication.class, args);
    }
}
