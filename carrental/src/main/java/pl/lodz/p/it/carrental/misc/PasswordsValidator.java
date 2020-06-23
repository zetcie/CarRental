package pl.lodz.p.it.carrental.misc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsValidator implements ConstraintValidator<PasswordsMatching, PasswordAndRePasswordProvider> {
   public void initialize(PasswordsMatching constraint) {
   }

   public boolean isValid(PasswordAndRePasswordProvider object, ConstraintValidatorContext context) {
      boolean isValid = true;
      if (object == null) {
         isValid = false;
      }
      if (object.getRePassword() == null || object.getPassword() == null) {
         isValid = false;
      }

      if(isValid) {
         isValid = object.getPassword().equals(object.getRePassword());
      }

      if(!isValid){
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                 .addPropertyNode( "rePassword" ).addConstraintViolation();
      }

      return isValid;
   }
}
