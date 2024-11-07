package nl.mpdev.hotel_california_backend.mappers.profiles;

import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.request.ProfileIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.profiles.response.ProfileCompleteResponseDto;
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

  public Profile toEntity(ProfileIdRequestDto responseDto) {
    return Profile.builder()
      .id(responseDto.getId())
      .build();
  }

  public ProfileCompleteResponseDto toDto(Profile entity) {
    return ProfileCompleteResponseDto.builder()
      .id(entity.getId())
      .firstName(entity.getFirstName())
      .lastName(entity.getLastName())
      .phoneNumber(entity.getPhoneNumber())
      .address(entity.getAddress())
      .points(entity.getPoints())
      .build();
  }
}
