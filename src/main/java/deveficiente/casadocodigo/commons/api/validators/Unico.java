package deveficiente.casadocodigo.commons.api.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UnicoValidator.class)
@Documented
public @interface Unico {
    String message() default "{registro.ja.existe}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> targetClass();
    String field();
}
