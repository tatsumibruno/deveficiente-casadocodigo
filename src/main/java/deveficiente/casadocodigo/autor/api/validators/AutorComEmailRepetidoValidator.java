package deveficiente.casadocodigo.autor.api.validators;

import deveficiente.casadocodigo.autor.api.NovoAutorRequest;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AutorComEmailRepetidoValidator implements Validator {

    private final AutorRepository autorRepository;

    public AutorComEmailRepetidoValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoAutorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        NovoAutorRequest autor = (NovoAutorRequest) o;
        if (autorRepository.existsByEmail(autor.getEmail())) {
            errors.rejectValue("email", "email.ja.cadastrado", new Object[]{autor.getEmail()}, "E-mail já cadastrado");
        }
    }
}
