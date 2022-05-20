package com.aison;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication
@MapperScan("com.aison.mapper")
public class AisonManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AisonManageApplication.class, args);
    }

}
