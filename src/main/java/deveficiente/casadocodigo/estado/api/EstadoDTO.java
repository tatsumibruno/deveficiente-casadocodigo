package deveficiente.casadocodigo.estado.api;

import deveficiente.casadocodigo.estado.dominio.Estado;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EstadoDTO {
    private UUID id;
    private String nome;
    private String pais;

    public static EstadoDTO from(Estado estado) {
        return new EstadoDTO(estado.getId(), estado.getNome(), estado.getPais().getNome());
    }
}
