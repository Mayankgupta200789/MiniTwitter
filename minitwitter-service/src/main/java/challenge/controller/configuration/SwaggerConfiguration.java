package challenge.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Author Mayank Gupta
 * @Date 8/21/17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean(name = "twitterServiceApiDocs")
    public Docket twitterServiceApiDocs() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("challenge.controller"))
                .paths(regex("/twitter-service.*"))
                .build();

    }


}
