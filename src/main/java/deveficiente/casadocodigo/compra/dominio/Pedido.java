package deveficiente.casadocodigo.compra.dominio;

import deveficiente.casadocodigo.commons.exceptions.BusinessException;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.math.BigDecimal.valueOf;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class Pedido {

    @Id
    @GeneratedValue
    private UUID id;
    @Getter
    @NotNull
    private BigDecimal valorTotal;
    private boolean descontoAplicado;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "pedido_item", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<PedidoItem> itens = new ArrayList<>();

    Pedido(@NonNull BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    Set<PedidoItem> getItens() {
        return new HashSet<>(itens);
    }

    void addItem(@NonNull Livro livro, int quantidade) {
        itens.add(new PedidoItem(livro, quantidade));
    }

    boolean valorTotalIgualAoCalculado() {
        BigDecimal valorTotalCalculado = getValorTotalCalculado();
        return valorTotal.compareTo(valorTotalCalculado) == 0;
    }

    BigDecimal getValorTotalCalculado() {
        return itens.stream()
                .map(PedidoItem::getValorCalculado)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void aplicarDesconto(@NonNull CupomDesconto cupom) {
        if (cupom.estaVencidoHoje()) {
            throw BusinessException.of("cupom.vencido", cupom.getCodigo(), cupom.getValidadeFormatada());
        }
        if (cupom.isUtilizado()) {
            throw BusinessException.of("cupom.ja.utilizado", cupom.getCodigo());
        }
        BigDecimal valorDesconto = this.valorTotal
                .multiply(cupom.getPercentualDesconto())
                .divide(valueOf(100), RoundingMode.HALF_UP);
        this.valorTotal = this.valorTotal.subtract(valorDesconto);
        cupom.setUtilizado(true);
        this.descontoAplicado = true;
    }
}
