package br.com.fiap.microservices.configuracao;


import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenApiConfig {
	
	@Bean
	@Lazy(false) // Porque tem que ser carregado assim que api-gateway estiver inicializando... Não pode esperar
	public List<GroupedOpenApi> apis(
			SwaggerUiConfigParameters config,
			RouteDefinitionLocator locator) {
		
		var definitions = locator.getRouteDefinitions().collectList().block();
		
		definitions.stream().filter(
					routeDefinition -> routeDefinition.getId()
						.matches(".*-service"))
							.forEach(routeDefinition -> {
								String name = routeDefinition.getId().replace("-service", "");
								config.addGroup(name);
								GroupedOpenApi.builder()
									.pathsToMatch("/" + name + "/**")
									.group(name).build();
							}
				);
		return new ArrayList<>();
	}

}