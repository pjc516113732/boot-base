package com.smile.boot.autoconfigure.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableConfigurationProperties(SpringFoxProperties.class)
@ConditionalOnClass(EnableSwagger2.class)
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SpringFoxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Docket api(SpringFoxProperties springFoxProperties) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .apiInfo(apiInfo(springFoxProperties))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();

        docket.pathProvider(new AbstractPathProvider() {
            @Override
            protected String applicationPath() {
                return springFoxProperties.getApplicationPath();
            }

            @Override
            protected String getDocumentationPath() {
                return springFoxProperties.getDocumentationPath();
            }
        });
        return docket;
    }


    @Bean
    @ConditionalOnMissingBean
    public ApiInfo apiInfo(SpringFoxProperties springFoxProperties) {
        SpringFoxProperties.ContactInfo contact = springFoxProperties.getContact();
        return new ApiInfoBuilder()
                .title(springFoxProperties.getTitle())
                .description(springFoxProperties.getDescription())
                .termsOfServiceUrl(springFoxProperties.getTermsOfServiceUrl())
                .license(springFoxProperties.getVersion())
                .licenseUrl(springFoxProperties.getLicenseUrl())
                .version(springFoxProperties.getVersion())
                .contact(new Contact(contact.getName(), contact.getUrl(), contact.getName())).build();
    }


    /**
     * ??????????????? UI ????????????
     *
     * @return UI ????????????
     */
    @Bean
    @ConditionalOnMissingBean
    public UiConfiguration uiConfig() {
        // ????????????????????????
        return UiConfigurationBuilder.builder().build();
    }

    /**
     * ???????????????swagger?????????????????????????????????"??????"???????????????????????????????????????????????????alpha??????????????????
     * <p>
     * ???????????????"??????"????????????????????? {@link Class#getDeclaredFields()}
     * ???????????????????????????????????????????????????????????????????????? Javadoc???
     */
    @Configuration
    @ConditionalOnClass(EnableSwagger2.class)
    @ConditionalOnProperty(name = "swagger.class-ordered", havingValue = "true", matchIfMissing = true)
    @Order(CustomApiModelPropertyPropertyBuilder.SWAGGER_PLUGIN_ORDER)
    public static class CustomApiModelPropertyPropertyBuilder implements ModelPropertyBuilderPlugin {

        /**
         * ???????????? {@link ApiModelPropertyPropertyBuilder} ??????
         */
        public static final int SWAGGER_PLUGIN_ORDER = SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1;

        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void apply(ModelPropertyContext context) {
            context.getBuilder().position(counter.getAndIncrement());
        }

        @Override
        public boolean supports(DocumentationType delimiter) {
            return SwaggerPluginSupport.pluginDoesApply(delimiter);
        }
    }


}
