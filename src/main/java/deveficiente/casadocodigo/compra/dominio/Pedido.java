package deveficiente.casadocodigo.compra.dominio;

import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@ToString(exclude = "compra")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Pedido {

    @Id
    @GeneratedValue
    private UUID id;
    @MapsId
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    private Compra compra;
    @Getter
    @NotNull
    private BigDecimal valorTotal;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "pedido_item")
    private Set<PedidoItem> itens = new HashSet<>();

    Pedido(@NonNull Compra compra, @NonNull BigDecimal valorTotal) {
        this.compra = compra;
        this.valorTotal = valorTotal;
    }

    public Set<PedidoItem> getItens() {
        return new HashSet<>(itens);
    }

    public void addItem(@NonNull Livro livro, int quantidade) {
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
}
