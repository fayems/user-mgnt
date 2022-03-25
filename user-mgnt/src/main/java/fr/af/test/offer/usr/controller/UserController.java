package fr.af.test.offer.usr.controller;

import fr.af.test.offer.api.UserApi;
import fr.af.test.offer.dto.ResponseDto;
import fr.af.test.offer.dto.UserDto;
import fr.af.test.offer.usr.exception.ServiceException;
import fr.af.test.offer.usr.exception.UserAlreadyExistsException;
import fr.af.test.offer.usr.service.UserService;
import fr.af.test.offer.usr.utils.MessageError;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 */
@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    public ResponseEntity getUser(Integer id) {
        try {
            UserDto result = userService.getUser(id);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                        .body(ResponseDto.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message("User not found")
                                .build());
            }
            return ResponseEntity.status(HttpStatus.OK.value()).body(result);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .body(ResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity postUser(UserDto userDto, BindingResult result) {
        try {
            if(result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                        .body(ResponseDto.builder()
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .message(MessageError.getError(result))
                                        .build());
            }
            if(userService.registrationForbidden(userDto)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                        .body(ResponseDto.builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .message("Only adult French residents are allowed to create an account!")
                                .build());
            }
            try {
                userService.saveUser(userDto);
            } catch(UserAlreadyExistsException uae) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                        .body(ResponseDto.builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .message("User already exists")
                                .build());
            }
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(ResponseDto.builder()
                            .status(HttpStatus.OK.value())
                            .message("User registered")
                            .build());
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .body(ResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .build());
        }
    }

}
