package nl.mpdev.hotel_california_backend.dtos.drinks;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrinkIdRequestDto {
  private Integer id;
}
