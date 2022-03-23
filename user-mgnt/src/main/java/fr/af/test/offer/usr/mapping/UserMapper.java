package fr.af.test.offer.usr.mapping;

import fr.af.test.offer.dto.UserDto;
import fr.af.test.offer.usr.entity.database.UserDB;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserDB toUserDB(UserDto userDto);
    public abstract UserDto toUserDto(UserDB userDB);
}
