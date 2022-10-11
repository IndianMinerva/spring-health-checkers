package de.techtask.saucelabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("de.techtask.saucelabs.model")
public class SchedulingTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingTasksApplication.class);
	}
}
