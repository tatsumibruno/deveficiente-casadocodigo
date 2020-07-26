package deveficiente.casadocodigo.pais.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaisRepository extends CrudRepository<Pais, UUID> {
}
