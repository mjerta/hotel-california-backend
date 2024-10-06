package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User registerNewUser(User entity) {
    Set<Authority> authorities = new HashSet<>();
    Authority.AuthorityBuilder authorityBuilder = Authority.builder();
    authorityBuilder.username(entity.getUsername());
    authorityBuilder.authority("ROLE_USER");
    authorities.add(authorityBuilder.build());
    entity = entity.toBuilder()
      // Later i have to decode the password here with a password decoder
      .authorities(authorities)
      .build();
    return userRepository.save(entity);
  }

  public User registerNewCustomUser(User entity) {
    Set<Authority> updatedAuthorities = new HashSet<>();
    if (!entity.getAuthorities().isEmpty()) {
      User finalEntity = entity;
      updatedAuthorities = entity.getAuthorities().stream()
        .map(existingAuthority -> {
          return Authority.builder()
            .username(finalEntity.getUsername())
            .authority(existingAuthority.getAuthority())
            .build();
        })
        .collect(Collectors.toSet());
    }
    entity = entity.toBuilder()
      // Later i have to decode the password here with a password decoder
      .authorities(updatedAuthorities)
      .build();
    return userRepository.save(entity);
  }
}
