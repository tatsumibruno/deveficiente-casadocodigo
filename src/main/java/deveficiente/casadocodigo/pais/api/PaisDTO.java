package deveficiente.casadocodigo.pais.api;

import deveficiente.casadocodigo.pais.dominio.Pais;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaisDTO {
    private UUID id;
    private String nome;

    public static PaisDTO from(Pais pais) {
        return new PaisDTO(pais.getId(), pais.getNome());
    }
}
