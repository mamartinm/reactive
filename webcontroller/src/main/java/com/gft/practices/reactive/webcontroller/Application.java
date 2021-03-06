package com.gft.practices.reactive.webcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableWebFlux
@ComponentScan(basePackages = "com.gft.practices.reactive")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
