package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private UUID id;
    private String email;
    private String nome;
    private String descricao;

    public static AutorDTO from(Autor autor) {
        return new AutorDTO(autor.getId(),
                autor.getEmail(),
                autor.getNome(),
                autor.getDescricao());
    }
}
