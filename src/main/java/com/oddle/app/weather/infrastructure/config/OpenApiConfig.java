package com.oddle.app.weather.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuongnd9
 * @date 15/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
@Configuration
public class OpenApiConfig {
	
	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("template").pathsToMatch("/**").build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API specification document")
                .description("API specification document for project")
                .version("v1")
                .license(new License().name("Weather API").url("http://localhost:8080/weather")))
                .externalDocs(new ExternalDocumentation()
                .description("")
                .url("http://localhost:8080/weather"));
    }
    
}
