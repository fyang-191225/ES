package com.fyy.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author fyy
 * @date 2020/1/10 0010 下午 18:52
 */@Configuration
public class SwaggerConfig {
     // 文档说明
     public ApiInfo creatApi() {
         ApiInfo apiInfo = new ApiInfoBuilder().title(" spring data Es 全文检索接口").description("增删查接口")
                 .contact(new Contact("fyy","","")).build();
         return apiInfo;
     }

     // 创建Swagger 扫描信息

    @Bean
    public Docket creatD() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .apiInfo(creatApi())
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("com.fyy.es.data.controller"))
                 .build();
    }
}
