package deveficiente.casadocodigo.compra.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.categoria.dominio.Categoria;
import deveficiente.casadocodigo.compra.dominio.Compra;
import deveficiente.casadocodigo.estado.dominio.Estado;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.livro.dominio.Livro;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import deveficiente.casadocodigo.pais.dominio.Pais;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

class CompraRequestTest {

    private static final Livro livro1 = Livro.builder()
            .autor(new Autor("autor@email.com", "Autor", "Descrição Autor"))
            .categoria(new Categoria("Categoria"))
            .dataPublicacao(LocalDate.now().plusMonths(1))
            .isbn("1")
            .numeroPaginas(200)
            .preco(BigDecimal.valueOf(25.99))
            .resumo("Resumo")
            .sumario("Sumário")
            .titulo("Título")
            .build();

    private static final Livro livro2 = Livro.builder()
            .autor(new Autor("autor@email.com", "Autor", "Descrição Autor"))
            .categoria(new Categoria("Categoria"))
            .dataPublicacao(LocalDate.now().plusMonths(1))
            .isbn("2")
            .numeroPaginas(200)
            .preco(BigDecimal.valueOf(30.17))
            .resumo("Resumo")
            .sumario("Sumário")
            .titulo("Título")
            .build();

    private LivroRepository livroRepository;
    private EstadoRepository estadoRepository;

    @BeforeEach
    void setup() {
        livro1.setId(UUID.randomUUID());
        livro2.setId(UUID.randomUUID());
        livroRepository = Mockito.mock(LivroRepository.class);
        estadoRepository = Mockito.mock(EstadoRepository.class);
        Mockito.when(livroRepository.findById(Mockito.eq(livro1.getId()))).thenReturn(Optional.of(livro1));
        Mockito.when(livroRepository.findById(Mockito.eq(livro2.getId()))).thenReturn(Optional.of(livro2));
        Estado estado = new Estado("Estado", new Pais("País"));
        Mockito.when(estadoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(estado));
    }

    @Test
    @DisplayName("O valor total informado deve ser igual ao calculado")
    void valorTotalOk() {
        CompraRequest request = CompraRequest.builder()
                .cep("19800-000")
                .cidade("Assis")
                .cnpj("68.098.857/0001-20")
                .complemento("N/A")
                .email("teste@teste.com.br")
                .endereco("Rua 1, 1")
                .idEstado(UUID.randomUUID())
                .nome("Nome")
                .sobrenome("Sobrenome")
                .telefone("123456789")
                .totalCompra(new BigDecimal("82.15"))
                .itens(Sets.newLinkedHashSet(
                        new CompraItemRequest(livro1.getId(), 2),
                        new CompraItemRequest(livro2.getId(), 1)
                ))
                .build();
        Compra compra = request.entity(estadoRepository, livroRepository);
        Assertions.assertNotNull(compra);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o valor total informado não bater com o calculado")
    void valorTotalErrado() {
        CompraRequest request = CompraRequest.builder()
                .cep("19800-000")
                .cidade("Assis")
                .cnpj("68.098.857/0001-20")
                .complemento("N/A")
                .email("teste@teste.com.br")
                .endereco("Rua 1, 1")
                .idEstado(UUID.randomUUID())
                .nome("Nome")
                .sobrenome("Sobrenome")
                .telefone("123456789")
                .totalCompra(new BigDecimal("142.5"))
                .itens(Sets.newLinkedHashSet(
                        new CompraItemRequest(livro1.getId(), 2),
                        new CompraItemRequest(livro2.getId(), 3)
                ))
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> request.entity(estadoRepository, livroRepository));
        Assertions.assertEquals("valor.compra.diferente.calculado", exception.getMessage());
    }
}
