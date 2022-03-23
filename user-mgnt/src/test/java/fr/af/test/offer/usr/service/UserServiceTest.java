package fr.af.test.offer.usr.service;

import fr.af.test.offer.dto.UserDto;
import fr.af.test.offer.usr.dao.UserDao;
import fr.af.test.offer.usr.entity.database.UserDB;
import fr.af.test.offer.usr.exception.DatabaseException;
import fr.af.test.offer.usr.exception.ServiceException;
import fr.af.test.offer.usr.exception.UserAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Test UserService service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserService userService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test(expected = ServiceException.class)
    public void getUserTest() throws ServiceException {
        UserDB userDB = UserDB.builder()
                .id(1)
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .gender("MALE")
                .build();
        UserDto userDto = UserDto.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .gender("MALE")
                .build();
        when(userDao.getUserById(anyInt())).thenReturn(userDB);
        UserDto result = userService.getUser(1);
        verify(userDao, times(1)).getUserById(anyInt());
        assertEquals(userDto, result);
        when(userDao.getUserById(anyInt())).thenThrow(new RuntimeException());
        result = userService.getUser(1);
        assertNull(result);
    }

    @Test(expected = ServiceException.class)
    public void saveUserTest() throws ServiceException, DatabaseException {
        UserDto userDto = UserDto.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .gender("MALE")
                .build();
        when(userDao.insert(any(UserDB.class))).thenReturn(1);
        Integer result = userService.saveUser(userDto);
        verify(userDao, times(1)).insert(any(UserDB.class));
        assertEquals(1, result);
        when(userDao.insert(any(UserDB.class))).thenThrow(new DatabaseException());
        result = userService.saveUser(userDto);
        assertNull(result);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void saveUserExistsTest() throws ServiceException, DatabaseException {
        UserDto userDto = UserDto.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .gender("MALE")
                .build();
        when(userDao.insert(any(UserDB.class))).thenThrow(new DatabaseException(new DuplicateKeyException("User already exists")));
        Integer result = userService.saveUser(userDto);
        assertNull(result);
    }

    @Test
    public void registrationForbiddenTest() {
        assertTrue(userService.registrationForbidden(null));
        UserDto userDto = UserDto.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("Senegal")
                .gender("MALE")
                .build();
        assertTrue(userService.registrationForbidden(userDto));
        userDto.setBirthday(null);
        userDto.setCountry("France");
        assertTrue(userService.registrationForbidden(userDto));
        userDto.setBirthday(LocalDate.parse("2010-01-25", formatter));
        userDto.setCountry("France");
        assertTrue(userService.registrationForbidden(userDto));
        userDto.setBirthday(LocalDate.parse("2001-01-25", formatter));
        userDto.setCountry("France");
        assertFalse(userService.registrationForbidden(userDto));
    }

}
