package com.xoff.chessvger;


import com.xoff.chessvger.util.DeleteDbUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
public class ChessVgerApplication implements WebMvcConfigurer {


  private DeleteDbUtil deleteDbUtil;

  /**
   * @deprecated TODO remove
   * @param args
   */
  private static void checkOption(String[] args) {
    for (String arg : args) {
      log.info("parameter " + arg);
      if (arg.startsWith(EnvManager.RUN_FOLDER_PARAM)) {
        String[] param = arg.split("=");
        log.info("option " + param[1]);
        EnvManager.getInstance().addValue(EnvManager.RUN_FOLDER_PARAM, param[1]);
      }

    }
  }

  public static void main(String[] args) {
    System.out.println("version 1.0.2");
    checkOption(args);
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
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }
  @EventListener(ApplicationReadyEvent.class)
  public void initializeAfterStartup() {
    log.info("initializeAfterStartup");
   // FIXME deleteDbUtil.execute();
  }
}
