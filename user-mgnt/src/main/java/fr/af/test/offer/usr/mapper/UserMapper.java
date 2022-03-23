package fr.af.test.offer.usr.mapper;

import fr.af.test.offer.usr.entity.database.UserDB;
import org.apache.ibatis.annotations.Mapper;

/**
 * User mapper
 */
@Mapper
public interface UserMapper {

    UserDB getById(Integer id);

    Integer insert(UserDB user);
}
