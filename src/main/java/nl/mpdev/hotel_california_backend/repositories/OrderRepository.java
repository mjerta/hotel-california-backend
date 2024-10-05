package nl.mpdev.hotel_california_backend.repositories;

import nl.mpdev.hotel_california_backend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
