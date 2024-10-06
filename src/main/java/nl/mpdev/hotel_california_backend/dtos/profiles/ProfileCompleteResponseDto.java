package nl.mpdev.hotel_california_backend.dtos.profiles;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ProfileCompleteResponseDto {
  private Integer id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String address;
  private Integer points;

}
