package com.skillplay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkillplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillplayApplication.class, args);
	}

}
