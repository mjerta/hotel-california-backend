package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import nl.mpdev.hotel_california_backend.services.security.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder,
                     AuthenticationManager authenticationManager, JWTService jwtService) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User registerNewUser(User entity) {
    checkIfUserExist(entity);
    Authority.AuthorityBuilder authorityBuilder = Authority.builder();
      authorityBuilder.username(entity.getUsername());
    // DEFAULT USER
    Set<Authority> authorities = new HashSet<>();
    authorities.add(authorityBuilder.authority("ROLE_USER").build());

    entity = entity.toBuilder()
      .enabled(true)
      .authorities(authorities)
      .password(passwordEncoder.encode(entity.getPassword()))
      .build();
    return userRepository.save(entity);
  }

  public User registerNewCustomUser(User entity) {
    checkIfUserExist(entity);
    if (entity.getAuthorities() == null || entity.getAuthorities().isEmpty()) {
      throw new GeneralException("User must have at least one authority.");
    }
    Set<Authority> updatedAuthorities;
      User finalEntity = entity;
      updatedAuthorities = entity.getAuthorities().stream()
        .map(existingAuthority -> Authority.builder()
          .username(finalEntity.getUsername())
          .authority(existingAuthority.getAuthority())
          .build())
        .collect(Collectors.toSet());

    // Check if at least one authority is valid
    List<String> validRoles = List.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_STAFF", "ROLE_USER");
    boolean hasValidRole = updatedAuthorities.stream()
      .map(Authority::getAuthority)
      .anyMatch(validRoles::contains);

    if (!hasValidRole) {
      throw new GeneralException("User must have at least one valid role: ROLE_ADMIN, ROLE_MANAGER, ROLE_STAFF, or ROLE_USER.");
    }
    entity = entity.toBuilder()
      .password(passwordEncoder.encode(entity.getPassword()))
      .authorities(updatedAuthorities)
      .build();
    return userRepository.save(entity);
  }

  public void deleteUserByUserLoggedIn() {
    User existingUser = getloggedInUser();
    userRepository.delete(existingUser);
  }

  private User getloggedInUser() {
    User exisitingUser = null;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      exisitingUser = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RecordNotFoundException("No user found"));
    }
    return exisitingUser;
  }

  public Map<String, String> verify(User user) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );
    Map<String, String> succesResult = new HashMap<>();
    if (authentication.isAuthenticated()) {
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("authorities", authentication.getAuthorities());
      succesResult.put("jwt", jwtService.generateToken(extraClaims, user.getUsername()));
      return succesResult;
    }
    Map<String, String> errorResult = new HashMap<>();
    errorResult.put("message", "Authentication failed");
    return errorResult;
  }

  private void checkIfUserExist(User entity) {
    if (userRepository.findByUsername(entity.getUsername()).isPresent()) {
      throw new GeneralException("User with username " + entity.getUsername() + " already exists");
    }
  }
}
