package fr.af.test.offer.usr.dao;

import fr.af.test.offer.usr.entity.database.UserDB;
import fr.af.test.offer.usr.exception.DatabaseException;
import fr.af.test.offer.usr.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserDao TU
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserDao.class})
public class UserDaoTest {

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test(expected = RuntimeException.class)
    public void getUserByIdTest() throws RuntimeException {
        UserDB userDB = UserDB.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .build();
        when(userMapper.getById(10)).thenReturn(null);
        when(userMapper.getById(null)).thenThrow(new RuntimeException());
        when(userMapper.getById(1)).thenReturn(userDB);
        UserDB result = userDao.getUserById(10);
        assertNull(result);
        result = userDao.getUserById(1);
        assertNotNull(result);
        assertEquals("Jean", result.getName());
        assertEquals("France", result.getCountry());
        userDao.getUserById(null);
    }

    @Test(expected = DatabaseException.class)
    public void insertTest() throws DatabaseException {
        UserDB userDB = new UserDB();
        when(userMapper.insert(any(UserDB.class))).thenReturn(1);
        Integer result = userDao.insert(userDB);
        verify(userMapper, times(1)).insert(any());
        assertEquals(Integer.valueOf(1), result);
        when(userMapper.insert(any(UserDB.class))).thenThrow(new RuntimeException());
        result = userDao.insert(userDB);
        assertNull(result);
    }

}
