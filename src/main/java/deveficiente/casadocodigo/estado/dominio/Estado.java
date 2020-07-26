package deveficiente.casadocodigo.estado.dominio;

import deveficiente.casadocodigo.pais.dominio.Pais;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Estado {

    @Id
    @GeneratedValue
    private UUID id;
    @NotEmpty
    private String nome;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id", foreignKey = @ForeignKey(name = "estado_pais_fk"))
    private Pais pais;

    @Deprecated
    Estado() {
    }

    public Estado(@NotEmpty String nome, @NotNull Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }
}
