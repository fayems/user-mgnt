package fr.af.test.offer.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator implements ConstraintValidator<StringConstraint, String> {
    private String attribut;
    private final Map<String, String> patternList = new HashMap<>();
    private boolean mandatory;
    @Override
    public void initialize(StringConstraint constraintAnnotation) {
        this.attribut = constraintAnnotation.attribut();
        this.mandatory = constraintAnnotation.mandatory();

        this.patternList.put("phone", "^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$");
        this.patternList.put("gender", "^(FEMALE|MALE)$");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return !mandatory;
        }
        String attributValue = this.attribut;
        Pattern pattern = Pattern.compile(this.patternList.get(attributValue));
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
