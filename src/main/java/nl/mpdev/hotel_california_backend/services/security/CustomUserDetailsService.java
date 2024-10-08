package nl.mpdev.hotel_california_backend.services.security;

import nl.mpdev.hotel_california_backend.config.UserPrincipal;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.models.User;
import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (user == null) {
      throw new GeneralException("User not found");
    }
    else {
      return new UserPrincipal(user);
    }
  }
}