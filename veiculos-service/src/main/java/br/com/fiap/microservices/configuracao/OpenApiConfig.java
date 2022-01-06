package br.com.fiap.microservices.configuracao;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info = 
@Info(title = "API de Serviço Veículos",
	version = "v1",
	description = "Documentação das API's de Serviço Veículos"))
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(
				new io.swagger.v3.oas.models.info.Info()
				.title("API Serviço Veículos")
				.version("v1")
				.license(
					new License()
						.name("Apache 2.0")
						.url("http://springdoc.org")
				)
			);
	}
	
}
