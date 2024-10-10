package nl.mpdev.hotel_california_backend.config.onstartup;

import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {
  @Value("${app.default.user.super}")
  private String superUser;
  @Value("${app.default.user.super.password}")
  private String superUserPassword;

  @Value("${app.default.user.manager}")
  private String managerUser;
  @Value("${app.default.user.manager.password}")
  private String managerUserPassword;

  @Value("${app.default.user.staff}")
  private String staffUser;
  @Value("${app.default.user.staff.password}")
  private String staffUserPassword;

  @Value("${app.default.user.regular}")
  private String regularUser;
  @Value("${app.default.user.regular.password}")
  private String regularUserPassword;

  private final UserRepository userRepository;

  public DataInitializer(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public CommandLineRunner loadData(PasswordEncoder passwordEncoder) {
    return args -> {
      createUserWithRoles(superUser, passwordEncoder.encode(superUserPassword), "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(managerUser, passwordEncoder.encode(managerUserPassword), "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(staffUser, passwordEncoder.encode(staffUserPassword), "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(regularUser, passwordEncoder.encode(regularUserPassword), "ROLE_USER");
    };
  }

  public void createUserWithRoles(String user, String password, String... roles) {
    Set<Authority> authorities = new HashSet<>();
    for (String role : roles) {
      authorities.add(createAuthority(user, role));
    }
    saveUser(user, password, authorities);
  }

  private void saveUser(String userName, String password, Set<Authority> superUserAuthorities) {
    User adminUser = User.builder()
      .username(userName)
      .password(password)
      .enabled(true)
      .authorities(superUserAuthorities)
      .build();
    userRepository.save(adminUser);
  }

  private Authority createAuthority(String username, String authorityType) {
    return Authority.builder()
      .username(username)
      .authority(authorityType)
      .build();
  }
}