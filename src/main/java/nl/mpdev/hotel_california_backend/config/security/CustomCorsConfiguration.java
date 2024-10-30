package nl.mpdev.hotel_california_backend.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomCorsConfiguration implements CorsConfigurationSource {

  @Override
  public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
    CorsConfiguration config = new CorsConfiguration();
    // Allow all origins
    config.addAllowedOriginPattern("*"); // Use addAllowedOriginPattern("*") to allow all origins
    // Allow all methods (GET, POST, PUT, DELETE, etc.)
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    // Allow all headers
    config.setAllowedHeaders(List.of("*"));
    // Allow credentials (e.g., cookies, Authorization headers)
    config.setAllowCredentials(true);
    // Cache pre-flight response for 1 hour
    config.setMaxAge(3600L);
    return config;
  }
}
