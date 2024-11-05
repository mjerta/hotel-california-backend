package nl.mpdev.hotel_california_backend.mappers.authorities;

import nl.mpdev.hotel_california_backend.dtos.authorities.request.AuthorityCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.authorities.response.AuthorityCompleteResponseDto;
import nl.mpdev.hotel_california_backend.exceptions.GeneralException;
import nl.mpdev.hotel_california_backend.models.Authority;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthoritiesCompleteMapper {

  public Set<Authority> toEntity(Set<AuthorityCompleteRequestDto> requestDto) {
    if (requestDto == null) {return null;}
    return requestDto.stream()
      .filter(Objects::nonNull) // Filter out null AuthorityCompleteRequestDto
      .map(dto -> {
        if (dto.getAuthority() == null) {
          throw new GeneralException("Authority cannot be null");
        }
        // Build Authority using AuthorityBuilder
        return Authority.builder()
          .authority(dto.getAuthority()) // Set authority
          .build();
      })
      .collect(Collectors.toSet()); // Collect into a Set
  }

  public Set<AuthorityCompleteResponseDto> toDto(Set<Authority> entities) {
    return entities.stream()
      .map(entity -> AuthorityCompleteResponseDto.builder()
        .username(entity.getUsername())
        .authority(entity.getAuthority())
        .build())
      .collect(Collectors.toSet()); // Collect into a Set
  }
}
