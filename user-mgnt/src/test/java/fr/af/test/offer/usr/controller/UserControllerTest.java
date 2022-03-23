package fr.af.test.offer.usr.controller;

import fr.af.test.offer.dto.ErrorUserDto;
import fr.af.test.offer.dto.ResponseDto;
import fr.af.test.offer.dto.UserDto;
import fr.af.test.offer.usr.exception.ServiceException;
import fr.af.test.offer.usr.exception.UserAlreadyExistsException;
import fr.af.test.offer.usr.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Test UserController
 */
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private BindingResult result;

    private UserDto userDto;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void init() {
        userDto = UserDto.builder()
                .name("Jean")
                .birthday(LocalDate.parse("2003-01-25", formatter))
                .country("France")
                .gender("MALE")
                .build();
        MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    public void getUserTest() throws ServiceException {
        // Not found test
        when(userService.getUser(anyInt())).thenReturn(null);
        ResponseEntity<UserDto> response = userController.getUser(1);
        verify(userService, times(1)).getUser(anyInt());
        assertEquals(404, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(404).message("User not found").build(), response.getBody());
        // Ok test
        when(userService.getUser(anyInt())).thenReturn(userDto);
        response = userController.getUser(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDto, response.getBody());
        // Internal error test
        when(userService.getUser(anyInt())).thenThrow(new ServiceException("An internal error occur"));
        response = userController.getUser(1);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(500).message("An internal error occur").build(), response.getBody());
    }

    @Test
    public void postUserTest() throws ServiceException {
        // Bad reques test
        List<ErrorUserDto> validationErrors = List.of(ErrorUserDto.builder()
                .field("birthday")
                .message("Birthday must be a valid date in the past")
                .build());
        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(List.of(new FieldError("UserDto", "birthday", "Birthday must be a valid date in the past")));
        ResponseEntity response = userController.postUser(userDto, result);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(validationErrors, response.getBody());
        // Forbidden test (not adult french)
        when(result.hasErrors()).thenReturn(false);
        when(userService.registrationForbidden(any(UserDto.class))).thenReturn(true);
        response = userController.postUser(userDto, result);
        assertEquals(403, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(403)
                .message("Only adult French residents are allowed to create an account!")
                .build(), response.getBody());
        // Forbidden test (user already exists)
        when(result.hasErrors()).thenReturn(false);
        when(userService.registrationForbidden(any(UserDto.class))).thenReturn(false);
        when(userService.saveUser(any(UserDto.class))).thenThrow(new UserAlreadyExistsException("User already exists"));
        response = userController.postUser(userDto, result);
        assertEquals(403, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(403)
                .message("User already exists")
                .build(), response.getBody());
        // Ok test
        when(result.hasErrors()).thenReturn(false);
        when(userService.registrationForbidden(any(UserDto.class))).thenReturn(false);
        when(userService.saveUser(any(UserDto.class))).thenReturn(1);
        response = userController.postUser(userDto, result);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(200)
                .message("User registered")
                .build(), response.getBody());
        // Internal error test
        when(result.hasErrors()).thenReturn(false);
        when(userService.registrationForbidden(any(UserDto.class))).thenReturn(false);
        when(userService.saveUser(any(UserDto.class))).thenThrow(new ServiceException("An internal error occur"));
        response = userController.postUser(userDto, result);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(ResponseDto.builder().status(500).message("An internal error occur").build(), response.getBody());
    }

}
