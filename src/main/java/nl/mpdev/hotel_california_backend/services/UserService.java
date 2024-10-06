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
    Authority authority = new Authority();
    authority.setUsername(entity.getUsername());
    authority.setAuthority("ROLE_USER");
    authorities.add(authority);
    entity = entity.toBuilder()
      .authorities(authorities)
      .build();

    return userRepository.save(entity);
  }
}
