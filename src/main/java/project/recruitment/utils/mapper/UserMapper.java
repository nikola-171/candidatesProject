package project.recruitment.utils.mapper;

import project.recruitment.model.Role;
import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.model.entity.UserEntity;

import java.util.Arrays;

public class UserMapper
{
    public static UserGetDTO toDTO(final UserEntity userEntity)
    {
        return UserGetDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .roles(userEntity.getRoles())
                .build();
    }

    public static UserEntity toEntity(final UserDTO userDTO)
    {
        return UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(Arrays.asList(Role.USER))
                .build();
    }
}
