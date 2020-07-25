package deveficiente.casadocodigo.livro.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LivroRepository extends CrudRepository<Livro, UUID> {
}
