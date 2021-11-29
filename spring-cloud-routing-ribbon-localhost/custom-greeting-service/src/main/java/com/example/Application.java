package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.example.configs.RibbonConfig;

@SpringBootApplication
@RibbonClient(name = "ribbon-client", configuration = RibbonConfig.class)
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
