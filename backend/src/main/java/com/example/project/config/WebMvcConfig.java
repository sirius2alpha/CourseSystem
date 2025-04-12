package com.example.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web全局配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 访问路径，允许所有路径跨域
                .allowedOrigins("http://localhost:8080", "http://127.0.0.1:8080", "null") // 跨域来源
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH") // 允许的HTTP方法
                .allowedHeaders("*") // 允许所有头部信息
                .allowCredentials(true) // 是否允许携带信息,例如token
                .maxAge(3600); // 最大响应时间
    }
}