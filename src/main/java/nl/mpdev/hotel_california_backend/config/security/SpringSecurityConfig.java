package nl.mpdev.hotel_california_backend.config.security;

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

  private String test;

  private final DataSource dataSource;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  public SpringSecurityConfig(DataSource dataSource, @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
                              UserDetailsService userDetailsService) {
    this.dataSource = dataSource;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  // Bean for the password encoder to encode the password
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
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

        // Meals
        .requestMatchers(HttpMethod.GET, "/api/v1/meals/*").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/meals").permitAll()
        //has rol and hasauthority they do effectively the same
        .requestMatchers(HttpMethod.POST, "/api/v1/meals").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/meals/").hasAuthority("ROLE_MANAGER")
        // Ingredients

        .requestMatchers(HttpMethod.DELETE, "/api/v1/ingredients/*").hasAuthority("ROLE_MANAGER")
        // Drinks

        .requestMatchers(HttpMethod.GET, "/api/v1/drinks/*").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/drinks").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/drinks").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")
        // Orders

        .requestMatchers(HttpMethod.GET, "/api/v1/orders/*").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAuthority("ROLE_STAFF")
        // he following is permitted for all however in the service layer there should be some kind of check to see uif one of the table
        // is occupied
        // Also need to change the dto
        // Need to assign this value based on the user that is logged in
        .requestMatchers(HttpMethod.POST, "/api/v1/orders").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/*").permitAll()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/*").permitAll()
        // Only a staff member cna close the order. After being payed off course.
        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/*").hasAuthority("ROLE_STAFF")

        // Users

        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ROLE_STAFF")
        .requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/customregister").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
        // also make sure here that only the user is logged in can be changed make this decision trough userdetails
        .requestMatchers(HttpMethod.PATCH, "/api/v1/userprofiles").hasAuthority("ROLE_USER")

        // also make sure here that only the user is logged in can be changed make this decision trough userdetails
        // and that the id should be retrieved based   on the jwt token is coming back

        .requestMatchers(HttpMethod.GET, "/api/v1/profiles/*").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/api/v1/profiles").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.POST, "/api/v1/profiles/*").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/profiles/*").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/profiles/*").hasAuthority("ROLE_USER")

        .requestMatchers(HttpMethod.DELETE, "/api/v1/profiles/*").hasAuthority("ROLE_MANAGER")
        .anyRequest().denyAll())
      .sessionManagement(session -> session
        .sessionCreationPolicy(
          SessionCreationPolicy.STATELESS)) // This is needed to make the application stateless , so no session is created , this is
      // needed for JWT , because JWT is stateless
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
  }
}
