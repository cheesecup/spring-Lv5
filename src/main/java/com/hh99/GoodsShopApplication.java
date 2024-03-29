package com.hh99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GoodsShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsShopApplication.class, args);
    }

}
