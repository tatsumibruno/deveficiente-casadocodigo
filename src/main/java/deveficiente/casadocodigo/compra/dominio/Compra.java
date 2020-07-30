package deveficiente.casadocodigo.compra.dominio;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Compra {

    private String cliente;
    private BigDecimal preco;
    private LocalDate previsaoEntrega;
    private String informacoesAdicionais;

    public static CompraBuilder builder() {
        return new CompraBuilder(new Compra());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraBuilder {
        private Compra compra;

        public CompraBuilder comInformacoesAdicionais(String informacoesAdicionais) {
            compra.informacoesAdicionais = informacoesAdicionais;
            return this;
        }

        public CompraBuilderComCliente comCliente(@NonNull String cliente) {
            compra.cliente = cliente;
            return new CompraBuilderComCliente(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraBuilderComCliente {
        private Compra compra;

        public CompraBuilderComPrevisaoEntrega comPreco(@NonNull BigDecimal preco) {
            compra.preco = preco;
            return new CompraBuilderComPrevisaoEntrega(compra);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CompraBuilderComPrevisaoEntrega {
        private Compra compra;

        public CompraBuilderPronto comPrevisaoEntrega(@NonNull LocalDate previsaoEntrega) {
            compra.previsaoEntrega = previsaoEntrega;
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
