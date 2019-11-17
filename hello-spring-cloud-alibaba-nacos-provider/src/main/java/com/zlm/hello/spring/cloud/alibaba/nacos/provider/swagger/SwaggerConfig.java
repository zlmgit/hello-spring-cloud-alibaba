package com.zlm.hello.spring.cloud.alibaba.nacos.provider.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8081/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller")) //要扫描的API(Controller)基础包
                .paths(PathSelectors.any()) // and by paths
                .build()
                .apiInfo(buildApiInf());
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("API文档")
                .version("1.0.0")
                .build();
    }
}