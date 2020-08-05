package deveficiente.casadocodigo.cupom_desconto.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CupomDescontoDTO {
    private UUID id;
    private String codigo;
    private BigDecimal percentualDesconto;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate validoAte;

    public static CupomDescontoDTO from(CupomDesconto cupomDesconto) {
        return new CupomDescontoDTO(cupomDesconto.getId(),
                cupomDesconto.getCodigo(),
                cupomDesconto.getPercentualDesconto(),
                cupomDesconto.getValidoAte());
    }
}
