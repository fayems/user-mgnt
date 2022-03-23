package fr.af.test.offer.usr.entity.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDB {

    /**
     * Primary key
     */
    private Integer id;

    /**
     * User name
     */
    private String name;

    /**
     * User birthday
     */
    @Past
    private LocalDate birthday;

    /**
     * User country of residence
     */
    private String country;

    /**
     * User phone number
     */
    private String phone;

    private LocalDateTime registrationDate;

    /**
     * User gender
     */
    String gender;

}
