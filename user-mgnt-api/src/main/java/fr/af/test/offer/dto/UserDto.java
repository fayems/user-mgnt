package fr.af.test.offer.dto;

import fr.af.test.offer.validator.StringConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * User Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor @Builder
public class UserDto {
    /**
     * User name
     */
    @ApiModelProperty(value = "name", example = "Jean-Paul")
    @NotBlank(message = "Name may not be blank")
    private String name;

    /**
     * User birthday
     */
    @ApiModelProperty(value= "birthday", example = "[2002, 2, 28]")
    @Past(message = "Birthday must by a valid date in the past")
    private LocalDate birthday;

    /**
     * User country of residence
     */
    @ApiModelProperty(value = "country", example = "France")
    @NotBlank(message = "Country may not be blank")
    private String country;

    /**
     * User phone number
     */
    @ApiModelProperty(value = "phone", example = "0612457890")
    @StringConstraint(attribut = "phone", mandatory = false, message = "Phone must by a valid french number")
    private String phone;

    /**
     * User gender
     */
    @ApiModelProperty(value = "gender", example = "FEMALE")
    @StringConstraint(attribut = "gender", mandatory = false, message = "Gender only take FEMALE or MALE value")
    private String gender;
}
