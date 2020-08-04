package deveficiente.casadocodigo.compra.api;

import lombok.*;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompraItemDTO {
    private UUID idLivro;
    private String tituloLivro;
    private int quantidade;
}
