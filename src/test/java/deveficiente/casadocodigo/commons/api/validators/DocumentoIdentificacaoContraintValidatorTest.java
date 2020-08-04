package deveficiente.casadocodigo.commons.api.validators;

import deveficiente.casadocodigo.commons.api.DocumentoIdentificacaoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentoIdentificacaoContraintValidatorTest {

    private DocumentoIdentificacaoContraintValidator validator = new DocumentoIdentificacaoContraintValidator();

    @Test
    @DisplayName("CPF e CNPJ preenchidos não deve ser válido")
    void t1() {
        boolean documentoIdentificacaoValido = validator.isValid(new DocumentoIdentificacaoRequestTest("1", "1"), null);
        Assertions.assertFalse(documentoIdentificacaoValido);
    }

    @Test
    @DisplayName("CPF e CNPJ em branco não deve ser válido")
    void t2() {
        boolean documentoIdentificacaoValido = validator.isValid(new DocumentoIdentificacaoRequestTest("", ""), null);
        Assertions.assertFalse(documentoIdentificacaoValido);
    }

    @Test
    @DisplayName("CPF e CNPJ nulos não deve ser válido")
    void t3() {
        boolean documentoIdentificacaoValido = validator.isValid(new DocumentoIdentificacaoRequestTest(null, null), null);
        Assertions.assertFalse(documentoIdentificacaoValido);
    }

    @Test
    @DisplayName("Somente CPF informado deve ser válido")
    void t4() {
        boolean documentoIdentificacaoValido = validator.isValid(new DocumentoIdentificacaoRequestTest("1", null), null);
        Assertions.assertTrue(documentoIdentificacaoValido);
    }

    @Test
    @DisplayName("Somente CNPJ informado deve ser válido")
    void t5() {
        boolean documentoIdentificacaoValido = validator.isValid(new DocumentoIdentificacaoRequestTest(null, "1"), null);
        Assertions.assertTrue(documentoIdentificacaoValido);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    class DocumentoIdentificacaoRequestTest implements DocumentoIdentificacaoRequest {
        private String cpf;
        private String cnpj;
    }
}
