package nl.mpdev.hotel_california_backend.mappers.authorities;

import nl.mpdev.hotel_california_backend.dtos.authority.AuthorityCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Authority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthoritiesCompleteMapper {

  public Set<AuthorityCompleteResponseDto> toDto(Set<Authority> entities) {

    return entities.stream()
      .map(entity -> AuthorityCompleteResponseDto.builder()
        .username(entity.getUsername())
        .authority(entity.getAuthority())
        .build())
      .collect(Collectors.toSet()); // Collect into a Set

  }

}
