package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.dtos.users.request.UserProfileRequestDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.exceptions.RecordNotFoundException;
import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.Profile;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.ProfileRepository;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import nl.mpdev.hotel_california_backend.services.security.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

  public User registerNewUser(User entity) {
    Set<Authority> authorities = new HashSet<>();
    Authority.AuthorityBuilder authorityBuilder = Authority.builder();
    authorityBuilder.username(entity.getUsername());
    authorityBuilder.authority("ROLE_USER");
    authorities.add(authorityBuilder.build());
    entity = entity.toBuilder()
      .authorities(authorities)
      .password(passwordEncoder.encode(entity.getPassword()))
      .build();
    return userRepository.save(entity);
  }

  public User registerNewCustomUser(User entity) {
    if (entity.getAuthorities() == null || entity.getAuthorities().isEmpty()) {
      throw new GeneralException("User must have at least one authority.");
    }
    Set<Authority> updatedAuthorities = new HashSet<>();
    if (!entity.getAuthorities().isEmpty()) {
      User finalEntity = entity;
      updatedAuthorities = entity.getAuthorities().stream()
        .map(existingAuthority -> Authority.builder()
          .username(finalEntity.getUsername())
          .authority(existingAuthority.getAuthority())
          .build())
        .collect(Collectors.toSet());
    }
    entity = entity.toBuilder()
      // Later i have to decode the password here with a password decoder
      .password(passwordEncoder.encode(entity.getPassword()))
      .authorities(updatedAuthorities)
      .build();
    return userRepository.save(entity);
  }

  public User updateProfileFields(Integer id, UserProfileRequestDto requestDto) {
    User existingUser = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User with id " + id + " not found."));
    Profile newOrExistingProfile;
    if(requestDto.getProfile() != null) {
      newOrExistingProfile = profileRepository.findById(requestDto.getProfile().getId()).orElseThrow(() -> new RecordNotFoundException());
    } else {
      newOrExistingProfile = existingUser.getProfile();
    }
    existingUser = existingUser.toBuilder()
      .profile(newOrExistingProfile)
      .build();
    return userRepository.save(existingUser);
  }

  public String verify(User user) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    if (authentication.isAuthenticated()) {
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("authorities", authentication.getAuthorities());
      return jwtService.generateToken(extraClaims, user.getUsername());

    }
    return "User is not logged in.";
  }
}
