package fr.af.test.offer.usr.service;

import fr.af.test.offer.dto.UserDto;
import fr.af.test.offer.usr.dao.UserDao;
import fr.af.test.offer.usr.entity.database.UserDB;
import fr.af.test.offer.usr.exception.ServiceException;
import fr.af.test.offer.usr.exception.UserAlreadyExistsException;
import fr.af.test.offer.usr.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


/**
 * User service
 */
@Service
public class UserService {
    /**
     * UserDao
     */
    private final UserDao userDao;

    /**
     * French adult age
     */
    @Value("${adult.age}")
    private final int adultAge = 18;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Get user's infos if exist in database
     *
     * @param id user id
     * @return UserDto
     * @throws ServiceException ServiceException
     */
    public UserDto getUser(Integer id) throws ServiceException {
        try {
            return UserMapper.INSTANCE.toUserDto(userDao.getUserById(id));
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Save user's infos in database
     */
    public Integer saveUser(UserDto user) throws ServiceException {
        try {
            UserDB userDB = UserMapper.INSTANCE.toUserDB(user);
            userDB.setRegistrationDate(LocalDateTime.now());
           return userDao.insert(userDB);
        } catch (Exception e) {
            if(e.getCause() instanceof DuplicateKeyException) {
                throw new UserAlreadyExistsException("User already exists");
            }
            throw new ServiceException(e);
        }
    }

    public boolean registrationForbidden(UserDto userDto) {
        if (userDto == null) {
            return true;
        }
        LocalDate birthDate = userDto.getBirthday();
        if (birthDate != null && "France".equalsIgnoreCase(userDto.getCountry())) {
            return Period.between(birthDate, LocalDate.now()).getYears() < adultAge;
        } else {
            return true;
        }
    }
}
