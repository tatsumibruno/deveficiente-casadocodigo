package deveficiente.casadocodigo.estado.api;

import deveficiente.casadocodigo.commons.api.validators.Unique;
import deveficiente.casadocodigo.estado.dominio.Estado;
import deveficiente.casadocodigo.pais.dominio.Pais;
import deveficiente.casadocodigo.pais.dominio.PaisRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NovoEstadoRequest {

    @NotEmpty
    @Unique(targetClass = Estado.class, field = "nome", message = "{estado.ja.existe}")
    private String nome;
    @NotNull
    private UUID idPais;

    public Estado entity(PaisRepository paisRepository) {
        Pais pais = paisRepository.findById(idPais)
                .orElseThrow(() -> new IllegalArgumentException("pais.nao.encontrado"));
        return new Estado(nome, pais);
    }
}
