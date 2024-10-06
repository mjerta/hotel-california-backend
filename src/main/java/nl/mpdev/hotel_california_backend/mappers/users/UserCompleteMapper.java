package nl.mpdev.hotel_california_backend.mappers.users;

import nl.mpdev.hotel_california_backend.dtos.users.request.UserCompleteRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserProfileRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserIdRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserLimitedResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.request.UserRegisterLimitedRequestDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserProfileResponseDto;
import nl.mpdev.hotel_california_backend.mappers.authorities.AuthoritiesCompleteMapper;
import nl.mpdev.hotel_california_backend.mappers.profiles.ProfileCompleteMapper;
import nl.mpdev.hotel_california_backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserCompleteMapper {

  private final AuthoritiesCompleteMapper authoritiesCompleteMapper;
  private final ProfileCompleteMapper profileCompleteMapper;

  public UserCompleteMapper(AuthoritiesCompleteMapper authoritiesCompleteMapper, ProfileCompleteMapper profileCompleteMapper) {
    this.authoritiesCompleteMapper = authoritiesCompleteMapper;
    this.profileCompleteMapper = profileCompleteMapper;
  }

  public User toEntity(UserIdRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .id(requestDto.getId())
      .build();
  }

  public User toEntity(UserRegisterLimitedRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .username(requestDto.getUsername())
      .password(requestDto.getPassword())
      .build();
  }

  public User toEntity(UserCompleteRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .username(requestDto.getUsername())
      .password(requestDto.getPassword())
      .authorities(authoritiesCompleteMapper.toEntity(requestDto.getAuthority()))
      .build();
  }

  public User toEntity(UserProfileRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .id(requestDto.getId())
      .profile(profileCompleteMapper.toEntity(requestDto.getProfile()))
      .build();
  }

  public UserLimitedResponseDto toUserLimitedResponse(User entity) {
    if (entity == null) return null;
    UserLimitedResponseDto.UserLimitedResponseDtoBuilder userLimitedResponseDtoBuilder = UserLimitedResponseDto.builder();
    userLimitedResponseDtoBuilder.id(entity.getId());
    userLimitedResponseDtoBuilder.username(entity.getUsername());
    return userLimitedResponseDtoBuilder.build();
  }
  public UserProfileResponseDto toUserProfileResponse(User entity) {
    if (entity == null) return null;
    UserProfileResponseDto.UserProfileResponseDtoBuilder userProfileResponseDtoBuilder = UserProfileResponseDto.builder();
    userProfileResponseDtoBuilder.id(entity.getId());
    userProfileResponseDtoBuilder.username(entity.getUsername());
    userProfileResponseDtoBuilder.profile(entity.getProfile());
    return userProfileResponseDtoBuilder.build();
  }

  public UserCompleteResponseDto toDto(User entity) {
    if (entity == null) return null;
    UserCompleteResponseDto.UserCompleteResponseDtoBuilder userCompleteResponseDtoBuilder = UserCompleteResponseDto.builder();
    userCompleteResponseDtoBuilder.id(entity.getId());
    userCompleteResponseDtoBuilder.username(entity.getUsername());
    userCompleteResponseDtoBuilder.authority(authoritiesCompleteMapper.toDto(entity.getAuthorities()));
    return userCompleteResponseDtoBuilder.build();
  }


}
