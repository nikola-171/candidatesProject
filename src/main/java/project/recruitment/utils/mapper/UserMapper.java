package project.recruitment.utils.mapper;

import project.recruitment.model.dto.user.UserDTO;
import project.recruitment.model.dto.user.UserGetDTO;
import project.recruitment.model.entity.UserEntity;

public class UserMapper
{
    public static UserGetDTO toDTO(final UserEntity userEntity)
    {
        return UserGetDTO.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .build();
    }
}
