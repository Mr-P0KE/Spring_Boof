package com.poke;

import com.poke.Controller.SetmealController;
import com.poke.mapper.UserMapper;
import com.poke.service.AddressBookService;
import com.poke.service.DishService;
import com.poke.service.impl.DishServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@SpringBootApplication
@MapperScan("com.poke.mapper")
@EnableTransactionManagement // ��������ע�����
@EnableCaching  //��������ע������
//@ServletComponentScan
public class SpringBootRuiJiApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootRuiJiApplication.class, args);

        log.info("=====�����ɹ�======");
//        context.close();
    }

}
