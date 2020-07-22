package deveficiente.casadocodigo.categoria.api;

import deveficiente.casadocodigo.categoria.dominio.Categoria;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoriaDTO {
    private final UUID id;
    private final String nome;

    public static CategoriaDTO from(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(),  categoria.getNome());
    }
}
