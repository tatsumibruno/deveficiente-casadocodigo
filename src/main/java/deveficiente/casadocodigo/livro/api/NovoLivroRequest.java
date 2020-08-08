package deveficiente.casadocodigo.livro.api;

import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
import deveficiente.casadocodigo.commons.api.validators.Unique;
import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@ToString
@EqualsAndHashCode(of = "isbn")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NovoLivroRequest {
    @NotEmpty
    @Unique(field = "titulo", targetClass = Livro.class)
    private String titulo;
    @NotNull
    @Size(min = 1, max = 500)
    private String resumo;
    private String sumario;
    @NotNull
    @Min(20)
    private BigDecimal preco;
    @NotNull
    @Min(100)
    private long numeroPaginas;
    @NotEmpty
    @Unique(field = "isbn", targetClass = Livro.class)
    private String isbn;
    @Future
    private LocalDate dataPublicacao;
    @NotNull
    private UUID idCategoria;
    @NotNull
    private UUID idAutor;

    public Livro entity(CategoriaRepository categoriaRepository, AutorRepository autorRepository) {
        return Livro.builder()
                .titulo(titulo)
                .resumo(resumo)
                .sumario(sumario)
                .preco(preco)
                .numeroPaginas(numeroPaginas)
                .isbn(isbn)
                .dataPublicacao(dataPublicacao)
                .categoria(categoriaRepository.findById(idCategoria).orElseThrow(() -> new IllegalArgumentException("categoria.nao.encontrada")))
                .autor(autorRepository.findById(idAutor).orElseThrow(() -> new IllegalArgumentException("autor.nao.encontrado")))
                .build();
    }
}
