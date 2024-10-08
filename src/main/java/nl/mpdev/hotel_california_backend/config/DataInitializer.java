package nl.mpdev.hotel_california_backend.config;

import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

  @Autowired
  private UserRepository userRepository;

  @Value("${app.default.password}") // Injecting the value of app.greeting from application.properties
  private String greeting;
  @Value("${app.default.username}")
  private String username;


  @Bean
  public CommandLineRunner loadData(PasswordEncoder passwordEncoder) {
    return args -> {
      // Hash the default password
      String hashedPassword = passwordEncoder.encode(greeting);

      // Create a set of authorities
      Set<Authority> authorities = new HashSet<>();

      // Build the authority
      Authority adminAuthority = Authority.builder()
        .username(username) // Set the username for the authority
        .authority("ROLE_ADMIN") // Define the authority role
        .build();

      // Add the authority to the set
      authorities.add(adminAuthority);

      // Create and save the user with the authorities
      User adminUser = User.builder()
        .username(username)
        .password(hashedPassword)
        .enabled(true)
        .authorities(authorities) // Set the authorities
        .build();

      userRepository.save(adminUser); // Save the user to the repository
    };
  }
}