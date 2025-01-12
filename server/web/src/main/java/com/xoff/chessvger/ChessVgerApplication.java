package com.xoff.chessvger;


import com.xoff.chessvger.util.DeleteDbUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Slf4j
@EntityScan("com.xoff.chessvger.repository")
public class ChessVgerApplication  {


  public static void main(String[] args) {
    System.out.println("version 1.0.6");
    SpringApplication.run(ChessVgerApplication.class, args);
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();

    messageSource.setBasename("classpath:languages/message");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void initializeAfterStartup() {
    log.info("initializeAfterStartup");
   // FIXME deleteDbUtil.execute();
  }

}
