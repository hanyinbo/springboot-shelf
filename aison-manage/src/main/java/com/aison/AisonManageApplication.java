package com.aison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AisonManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AisonManageApplication.class, args);
    }

}
