package deveficiente.casadocodigo.livro.dominio;

import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.categoria.dominio.Categoria;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro", uniqueConstraints = {
        @UniqueConstraint(name = "livro_titulo_un", columnNames = "titulo"),
        @UniqueConstraint(name = "livro_isbn_un", columnNames = "isbn")
})
@Builder
@ToString(of = {"id", "isbn", "titulo", "preco"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Livro {
    @Id
    @Getter
    @GeneratedValue
    private UUID id;
    @Getter
    @NotEmpty
    private String titulo;
    @Getter
    @NotNull
    @Size(min = 1, max = 500)
    private String resumo;
    @Getter
    private String sumario;
    @Getter
    @NotNull
    @Min(20)
    private BigDecimal preco;
    @Getter
    @NotNull
    @Min(100)
    private long numeroPaginas;
    @Getter
    @NotEmpty
    private String isbn;
    @Getter
    @Future
    private LocalDate dataPublicacao;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "livro_categoria_fk"))
    private Categoria categoria;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", foreignKey = @ForeignKey(name = "livro_autor_fk"))
    private Autor autor;

    @Deprecated
    Livro() {
    }

    public static class LivroBuilder {
        //Não faz sentido passar o ID na criação, sobrescrevendo lombok
        private LivroBuilder id() {
            return this;
        }
    }

    public UUID getIdCategoria() {
        return categoria.getId();
    }

    public String getNomeCategoria() {
        return categoria.getNome();
    }

    public UUID getIdAutor() {
        return autor.getId();
    }

    public String getNomeAutor() {
        return autor.getNome();
    }
}
