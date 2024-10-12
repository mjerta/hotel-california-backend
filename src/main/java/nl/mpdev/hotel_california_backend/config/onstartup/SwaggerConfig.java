package nl.mpdev.hotel_california_backend.config.onstartup;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Hotel California Backend")
        .version("0.0.1")
        .description("Backend REST API for Hotel California Backend")
        .contact(new Contact()
          .name("Maarten Postma")
          .email("info@mpdev.nl")
          .url("https://mpdev.nl")));
  }


}