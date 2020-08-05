package deveficiente.casadocodigo.cupom_desconto.api;

import deveficiente.casadocodigo.commons.api.validators.Unique;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NovoCupomDescontoRequest {

    @NotEmpty
    @Unique(field = "codigo", targetClass = CupomDesconto.class, message = "{cupom.ja.cadastrado}")
    private String codigo;
    @NotNull
    @Positive
    private BigDecimal percentualDesconto;
    @Future
    @NotNull
    private LocalDate validoAte;

    public CupomDesconto entity() {
        return new CupomDesconto(codigo, percentualDesconto, validoAte);
    }
}
