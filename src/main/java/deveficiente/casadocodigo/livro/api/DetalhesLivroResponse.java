package deveficiente.casadocodigo.livro.api;

import deveficiente.casadocodigo.livro.dominio.Livro;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DetalhesLivroResponse {

    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private long numeroPaginas;
    private String isbn;
    private String nomeAutor;
    private String descricaoAutor;

    public static DetalhesLivroResponse from(Livro livro) {
        return DetalhesLivroResponse.builder()
                .titulo(livro.getTitulo())
                .resumo(livro.getResumo())
                .sumario(livro.getSumario())
                .preco(livro.getPreco())
                .numeroPaginas(livro.getNumeroPaginas())
                .isbn(livro.getIsbn())
                .nomeAutor(livro.getNomeAutor())
                .descricaoAutor(livro.getDescricaoAutor())
                .build();
    }
}
