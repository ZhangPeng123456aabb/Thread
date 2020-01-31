package com.exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * interceptor register
 */
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

    @Autowired
    private HelloWordInterceptor helloWordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(helloWordInterceptor).addPathPatterns("/**").excludePathPatterns(
            "/swagger-resources/**", "/swagger-ui.html/**");
    }
}
