package nl.mpdev.hotel_california_backend.dtos.authorities.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityCompleteRequestDto {
  @NotBlank(message = "Authority cannot be blank")
  private String authority;
}
