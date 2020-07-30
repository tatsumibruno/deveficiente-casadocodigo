package deveficiente.casadocodigo.compra.api;

import deveficiente.casadocodigo.commons.api.DocumentoIdentificacaoRequest;
import deveficiente.casadocodigo.commons.api.validators.DocumentoIdentificacaoConstraint;
import deveficiente.casadocodigo.commons.api.validators.IdExistente;
import deveficiente.casadocodigo.compra.dominio.Compra;
import deveficiente.casadocodigo.estado.dominio.Estado;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@ToString
@DocumentoIdentificacaoConstraint
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagamentoRequest implements DocumentoIdentificacaoRequest {

    @Email
    @NotNull
    private String email;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String sobrenome;
    @CPF
    private String cpf;
    @CNPJ
    private String cnpj;
    @IdExistente(targetClass = Estado.class)
    private UUID idEstado;
    @NotEmpty
    private String cidade;
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "{cep.invalido}")
    private String cep;
    @NotEmpty
    private String endereco;
    @NotEmpty
    private String complemento;
    @NotEmpty
    private String telefone;

    public static void main(String[] args) {
//       new Compra();
//       new Compra(null, null, null, null);
       Compra.builder()
               .comCliente(null);
    }
}
