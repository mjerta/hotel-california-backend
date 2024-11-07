package nl.mpdev.hotel_california_backend.dtos.profiles.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ProfileCompleteRequestDto {
  @NotBlank(message = "First name must not be blank")
  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
  private String lastName;

  @NotBlank(message = "Phone number must not be blank")
  @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number must contain only numbers and can start with +")
  @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
  private String phoneNumber;

  @NotBlank(message = "Address must not be blank")
  @Size(max = 100, message = "Address must not exceed 100 characters")
  private String address;

  @NotNull(message = "Points must not be null")
  @Min(value = 0, message = "Points must be greater than or equal to 0")
  private Integer points;
}
