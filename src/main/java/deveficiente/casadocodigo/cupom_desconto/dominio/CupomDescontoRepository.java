package deveficiente.casadocodigo.cupom_desconto.dominio;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CupomDescontoRepository extends CrudRepository<CupomDesconto, UUID> {
    Optional<CupomDesconto> findByCodigo(String codigoCupom);
}
