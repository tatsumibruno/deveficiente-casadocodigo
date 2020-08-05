package deveficiente.casadocodigo.cupom_desconto.dominio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "cupom_desconto", uniqueConstraints = @UniqueConstraint(name = "cupom_un", columnNames = "codigo"))
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CupomDesconto {
    @Id
    @GeneratedValue
    private UUID id;
    @NotEmpty
    private String codigo;
    @NotNull
    @Positive
    private BigDecimal percentualDesconto;
    @Future
    @NotNull
    private LocalDate validoAte;

    public CupomDesconto(@NonNull String codigo, @NonNull BigDecimal percentualDesconto, @NonNull LocalDate validoAte) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validoAte = validoAte;
    }
}
