package deveficiente.casadocodigo.categoria.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Entity
@ToString
@EqualsAndHashCode(of = "nome")
public class Categoria {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private String nome;

    public Categoria(@NotNull String nome) {
        this.nome = nome;
    }
}
