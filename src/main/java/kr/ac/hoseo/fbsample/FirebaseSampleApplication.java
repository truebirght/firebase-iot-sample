package kr.ac.hoseo.fbsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class FirebaseSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebaseSampleApplication.class, args);
	}
}