package com.xoff.chessvger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

  @Autowired
private CustomUserDetailsService userDetailsService;

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder)
        .and()
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/auth/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
    return http.build();
  }

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