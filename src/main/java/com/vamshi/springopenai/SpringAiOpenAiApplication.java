package com.vamshi.springopenai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringAiOpenAiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAiOpenAiApplication.class, args);
	}
}
