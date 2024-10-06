package nl.mpdev.hotel_california_backend.services;

import nl.mpdev.hotel_california_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
