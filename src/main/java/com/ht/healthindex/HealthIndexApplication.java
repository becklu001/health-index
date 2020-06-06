package com.ht.healthindex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ht.healthindex.dao")
@EnableScheduling
public class HealthIndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthIndexApplication.class, args);
	}

}
