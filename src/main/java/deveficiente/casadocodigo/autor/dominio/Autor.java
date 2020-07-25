package deveficiente.casadocodigo.autor.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Entity
@ToString
@EqualsAndHashCode(of = "email")
public class Autor {
    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    private String nome;
    private String descricao;

    @Deprecated
    Autor() {
    }

    public Autor(@Email @NotNull String email, @NotEmpty String nome, @NotEmpty @Size(max = 400) String descricao) {
        this.email = email;
        this.nome = nome;
        this.descricao = descricao;
    }
}
