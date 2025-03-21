package com.ll.spaceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpaceServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpaceServiceApplication.class, args);
  }

}
