package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

}
