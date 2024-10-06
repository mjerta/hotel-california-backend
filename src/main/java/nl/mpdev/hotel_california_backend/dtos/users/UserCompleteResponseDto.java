package nl.mpdev.hotel_california_backend.dtos.users;

import lombok.Builder;
import lombok.Getter;
import nl.mpdev.hotel_california_backend.dtos.authority.AuthorityCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.orders.OrderCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Authority;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class UserCompleteResponseDto {
  private Integer id;
  private String username;
  private Set<AuthorityCompleteResponseDto> authority;
}
