package deveficiente.casadocodigo.compra.dominio;

import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Embeddable
public class Documento implements Serializable {

    @NotEmpty
    @Column(name = "documento")
    protected String valor;
    @NotNull
    @Column(name = "tipo_documento")
    protected TipoDocumento tipo;

    public Documento(@NonNull TipoDocumento tipo, @NonNull String valor) {
        this.valor = valor;
        this.tipo = tipo;
    }
}
