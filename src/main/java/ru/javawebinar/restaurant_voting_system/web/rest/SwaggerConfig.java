package ru.javawebinar.restaurant_voting_system.web.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.javawebinar.restaurant_voting_system"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Restaurant Voting System API",
                """
                        API for Restaurant choosing.
                        Credentials for Swagger authorization (username/password):
                        Admin:     admin@gmail.com /   admin
                        Profile:   user@yandex.ru  /   password""",
                "1.0",
                "Free to use",
                new Contact(
                        "Artur Galimov",
                        "https://github.com/richart-proger/topjava-restaurant-voting-system",
                        "arturgalimov33@gmail.com"
                ), "", "", Collections.emptyList());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder
                .builder()
                .operationsSorter(OperationsSorter.ALPHA)
                .build();
    }

    private static List<? extends SecurityScheme> securitySchemes() {
        return List.of(new BasicAuth("Basic"));
    }
}