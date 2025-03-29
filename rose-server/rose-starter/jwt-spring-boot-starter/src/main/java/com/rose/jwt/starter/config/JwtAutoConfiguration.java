package com.rose.jwt.starter.config;

import com.rose.jwt.starter.utils.JwtUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@PropertySource("classpath:/config/jwt-default.properties")
public class JwtAutoConfiguration {

    @Bean
    public JwtUtil jwtUtil(JwtProperties jwtProperties){
        return new JwtUtil(jwtProperties);
    }

}
