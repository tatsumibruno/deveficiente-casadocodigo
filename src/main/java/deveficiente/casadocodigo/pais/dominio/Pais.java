package deveficiente.casadocodigo.pais.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Pais {

    @Id
    @GeneratedValue
    private UUID id;
    @NotEmpty
    private String nome;

    @Deprecated
    Pais() {
    }

    public Pais(@NotEmpty @NonNull String nome) {
        this.nome = nome;
    }
}
