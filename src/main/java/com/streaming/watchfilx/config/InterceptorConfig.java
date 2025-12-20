package com.streaming.watchfilx.config;

import com.streaming.watchfilx.middleware.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ApiInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns("/**") // TOUTES les API
                .excludePathPatterns(
                        "/auth/login",
                        "/auth/register",
                        "/auth/forgot-password",
                        "/auth/reset-password"
                );
    }
}
