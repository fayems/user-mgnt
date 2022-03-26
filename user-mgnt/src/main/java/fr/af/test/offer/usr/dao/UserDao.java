package fr.af.test.offer.usr.dao;

import fr.af.test.offer.usr.entity.database.UserDB;
import fr.af.test.offer.usr.exception.DatabaseException;
import fr.af.test.offer.usr.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * DAO layer for UserDB
 */
@Service
@AllArgsConstructor
public class UserDao {

    /** User mapper */
    private final UserMapper userMapper;

    /**
     * Get user's infos
     * @param id user id
     * @return userDB
     */
    public UserDB getUserById(Integer id) {
        return userMapper.getById(id);
    }
    /**
     * Insert or update new user
     * See replace command
     * @param user
     * @return Integer
     * @throws DatabaseException
     */
    public Integer insert(UserDB user) throws DatabaseException {
        try {
            userMapper.insert(user);
            return user.getId();
        } catch (Exception e) {
            throw new DatabaseException(e);
        }
    }
}
