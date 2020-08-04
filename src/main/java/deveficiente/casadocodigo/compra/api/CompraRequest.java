package deveficiente.casadocodigo.compra.api;

import com.google.common.base.Strings;
import deveficiente.casadocodigo.commons.api.DocumentoIdentificacaoRequest;
import deveficiente.casadocodigo.commons.api.validators.DocumentoIdentificacaoConstraint;
import deveficiente.casadocodigo.compra.dominio.Compra;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.livro.dominio.Livro;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@ToString
@DocumentoIdentificacaoConstraint
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompraRequest implements DocumentoIdentificacaoRequest {

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
    @NotNull
    private BigDecimal totalCompra;
    @Size(min = 1)
    @Valid
    private Set<CompraItemRequest> itens;

    public Set<CompraItemRequest> getItens() {
        return new HashSet<>(itens);
    }

    public Compra entity(EstadoRepository estadoRepository, LivroRepository livroRepository) {
        Compra compra = compraBuilder()
                .comEmail(email)
                .comNome(nome)
                .comSobrenome(sobrenome)
                .comCep(cep)
                .comEndereco(endereco)
                .comComplemento(complemento)
                .comTelefone(telefone)
                .comEstado(estadoRepository.findById(idEstado).orElseThrow(() -> new IllegalArgumentException("estado.nao.encontrado")))
                .comCidade(cidade)
                .comValorTotal(totalCompra)
                .build();
        itens.forEach(item -> {
            Livro livro = livroRepository.findById(item.getIdLivro()).orElseThrow(() -> new IllegalArgumentException("livro.nao.encontrado"));
            compra.addItem(livro, item.getQuantidade());
        });
        if (!compra.valorTotalIgualAoCalculado()) {
            throw new IllegalArgumentException("valor.compra.diferente.calculado");
        }
        return compra;
    }

    private Compra.CompraEmailBuilder compraBuilder() {
        return compraComCpf() ? Compra.builder().comCpf(cpf) : Compra.builder().comCnpj(cnpj);
    }

    private boolean compraComCpf() {
        return !Strings.isNullOrEmpty(cpf);
    }
}
