package deveficiente.casadocodigo.compra.dominio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompraRepository extends CrudRepository<Compra, UUID> {

    @Override
    @Query("select compra from Compra compra join fetch compra.estado estado join fetch estado.pais")
    Optional<Compra> findById(UUID uuid);
}
