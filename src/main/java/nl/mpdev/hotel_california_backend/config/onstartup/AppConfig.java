package nl.mpdev.hotel_california_backend.config.onstartup;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.default")
public class AppConfig {
  private String password;
  private String username;
}