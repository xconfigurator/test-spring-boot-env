package liuyang.testspringbootenv.modules.web.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


//@Configuration
//@EnableSwagger2
//@EnableSwaggerBootstrapUI
public class Swagger2Config implements WebMvcConfigurer {

    private static final String BASE_PACKAGE = "liuyang.testspringbootenv.modules.web.controller";

    @Bean
    public Docket liuyangCustomizedDocket() {
        List<Parameter> params = new ArrayList<>();
        ParameterBuilder tokenParam = new ParameterBuilder();
        params.add(tokenParam.name("access-token")
                .description("令牌")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("liuyang的数据平台API")
                        .version("0.0.1")
                        .description("融合数据提供者，为前端提供JSON格式数据。目前支持MySQL, Redis。")
                        .contact(new Contact("liuyang", "https://xconfigurator.github.io/", "xconfigurator@126.com"))
                        .license("The Apache License, Version 2.0")
                        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // .paths(PathSelectors.any()) // ??
                .build().globalOperationParameters(params);
        return docket;
    }
}
