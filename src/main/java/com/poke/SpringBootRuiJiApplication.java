package com.poke;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@SpringBootApplication
@MapperScan("com.poke.mapper")
@EnableTransactionManagement // 开启注解控制
//@ServletComponentScan
public class SpringBootRuiJiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRuiJiApplication.class, args);
        log.info("=====启动成功======");
    }

}
