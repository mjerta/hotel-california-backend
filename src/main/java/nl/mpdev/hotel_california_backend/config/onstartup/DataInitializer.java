package nl.mpdev.hotel_california_backend.config.onstartup;

import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
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

  @Value("${app.default.user.chef}")
  private String chefUser;
  @Value("${app.default.user.chef.password}")
  private String chefUserPassword;

  @Value("${app.default.user.staff}")
  private String staffUser;
  @Value("${app.default.user.staff.password}")
  private String staffUserPassword;

  @Value("${app.default.user.regular}")
  private String regularUser;

  @Value("${app.default.user.regular.password}")
  private String regularUserPassword;

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;

  public DataInitializer(UserRepository userRepository, ProfileRepository profileRepository) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
  }

  @Bean
  public CommandLineRunner loadData(PasswordEncoder passwordEncoder) {
    return args -> {
      createUserWithRoles(superUser, passwordEncoder.encode(superUserPassword), "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(managerUser, passwordEncoder.encode(managerUserPassword), "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(chefUser, passwordEncoder.encode(chefUserPassword), "ROLE_STAFF", "ROLE_CHEF", "ROLE_USER");
      createUserWithRoles(staffUser, passwordEncoder.encode(staffUserPassword), "ROLE_STAFF", "ROLE_USER");
      createUserWithRoles(regularUser, passwordEncoder.encode(regularUserPassword), profileRepository.findById(1).orElseThrow(() -> new RecordNotFoundException("No profile found")), "ROLE_USER");
    };
  }

  public void createUserWithRoles(String user, String password, String... roles) {
    Set<Authority> authorities = new HashSet<>();
    for (String role : roles) {
      authorities.add(createAuthority(user, role));
    }
    saveUser(user, password, authorities);
  }

  public void createUserWithRoles(String user, String password, Profile profile, String... roles) {
    Set<Authority> authorities = new HashSet<>();
    for (String role : roles) {
      authorities.add(createAuthority(user, role));
    }
    saveUser(user, password, authorities, profile);
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

  private void saveUser(String userName, String password, Set<Authority> superUserAuthorities, Profile profile) {
    User adminUser = User.builder()
      .username(userName)
      .password(password)
      .enabled(true)
      .authorities(superUserAuthorities)
      .profile(profile)
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