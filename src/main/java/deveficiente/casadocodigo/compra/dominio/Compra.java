package deveficiente.casadocodigo.compra.dominio;

import deveficiente.casadocodigo.estado.dominio.Estado;
import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@ToString(exclude = "pedido")
public class Compra {

    @Id
    @GeneratedValue
    private UUID id;
    @Email
    @Getter
    @NotNull
    private String email;
    @Getter
    @NotEmpty
    private String nome;
    @Getter
    @NotEmpty
    private String sobrenome;
    @Getter
    @NotEmpty
    private String cidade;
    @Getter
    @NotNull
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "{cep.invalido}")
    private String cep;
    @Getter
    @NotEmpty
    private String endereco;
    @Getter
    @NotEmpty
    private String complemento;
    @Getter
    @NotEmpty
    private String telefone;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", foreignKey = @ForeignKey(name = "compra_estado_fk"))
    private Estado estado;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "compra", cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "compra_pedido_fk"))
    private Pedido pedido;
    @CPF
    @Getter
    private String cpf;
    @CNPJ
    @Getter
    private String cnpj;

    /**
     * Utilizar builder
     */
    @Deprecated
    Compra() {
    }

    public void addItem(@NonNull Livro livro, int quantidade) {
        this.pedido.addItem(livro, quantidade);
    }

    public UUID getIdEstado() {
        return estado.getId();
    }

    public BigDecimal getTotal() {
        return pedido.getValorTotal();
    }

    public static CompraBuilder builder() {
        return new CompraBuilder(new Compra());
    }

    public Set<PedidoItem> getItens() {
        return pedido.getItens();
    }

    public boolean valorTotalIgualAoCalculado() {
        return pedido.valorTotalIgualAoCalculado();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraBuilder {
        private Compra compra;

        public CompraEmailBuilder comCpf(String cpf) {
            compra.cpf = cpf;
            return new CompraEmailBuilder(compra);
        }

        public CompraEmailBuilder comCnpj(String cnpj) {
            compra.cnpj = cnpj;
            return new CompraEmailBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraEmailBuilder {
        private Compra compra;

        public CompraNomeBuilder comEmail(String email) {
            compra.email = email;
            return new CompraNomeBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraNomeBuilder {
        private Compra compra;

        public CompraSobrenomeBuilder comNome(String nome) {
            compra.nome = nome;
            return new CompraSobrenomeBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraSobrenomeBuilder {
        private Compra compra;

        public CompraCepBuilder comSobrenome(String sobrenome) {
            compra.sobrenome = sobrenome;
            return new CompraCepBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraCepBuilder {
        private Compra compra;

        public CompraEnderecoBuilder comCep(String cep) {
            compra.cep = cep;
            return new CompraEnderecoBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraEnderecoBuilder {
        private Compra compra;

        public CompraComplementoBuilder comEndereco(String endereco) {
            compra.endereco = endereco;
            return new CompraComplementoBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraComplementoBuilder {
        private Compra compra;

        public CompraTelefoneBuilder comComplemento(String complemento) {
            compra.complemento = complemento;
            return new CompraTelefoneBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraTelefoneBuilder {
        private Compra compra;

        public CompraEstadoBuilder comTelefone(String telefone) {
            compra.telefone = telefone;
            return new CompraEstadoBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraEstadoBuilder {
        private Compra compra;

        public CompraCidadeBuilder comEstado(Estado estado) {
            compra.estado = estado;
            return new CompraCidadeBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraCidadeBuilder {
        private Compra compra;

        public CompraValorBuilder comCidade(String cidade) {
            compra.cidade = cidade;
            return new CompraValorBuilder(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraValorBuilder {
        private Compra compra;

        public CompraBuilderPronto comValorTotal(BigDecimal valorTotal) {
            compra.pedido = new Pedido(compra, valorTotal);
            return new CompraBuilderPronto(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraBuilderPronto {
        private Compra compra;

        public Compra build() {
            return compra;
        }
    }
}
