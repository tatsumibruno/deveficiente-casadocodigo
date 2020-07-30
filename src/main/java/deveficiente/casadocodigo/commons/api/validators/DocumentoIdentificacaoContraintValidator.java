package deveficiente.casadocodigo.commons.api.validators;

import com.google.common.base.Strings;
import deveficiente.casadocodigo.commons.api.DocumentoIdentificacaoRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentoIdentificacaoContraintValidator implements ConstraintValidator<DocumentoIdentificacaoConstraint, DocumentoIdentificacaoRequest> {

    @Override
    public boolean isValid(DocumentoIdentificacaoRequest value, ConstraintValidatorContext context) {
        boolean cnpjPreenchido = !Strings.isNullOrEmpty(value.getCnpj());
        boolean cpfPreenchido = !Strings.isNullOrEmpty(value.getCpf());
        return cpfPreenchido ^ cnpjPreenchido;
    }
}
