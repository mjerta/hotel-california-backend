package nl.mpdev.hotel_california_backend.mappers.users;

import nl.mpdev.hotel_california_backend.dtos.users.request.*;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserCompleteResponseDto;
import nl.mpdev.hotel_california_backend.dtos.users.response.UserLimitedResponseDto;
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

  public User toEntity(UserNameRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .username(requestDto.getUsername())
      .build();
  }

  public User toEntity(UserRegisterLimitedRequestDto requestDto) {
    if (requestDto == null) return null;
    User.UserBuilder builder = User.builder();
    if (requestDto.getUsername() != null) {
      builder.username(requestDto.getUsername());
    }
    if (requestDto.getPassword() != null) {
      builder.password(requestDto.getPassword());
    }
    if (requestDto.getProfile() != null) {
      builder.profile(profileCompleteMapper.toEntity(requestDto.getProfile()));
    }

    return builder.build();
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
      .profile(profileCompleteMapper.toEntity(requestDto.getProfile()))
      .build();
  }

  public User toEntity(UserLoginRequestDto requestDto) {
    if (requestDto == null) return null;
    return User.builder()
      .username(requestDto.getUsername())
      .password(requestDto.getPassword())
      .build();
  }

  public UserLimitedResponseDto toUserLimitedResponse(User entity) {
    if (entity == null) return null;
    UserLimitedResponseDto.UserLimitedResponseDtoBuilder userLimitedResponseDtoBuilder = UserLimitedResponseDto.builder();
    userLimitedResponseDtoBuilder.username(entity.getUsername());
    return userLimitedResponseDtoBuilder.build();
  }

  public UserProfileResponseDto toUserProfileResponse(User entity) {
    if (entity == null) return null;
    UserProfileResponseDto.UserProfileResponseDtoBuilder userProfileResponseDtoBuilder = UserProfileResponseDto.builder();
    userProfileResponseDtoBuilder.username(entity.getUsername());
    if (entity.getProfile() != null) {
      userProfileResponseDtoBuilder.profile(profileCompleteMapper.toDto(entity.getProfile()));
    }
    return userProfileResponseDtoBuilder.build();
  }

  public UserCompleteResponseDto toDto(User entity) {
    if (entity == null) return null;
    UserCompleteResponseDto.UserCompleteResponseDtoBuilder userCompleteResponseDtoBuilder = UserCompleteResponseDto.builder();
    if (entity.getUsername() != null) {
      userCompleteResponseDtoBuilder.username(entity.getUsername());
    }
    if (entity.getAuthorities() != null) {
      userCompleteResponseDtoBuilder.authority(authoritiesCompleteMapper.toDto(entity.getAuthorities()));
    }
    if (entity.getProfile() != null) {
      userCompleteResponseDtoBuilder.profile(profileCompleteMapper.toDto(entity.getProfile()));
    }
    return userCompleteResponseDtoBuilder.build();
  }
}
