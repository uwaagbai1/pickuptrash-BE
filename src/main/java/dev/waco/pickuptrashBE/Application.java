package dev.waco.pickuptrashBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import dev.waco.pickuptrashBE.config.SecurityConfig;
import dev.waco.pickuptrashBE.config.AppConfig;


@SpringBootApplication
@Import({SecurityConfig.class, AppConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
