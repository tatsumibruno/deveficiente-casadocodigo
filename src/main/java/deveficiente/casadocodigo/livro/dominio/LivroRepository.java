package deveficiente.casadocodigo.livro.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends CrudRepository<Livro, UUID> {

    @Override
//    @Query("select l from Livro l join fetch l.autor join fetch l.categoria where l.id = :id")
    Optional<Livro> findById(UUID id);

}
