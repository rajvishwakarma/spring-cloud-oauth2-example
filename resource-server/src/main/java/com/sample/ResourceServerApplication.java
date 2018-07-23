package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This is the Ecosystem Recommendation Application which serves the purpose of
 * a hosting the Different Type of Recommendation flows. 
 * This application serves the purpose of REST applications. 
 * 
 * Authentication: Ecosystem Auth
 * Spring Cloud Implementation: Spring Cloud Security Ouath2
 * 
 * @author RV
 *
 */
@EnableResourceServer
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ResourceServerApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests().anyRequest().authenticated();
    }

    @Configuration
    @EnableOAuth2Client
    static class OauthClient {

        @Bean
        public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                     OAuth2ProtectedResourceDetails details) {
            return new OAuth2RestTemplate(details, oauth2ClientContext);
        }
    }

    @Configuration
    public class WorkaroundConfig extends WebMvcConfigurerAdapter {

        @Autowired
        @Qualifier("tokenRelayRequestInterceptor")
        HandlerInterceptor handlerInterceptor;

        @Override
        public void addInterceptors (InterceptorRegistry registry) {
            registry.addInterceptor(handlerInterceptor);
        }
    }
}