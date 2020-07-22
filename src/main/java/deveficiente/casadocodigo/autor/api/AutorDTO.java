package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import lombok.Data;

import java.util.UUID;

@Data
public class AutorDTO {
    private final UUID id;
    private final String email;
    private final String nome;
    private final String descricao;

    public static AutorDTO from(Autor autor) {
        return new AutorDTO(autor.getId(),
                autor.getEmail(),
                autor.getNome(),
                autor.getDescricao());
    }
}
