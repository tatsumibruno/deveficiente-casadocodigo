package deveficiente.casadocodigo.compra.api;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompraItemRequest {
    @NotNull
    private UUID idLivro;
    @Min(value = 1)
    private int quantidade;
}
