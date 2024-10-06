package nl.mpdev.hotel_california_backend.dtos.users;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;

import java.util.List;

@Builder
@Getter
public class UserCompleteResponseDto {
  private Integer id;
  private String username;
}
