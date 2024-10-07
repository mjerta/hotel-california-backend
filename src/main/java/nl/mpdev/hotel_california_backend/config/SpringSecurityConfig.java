package nl.mpdev.hotel_california_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // This annotation is not needed in Spring Boot 2.0 (Spring Security 5.0) and later versions of Spring Boot because
// Spring Boot automatically enables Spring Security.
public class SpringSecurityConfig {

  private final DataSource dataSource;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserDetailsService userDetailsService;

  @Autowired
  public SpringSecurityConfig(DataSource dataSource, @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
                              UserDetailsService userDetailsService) {
    this.dataSource = dataSource;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userDetailsService = userDetailsService;
  }

  // Bean for the password encoder to encode the password
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // The userDetailsService is custom and is autowired als a field in this class, i could however use constructor injection
  // The main benefits:  Custom Authentication Logic, Flexibility, Integration, Reusability, Control
  // The UserDetailsService is can not be used in combination with this method
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // the filterChain method is used to configure the security filter chain
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login").permitAll()
        .requestMatchers("/register").permitAll()
        .requestMatchers("/logout").permitAll()
        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/info").hasAuthority("WRITE_PRIVILEGE")
        .requestMatchers(HttpMethod.POST, "/register").hasAnyRole("ADMIN", "USER")
        .requestMatchers("/api/csrf-token").hasAnyRole("ADMIN", "USER")
        .anyRequest().denyAll())
//      .httpBasic(Customizer.withDefaults())
//      .formLogin(Customizer.withDefaults())
      .sessionManagement(session -> session
        .sessionCreationPolicy(
          SessionCreationPolicy.STATELESS)) // This is needed to make the application stateless , so no session is created , this is
      // needed for JWT , because JWT is stateless
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
  }
}
