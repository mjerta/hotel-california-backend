package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
