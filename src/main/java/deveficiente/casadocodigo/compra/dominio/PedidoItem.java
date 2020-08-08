package deveficiente.casadocodigo.compra.dominio;

import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PedidoItem implements Serializable {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", foreignKey = @ForeignKey(name = "pedido_item_livro_fk"))
    private Livro livro;
    @NotNull
    @Min(1)
    private int quantidade;

    BigDecimal getValorCalculado() {
        return livro.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
