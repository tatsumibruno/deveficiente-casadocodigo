package deveficiente.casadocodigo.infra.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ValidationErrorHandlers {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageError handleValidationErrors(MethodArgumentNotValidException exception, Locale locale) {
        MessageError error = new MessageError(messageSource.getMessage("validation.errors.title", null, locale));
        exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .map(fieldError -> {
                    String mensagem = messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), locale);
                    return String.format("%s: %s", fieldError.getField(), mensagem);
                })
                .forEach(error::addDetail);
        return error;
    }
}
