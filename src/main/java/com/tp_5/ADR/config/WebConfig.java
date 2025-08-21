package com.tp_5.ADR.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:4200")
        .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(false)
        .maxAge(3600);
  }
}