package com.xoff.chessvger.config;

import java.util.Arrays;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
System.out.println("addCorsMappings");
    registry.addMapping("/ws/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowedOrigins("http://localhost:3000").allowCredentials(false);

    registry.addMapping("/api/**") .allowCredentials(false);
    registry.addMapping("/ws/**") .allowCredentials(false);
    registry.addMapping("/app/ws/**") .allowCredentials(false);
    registry
        // Enable cross-origin request handling for the specified path pattern.
        // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**").
        .addMapping("/*")
        .allowedOrigins("*")
        // .allowedOriginPatterns("")
        .allowCredentials(false)
        .allowedHeaders("*")
        .exposedHeaders("*")
        .maxAge(60 *30)
        .allowedMethods("*")
    ;
  }
}