package fr.af.test.offer.usr.utils;

import fr.af.test.offer.dto.ErrorUserDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for contructing errors messages when user dto datas are precessed
 */
public class MessageError {

    /**
     * Add a private constructor to hide the implicit public one
     */
    private MessageError() { }

    public static List<ErrorUserDto> getError(BindingResult result) {
        List<ErrorUserDto> errorUserList = new ArrayList<>();
        result.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldError = ((FieldError) error).getField(); // Voir la doc de spring
                errorUserList.add(new ErrorUserDto(error.getDefaultMessage(), fieldError));
            }
        });
        return errorUserList;
    }
}
