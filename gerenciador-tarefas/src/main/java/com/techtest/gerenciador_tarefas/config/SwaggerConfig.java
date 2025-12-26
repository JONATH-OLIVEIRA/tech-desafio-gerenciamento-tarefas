package com.techtest.gerenciador_tarefas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Sistema de Tarefas API").version("1.0")
						.description("Documentação da API do Sistema de Tarefas"))
				.schema("DateCustom", new StringSchema().example("26/12/2025").pattern("^\\d{2}/\\d{2}/\\d{4}$")
						.description("Data no formato dd/MM/yyyy"));
	}
}
