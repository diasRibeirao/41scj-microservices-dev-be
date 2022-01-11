package br.com.fiap.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApplicationUsuariosService {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationUsuariosService.class, args);
	}

}
