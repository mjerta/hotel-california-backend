package nl.mpdev.hotel_california_backend.mappers.profiles;

import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.ProfileCompleteResponseDto;
import nl.mpdev.hotel_california_backend.models.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileCompleteMapper {

  public Profile toEntity(ProfileCompleteRequestDto requestDto) {
    return  Profile.builder()
      .firstName(requestDto.getFirstName())
      .lastName(requestDto.getLastName())
      .phoneNumber(requestDto.getPhoneNumber())
      .address(requestDto.getAddress())
      .points(requestDto.getPoints())
      .build();
  }

  public ProfileCompleteResponseDto toDto(Profile entity) {
    return ProfileCompleteResponseDto.builder()
      .firstName(entity.getFirstName())
      .lastName(entity.getLastName())
      .phoneNumber(entity.getPhoneNumber())
      .address(entity.getAddress())
      .points(entity.getPoints())
      .build();
  }
}
