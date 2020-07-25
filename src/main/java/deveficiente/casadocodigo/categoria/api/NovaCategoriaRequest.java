package deveficiente.casadocodigo.categoria.api;

import deveficiente.casadocodigo.categoria.dominio.Categoria;
import deveficiente.casadocodigo.commons.api.validators.Unico;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@EqualsAndHashCode(of = "nome")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NovaCategoriaRequest {
    @NotEmpty
    @Unico(targetClass = Categoria.class, field = "nome")
    private String nome;

    public Categoria entity() {
        return new Categoria(nome);
    }
}
