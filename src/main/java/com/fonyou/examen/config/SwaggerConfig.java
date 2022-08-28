package com.fonyou.examen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${info.project.version}")
	private String projectVersion;

	private final Response noContentResponse = new ResponseBuilder().code("200").description("OK")
			.examples(List.of(new ExampleBuilder().mediaType("application/json")
					.value("No value returned in body").build())).build();

	private final Response requiredResponse = new ResponseBuilder().code("400")
			.description("Bad Request")
			.examples(List.of(new ExampleBuilder().mediaType("application/json").value("{\n"
					+ "    \"timestamp\": \"2021-12-13T18:48:18.2883252\",\n"
					+ "    \"error\": {\n"
					+ "        \"name\": \"name is required\"\n"
					+ "    },\n"
					+ "    \"message\": \"Client error\"\n"
					+ "}").build())).build();

	private final Response noValuePresentResponse = new ResponseBuilder().code("400")
			.description("Bad Request")
			.examples(List.of(new ExampleBuilder().mediaType("application/json").value("{\n"
					+ "    \"error\": \"No value present\",\n"
					+ "    \"message\": \"Client error\",\n"
					+ "    \"timestamp\": \"2021-12-13T18:17:10.1934048\"\n"
					+ "}").build())).build();

	private final Response serverErrorResponse = new ResponseBuilder().code("500")
			.description("Internal Server Error")
			.examples(List.of(new ExampleBuilder().mediaType("application/json").value("{\n"
					+ "  \"please contact technical support and provide the following timestamp\": \"2021-08-30T10:25:36.865732600\"\n"
					+ "}").build())).build();

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.fonyou.examen.rest.controller"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false).apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(" ").version("1.0.0")
				.contact(new Contact("angel santiago", "", "ashernandezs@outlook.com"))
				.build();
	}
}
