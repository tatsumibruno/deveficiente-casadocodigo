package deveficiente.casadocodigo.autor.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AutorRepository extends CrudRepository<Autor, UUID> {
    boolean existsByEmail(String email);
}
