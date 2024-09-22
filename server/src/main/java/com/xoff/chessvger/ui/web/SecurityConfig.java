package com.xoff.chessvger.ui.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                 HandlerMappingIntrospector introspector)
      throws Exception {

    //   MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    // http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvcMatcherBuilder.
    // pattern("/bds")).permitAll().requestMatchers(mvcMatcherBuilder.pattern("/features")).
    // permitAll().anyRequest().authenticated()).formLogin(Customizer.withDefaults()).
    // httpBasic(Customizer.withDefaults());
   /*     http.authorizeHttpRequests((requests)
                -> requests.requestMatchers(mvcMatcherBuilder.pattern("/bds")).permitAll().
                requestMatchers(mvcMatcherBuilder.pattern("/features")).permitAll().
                anyRequest().authenticated()).formLogin(Customizer.withDefaults()).
                httpBasic(Customizer.withDefaults());
    */
    // FIXME
    // le srf permet d autoriser le post sur les tests
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
    return http.build();

    /*
     *  Configuration to deny all the requests
     */
    /*http.authorizeHttpRequests(requests -> requests.anyRequest().denyAll())
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());
    return http.build();*/

    /*
     *  Configuration to permit all the requests
     */
    /*
    http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());
    return http.build();
    */
  }
}
