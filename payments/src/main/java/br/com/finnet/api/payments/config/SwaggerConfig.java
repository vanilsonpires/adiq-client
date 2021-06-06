package br.com.finnet.api.payments.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo()).
				securitySchemes(Arrays.asList(apiKey()));
	}    

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Serviço de pagamento")
				.description("API intermediadora de pagamentos")
				.termsOfServiceUrl("/payments/terms")
				.version("1.0")
				.build();
	}
	
	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
}
