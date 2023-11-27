package com.codestatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableAsync
@EnableJpaAuditing
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class CodeStateMainProjectApplication {

	@PostConstruct
	public void timeZoneSet() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CodeStateMainProjectApplication.class, args);
	}
}
