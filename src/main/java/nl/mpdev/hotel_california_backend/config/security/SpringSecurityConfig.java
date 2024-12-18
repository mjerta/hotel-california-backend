package nl.mpdev.hotel_california_backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableWebSecurity
public class SpringSecurityConfig {
  private final DataSource dataSource;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserDetailsService userDetailsService;
  private final CustomCorsConfiguration customCorsConfiguration;

  @Autowired
  public SpringSecurityConfig(DataSource dataSource, JwtAuthenticationFilter jwtAuthenticationFilter,
                              UserDetailsService userDetailsService, CustomCorsConfiguration customCorsConfiguration) {
    this.dataSource = dataSource;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userDetailsService = userDetailsService;
    this.customCorsConfiguration = customCorsConfiguration;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

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

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      .cors(cors -> cors.configurationSource(customCorsConfiguration))
      .authorizeHttpRequests(auth -> auth

        // Swagger - API documentation
        .requestMatchers("/swagger-ui/**").permitAll()
        .requestMatchers("/swagger-ui/").permitAll()
        .requestMatchers("/swagger-resources/**").permitAll()
        .requestMatchers("/swagger-resources").permitAll()

        // Image-Meals
        .requestMatchers("/api/v1/image-meals/*").permitAll()

        // Meals
        .requestMatchers(HttpMethod.GET, "/api/v1/meals/*").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/meals").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/meals").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/meals/*").hasAuthority("ROLE_MANAGER")

        // Ingredients

        .requestMatchers(HttpMethod.POST, "/api/v1/ingredients").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/ingredients/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/ingredients/*").hasAuthority("ROLE_MANAGER")

        // Drinks
        .requestMatchers(HttpMethod.GET, "/api/v1/drinks/*").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/drinks").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/drinks").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.POST, "/api/v1/drinks").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/drinks/*").hasAuthority("ROLE_MANAGER")

        // Orders
        .requestMatchers(HttpMethod.GET, "/api/v1/orders/orderreference").permitAll() // 1
        .requestMatchers(HttpMethod.GET, "/api/v1/orders/*").hasAuthority("ROLE_USER") // 2
        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAuthority("ROLE_STAFF")
        .requestMatchers(HttpMethod.GET, "/api/v1/orders/loggeduser").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.POST, "/api/v1/orders").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/orderreference").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/*").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/orderreference").permitAll()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/*").hasAuthority("ROLE_USER")
        // Only a staff member can close the order. After being payed off course.
        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/updateorderbystaff/*").hasAuthority("ROLE_STAFF")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/updateorderbystaff/*").hasAuthority("ROLE_STAFF")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/*").hasAuthority("ROLE_STAFF")

        // Locations
        .requestMatchers(HttpMethod.GET, "/api/v1/locations").permitAll()

        // Users
        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ROLE_STAFF")
        .requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/customregister").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/loggeduser").hasAuthority("ROLE_USER")

        // Profiles
        .requestMatchers(HttpMethod.GET, "/api/v1/profiles/loggeduser").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.GET, "/api/v1/profiles").hasAuthority("ROLE_MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/v1/profiles").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PATCH, "/api/v1/profiles").hasAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.DELETE, "/api/v1/profiles/*").hasAuthority("ROLE_MANAGER")
        .anyRequest().permitAll())
      .sessionManagement(session -> session
        .sessionCreationPolicy(
          SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
  }
}
