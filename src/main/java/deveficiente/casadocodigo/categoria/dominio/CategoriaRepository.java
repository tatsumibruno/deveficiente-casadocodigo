package deveficiente.casadocodigo.categoria.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<Categoria, UUID> {
    boolean existsByNome(String nome);
}
