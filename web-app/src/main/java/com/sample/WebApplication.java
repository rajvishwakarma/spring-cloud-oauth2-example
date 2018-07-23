package com.sample;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This is the Web Application which serves the purpose as a 
 * face of the Microservices Application hosting the different 
 * types of services and its flows. This application serves the purpose of 
 * MVC applications. 
 * 
 * Authentication: Auth-Server(Spring Cloud Outh2)
 * Spring Cloud Implementation: Zuul Proxy Server
 * 
 * @author RV
 *
 */
@EnableZuulProxy
@EnableOAuth2Sso
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) throws MalformedURLException {

        SpringApplication.run(WebApplication.class, args);

    }
    
    @Value("${auth-server}/exit")
    private String logoutUrl;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl(logoutUrl).and().antMatcher("/**").authorizeRequests()
                .antMatchers("/login","/auth/**","/login/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }

}