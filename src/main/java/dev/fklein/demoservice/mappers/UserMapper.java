package dev.fklein.demoservice.mappers;

import dev.fklein.demoservice.dto.UserMsDto;
import dev.fklein.demoservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target="userId")
    @Mapping(source = "email", target="emailAddress")
    @Mapping(source = "role", target="group")
    UserMsDto userToUserDto(User entity);

    List<UserMsDto> usersToUserDtos(List<User> entities);
}
