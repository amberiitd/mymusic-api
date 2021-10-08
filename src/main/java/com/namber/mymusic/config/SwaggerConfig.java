package com.namber.mymusic.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.oauth.serverUrl}")
    private String authServer;

    @Value("${swagger.oauth.clientId}")
    private String clientId;

    @Value("${swagger.oauth.clientSecret}")
    private String clientSecret;

    private static String OAUTH_REF = "OAuth2.0";

    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(
                        new SecurityContextBuilder().securityReferences(
                            Arrays.asList(new SecurityReference(OAUTH_REF, getOAuthScopes()))
                        ).build()
                        )
                )
                .securitySchemes(Arrays.asList(getOAuthScheme()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private AuthorizationScope[] getOAuthScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("read", "fetch song data")};
    }

    private SecurityScheme getOAuthScheme() {
        return new OAuthBuilder().grantTypes(
                Arrays.asList(new AuthorizationCodeGrantBuilder()
                        .tokenEndpoint(new TokenEndpoint(authServer + "/oauth/token", "oauthtoken"))
                        .tokenRequestEndpoint(
                                new TokenRequestEndpoint(authServer + "/oauth/authorize", clientId, clientSecret))
                        .build()
                ))
                .name("OAuth2.0")
                .scopes(Arrays.asList(getOAuthScopes()))
                .build();
    }


    // swagger config by resource disabled
//    @Primary
//    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider resourcesProvider){
        return () ->{
            return getResources();
        };
    }

    private List<SwaggerResource> getResources() {
        List<SwaggerResource> resources = new ArrayList<>();

        Arrays.asList("swagger-config").forEach( name ->{
            resources.add(new SwaggerResource(){{
                setName(name);
                setLocation("/"+ name+".yml");
                setSwaggerVersion("2.0");
            }});
        });

        return resources;
    }

}
