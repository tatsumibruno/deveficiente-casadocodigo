package deveficiente.casadocodigo.livro.api;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Tag("unit")
@SpringBootTest
class NovoLivroRequestTest {

    @Autowired
    private LivroController livroController;

    @Test
    @DisplayName("O título do livro deve ser obrigatório")
    void tituloObrigatorio() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .titulo(null)
                .build());
    }

    @Test
    @DisplayName("O resumo do livro deve ter no máximo 500 caracteres")
    void resumoMax500() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .resumo(Strings.repeat("A", 501))
                .build());
    }

    @Test
    @DisplayName("O resumo do livro deve ser obrigatório")
    void resumoObrigatorio() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .resumo(null)
                .build());
    }

    @Test
    @DisplayName("O preço deve ser obrigatório")
    void precoObrigatorio() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .preco(null)
                .build());
    }

    @Test
    @DisplayName("O preço deve ser maior ou igual à 20")
    void precoMin20() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .preco(BigDecimal.valueOf(19.99))
                .build());
    }

    @Test
    @DisplayName("O número de páginas deve ser maior ou igual à 100")
    void paginasMin100() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .numeroPaginas(99)
                .build());
    }

    @Test
    @DisplayName("O ISBN deve ser obrigatório")
    void isbnObrigatorio() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .isbn(null)
                .build());
    }

    @Test
    @DisplayName("Data de publicação deve estar no futuro")
    void dataPublicacao() {
        assertConstraintViolation(novoLivroCompletoBuilder()
                .dataPublicacao(LocalDate.now().minusDays(1))
                .build());
    }

    private void assertConstraintViolation(NovoLivroRequest request) {
        Assertions.assertThrows(ConstraintViolationException.class, () -> livroController.cadastrar(request));
    }

    private NovoLivroRequest.NovoLivroRequestBuilder novoLivroCompletoBuilder() {
        return NovoLivroRequest.builder()
                .idAutor(UUID.randomUUID())
                .idCategoria(UUID.randomUUID())
                .titulo("título")
                .sumario("sumário")
                .resumo("resumo")
                .preco(BigDecimal.valueOf(30))
                .numeroPaginas(100)
                .isbn("isbn")
                .dataPublicacao(LocalDate.now().plusMonths(1));
    }

}
