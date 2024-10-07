package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
}
