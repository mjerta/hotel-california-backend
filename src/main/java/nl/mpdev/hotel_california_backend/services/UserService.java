package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.models.Authority;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
      .authorities(authorities)
      .build();
    return userRepository.save(entity);
  }

  public User registerNewCustomUser(User entity) {
    Set<Authority> authorities = new HashSet<>();
    Authority.AuthorityBuilder authorityBuilder = Authority.builder();
    authorityBuilder.username(entity.getUsername());
    authorityBuilder.authority("ROLE_USER");
    authorities.add(authorityBuilder.build());
    entity = entity.toBuilder()
      .authorities(authorities)
      .build();
    return userRepository.save(entity);
  }
}
