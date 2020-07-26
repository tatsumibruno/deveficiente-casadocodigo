package deveficiente.casadocodigo.pais.api;

import deveficiente.casadocodigo.commons.api.validators.Unique;
import deveficiente.casadocodigo.pais.dominio.Pais;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NovoPaisRequest {
    @NotEmpty
    @Unique(targetClass = Pais.class, field = "nome", message = "{pais.ja.existe}")
    private String nome;

    public Pais entity() {
        return new Pais(nome);
    }
}
