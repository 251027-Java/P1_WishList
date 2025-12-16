package com.revature.WishListApplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Fields
    private final BasicAuthInterceptor basicAuthInterceptor;

    // Constructor
    public WebConfig(BasicAuthInterceptor bai) {
        this.basicAuthInterceptor = bai;
    }

    // Method
    @Override
    public void addInterceptors(InterceptorRegistry reg) {
        reg.addInterceptor(basicAuthInterceptor)
        .addPathPatterns("/api/**")
        .excludePathPatterns("/**/OPTIONS");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
    }
}
