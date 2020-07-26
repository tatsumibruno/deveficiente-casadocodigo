package deveficiente.casadocodigo.estado.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EstadoRepository extends CrudRepository<Estado, UUID> {
}
