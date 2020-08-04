package deveficiente.casadocodigo.livro;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.TestConstants;
import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import deveficiente.casadocodigo.categoria.dominio.Categoria;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
import deveficiente.casadocodigo.livro.api.LivroDTO;
import deveficiente.casadocodigo.livro.api.NovoLivroRequest;
import deveficiente.casadocodigo.livro.dominio.Livro;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LivroIT {

    private static final String URI = "/api/v1/livros";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    private Categoria categoria;
    private Autor autor;

    @BeforeEach
    void setup() {
        categoria = categoriaRepository.save(new Categoria("categoria"));
        autor = autorRepository.save(new Autor("autor@gmail.com", "autor", "descricao autor"));
    }

    @Test
    @DisplayName("Ao informar os dados corretamente, o sistema deve registrar o novo livro")
    void novoLivro() throws Exception {
        NovoLivroRequest request = NovoLivroRequest.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("isbn")
                .numeroPaginas(200)
                .preco(BigDecimal.valueOf(100))
                .resumo("resumo")
                .titulo("titulo")
                .idCategoria(categoria.getId())
                .idAutor(autor.getId())
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        LivroDTO response = objectMapper.readValue(result.getResponse().getContentAsByteArray(), LivroDTO.class);
        Livro livroCriado = livroRepository.findById(response.getId()).orElseThrow();

        Assertions.assertEquals(categoria.getId(), livroCriado.getIdCategoria());
        Assertions.assertEquals("categoria", livroCriado.getNomeCategoria());
    }

    @Test
    @DisplayName("O título do livro deve ser único")
    void tituloRepetido() throws Exception {
        Livro livroExistente = Livro.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("123")
                .numeroPaginas(100)
                .preco(BigDecimal.valueOf(100))
                .sumario("sumário")
                .resumo("resumo")
                .titulo("título")
                .autor(autor)
                .categoria(categoria)
                .build();
        livroRepository.save(livroExistente);

        NovoLivroRequest request = NovoLivroRequest.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("456")
                .numeroPaginas(200)
                .preco(BigDecimal.valueOf(100))
                .resumo("resumo")
                .titulo("título")
                .idCategoria(categoria.getId())
                .idAutor(autor.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details[0]").value("titulo: A informação já existe no sistema"));
    }

    @Test
    @DisplayName("O ISBN do livro deve ser único")
    void isbnRepetido() throws Exception {
        Livro livroExistente = Livro.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("123")
                .numeroPaginas(100)
                .preco(BigDecimal.valueOf(100))
                .sumario("sumário")
                .resumo("resumo")
                .titulo("título")
                .autor(autor)
                .categoria(categoria)
                .build();
        livroRepository.save(livroExistente);

        NovoLivroRequest request = NovoLivroRequest.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("123")
                .numeroPaginas(200)
                .preco(BigDecimal.valueOf(100))
                .resumo("resumo")
                .titulo("outro título")
                .idCategoria(categoria.getId())
                .idAutor(autor.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details[0]").value("isbn: A informação já existe no sistema"));
    }

    @Test
    @DisplayName("O livro deve ter um autor existente")
    void autorNaoExiste() throws Exception {
        NovoLivroRequest request = NovoLivroRequest.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("123")
                .numeroPaginas(200)
                .preco(BigDecimal.valueOf(100))
                .resumo("resumo")
                .titulo("título")
                .idCategoria(categoria.getId())
                .idAutor(UUID.randomUUID())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.details[0]").value("Autor não encontrado"));
    }

    @Test
    @DisplayName("O livro deve ter uma categoria existente")
    void categoriaNaoExiste() throws Exception {
        NovoLivroRequest request = NovoLivroRequest.builder()
                .dataPublicacao(LocalDate.now().plusMonths(1))
                .isbn("123")
                .numeroPaginas(200)
                .preco(BigDecimal.valueOf(100))
                .resumo("resumo")
                .titulo("título")
                .idCategoria(UUID.randomUUID())
                .idAutor(autor.getId())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.details[0]").value("Categoria não encontrada"));
    }
}
