package deveficiente.casadocodigo.infra.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ValidationErrorHandlers {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageError handleValidationErrors(MethodArgumentNotValidException exception, Locale locale) {
        MessageError responseError = new MessageError(messageSource.getMessage("validation.errors.title", null, locale));
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String mensagem = messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), locale);
                responseError.addDetail(String.format("%s: %s", fieldError.getField(), mensagem));
            } else {
                responseError.addDetail(messageSource.getMessage(error.getCode(), error.getArguments(), locale));
            }
        }
        return responseError;
    }
}