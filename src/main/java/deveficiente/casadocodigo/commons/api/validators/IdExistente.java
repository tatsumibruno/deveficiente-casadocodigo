package deveficiente.casadocodigo.commons.api.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdExistenteValidator.class)
@Documented
public @interface IdExistente {
    String message() default "{identificador.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field() default "id";
    Class<?> targetClass();
}
