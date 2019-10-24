package com.lvmen.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 管理端启动类
 * Created by lvmen on 2019/10/24
 */
@SpringBootApplication
@EntityScan("com.lvmen.entity")
public class ManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class);
    }

}
