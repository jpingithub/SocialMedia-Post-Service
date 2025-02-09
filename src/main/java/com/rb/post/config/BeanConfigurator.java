package com.rb.post.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rb.post.utils.UtilClass;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurator {

    @Bean ObjectMapper objectMapper(){return new ObjectMapper();}

    @Bean RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String jwtToken = UtilClass.TokenContext.get();
            requestTemplate.header("Authorization", jwtToken);
        };
    }

}
