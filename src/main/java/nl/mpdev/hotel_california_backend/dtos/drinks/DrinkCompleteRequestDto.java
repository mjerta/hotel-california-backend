package nl.mpdev.hotel_california_backend.dtos.drinks;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class DrinkCompleteRequestDto {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private byte[] image;
  private Boolean isAlcoholic;
  private Integer size;
  private String measurement;
}
