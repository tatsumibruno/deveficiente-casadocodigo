package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovoAutorRequest {
    @Email
    @NotNull
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
