package deveficiente.casadocodigo.livro.api;

import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LivroDTO {
    private UUID id;
    private String titulo;

    public static LivroDTO from(Livro livro) {
        return new LivroDTO(livro.getId(), livro.getTitulo());
    }
}
