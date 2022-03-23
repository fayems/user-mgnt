package fr.af.test.offer.api.validator;

import fr.af.test.offer.validator.StringConstraint;
import fr.af.test.offer.validator.StringValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringValidatorTest {

    @Mock
    private StringConstraint stringConstraint;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void testPhone() {
        when(stringConstraint.mandatory()).thenReturn(false);
        when(stringConstraint.attribut()).thenReturn("phone");
        StringValidator stringValidator = new StringValidator();
        stringValidator.initialize(stringConstraint);
        assertTrue(stringValidator.isValid("0612457890", constraintValidatorContext));
        assertTrue(stringValidator.isValid(null, constraintValidatorContext));
        assertFalse(stringValidator.isValid("12345", constraintValidatorContext));
    }

    @Test
    public void testGender() {
        when(stringConstraint.mandatory()).thenReturn(false);
        when(stringConstraint.attribut()).thenReturn("gender");
        StringValidator stringValidator = new StringValidator();
        stringValidator.initialize(stringConstraint);
        assertTrue(stringValidator.isValid("FEMALE", constraintValidatorContext));
        assertTrue(stringValidator.isValid("MALE", constraintValidatorContext));
        assertTrue(stringValidator.isValid(null, constraintValidatorContext));
        assertFalse(stringValidator.isValid("F", constraintValidatorContext));
    }

}
