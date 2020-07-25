package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.commons.api.validators.Unico;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
@EqualsAndHashCode(of = "email")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NovoAutorRequest {
    @Email
    @NotNull
    @Unico(targetClass = Autor.class, field = "email", message = "{email.ja.cadastrado}")
    private String email;
    @NotEmpty
    private String nome;
    @NotEmpty
    @Size(min = 1, max = 400)
    private String descricao;

    public Autor entity() {
        return new Autor(email, nome, descricao);
    }
}
