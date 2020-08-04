package deveficiente.casadocodigo.compra.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CompraRepository extends CrudRepository<Compra, UUID> {
}
