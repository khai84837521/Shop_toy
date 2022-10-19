package com.example.shop_toy.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("쇼핑몰 회원, 상품, 주문 기능 프로젝트")
                .description("API 명세서")
                .build();
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, newArrayList(
                        new ResponseBuilder().code("500").description("서버에 오류가 발생").build(),
                        new ResponseBuilder().code("404").description("잘못된 주소").build(),
                        new ResponseBuilder().code("400").description("요청에 필요한 데이터를 잘못 작성").build()
                ))
                .globalResponses(HttpMethod.POST, newArrayList(
                        new ResponseBuilder().code("500").description("서버에 오류가 발생").build(),
                        new ResponseBuilder().code("404").description("잘못된 주소").build(),
                        new ResponseBuilder().code("400").description("요청에 필요한 데이터를 잘못 작성").build()
                ))
                .globalResponses(HttpMethod.PUT, newArrayList(
                        new ResponseBuilder().code("500").description("서버에 오류가 발생").build(),
                        new ResponseBuilder().code("404").description("잘못된 주소").build(),
                        new ResponseBuilder().code("400").description("요청에 필요한 데이터를 잘못 작성").build()
                ))
                .globalResponses(HttpMethod.DELETE, newArrayList(
                        new ResponseBuilder().code("500").description("서버에 오류가 발생").build(),
                        new ResponseBuilder().code("404").description("잘못된 주소").build(),
                        new ResponseBuilder().code("400").description("요청에 필요한 데이터를 잘못 작성").build()
                ))
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class))
                )
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.parkjinhun.shopping.shopping_mall_project.interfaces"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    @Getter
    @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(1..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues="range[0, 100]")
        private Integer size;

        @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
        private List<String> sort;
    }
}
