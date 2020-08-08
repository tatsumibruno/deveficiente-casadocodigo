package deveficiente.casadocodigo.cupom_desconto.dominio;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static deveficiente.casadocodigo.commons.utils.FormatosData.DIA_MES_ANO;

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
    @Setter
    private boolean utilizado;

    public CupomDesconto(@NonNull String codigo, @NonNull BigDecimal percentualDesconto, @NonNull LocalDate validoAte) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.validoAte = validoAte;
    }

    public boolean estaVencidoHoje() {
        return validoAte.isBefore(LocalDate.now());
    }

    public String getValidadeFormatada() {
        return validoAte.format(DateTimeFormatter.ofPattern(DIA_MES_ANO));
    }
}
