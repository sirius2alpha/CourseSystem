package com.example.project.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//全局配置类--配置跨域请求
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")   //访问路径，访问的都允许跨越
                .allowedOrigins("Http://localhost:8080","null") //跨越来源
                .allowedMethods("GET","POST","PUT","OPTION","DELETE")  //跨越方法
                .allowCredentials(true) //是否允许携带信息,例如tocken
                .maxAge(3600); //最大响应时间
    }
}
