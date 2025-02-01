package com.xoff.chessvger.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    System.out.println("addCorsMappings");

    registry.addMapping("/ws/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
        .allowedOrigins("http://localhost:3002").allowCredentials(false);

    registry.addMapping("/ws/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
        .allowedOrigins("http://localhost:3000").allowCredentials(false);

    registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowedOrigins("http://localhost:3000").allowCredentials(false);
    registry.addMapping("/apiadmin/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowedOrigins("http://localhost:3002").allowCredentials(false);
    registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowedOrigins("http://localhost:3002").allowCredentials(false);
    registry.addMapping("/apiadmin/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowedOrigins("http://localhost:3000").allowCredentials(false);

     registry.addMapping("/api/**")  .allowedHeaders("*").allowCredentials(false);
    registry.addMapping("/apiadmin/**")  .allowedHeaders("*").allowCredentials(false);

    registry.addMapping("/ws/**").allowCredentials(false);
    registry.addMapping("/app/ws/**").allowCredentials(false);

    registry.addMapping("/*").allowedOrigins("*").allowCredentials(false).allowedHeaders("*")
        .exposedHeaders("*").maxAge(60 * 30).allowedMethods("*");
  }
}