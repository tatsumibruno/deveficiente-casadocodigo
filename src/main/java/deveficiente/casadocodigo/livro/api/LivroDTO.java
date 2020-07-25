package deveficiente.casadocodigo.livro.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LivroDTO {
    private UUID id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private long numeroPaginas;
    private String isbn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;
    private UUID idCategoria;
    private String nomeCategoria;
    private UUID idAutor;
    private String nomeAutor;

    public static LivroDTO from(Livro livro) {
        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .resumo(livro.getResumo())
                .sumario(livro.getSumario())
                .preco(livro.getPreco())
                .numeroPaginas(livro.getNumeroPaginas())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .idCategoria(livro.getIdCategoria())
                .nomeCategoria(livro.getNomeCategoria())
                .idAutor(livro.getIdAutor())
                .nomeAutor(livro.getNomeAutor())
                .build();
    }
}
