package deveficiente.casadocodigo.autor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import deveficiente.casadocodigo.autor.api.AutorDTO;
import deveficiente.casadocodigo.autor.api.NovoAutorRequest;
import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;

import static deveficiente.casadocodigo.TestConstants.PT_BR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Tag("integration")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AutorIT {

    private static final String API_PATH = "/api/v1/autores";

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um autor com os dados informados corretamente")
    public void criarAutor() throws Exception {
        NovoAutorRequest novoAutor = new NovoAutorRequest("teste@gmail.com", "nome do autor", "descrição do autor");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(API_PATH)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .locale(PT_BR)
                .content(objectMapper.writeValueAsString(novoAutor)))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        AutorDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsByteArray(), AutorDTO.class);
        Assertions.assertNotNull(responseDTO);
        Assertions.assertNotNull(responseDTO.getId());
        Autor autorCadastrado = autorRepository.findById(responseDTO.getId()).orElseThrow();

        Assertions.assertEquals("teste@gmail.com", autorCadastrado.getEmail());
        Assertions.assertEquals("nome do autor", autorCadastrado.getNome());
        Assertions.assertEquals("descrição do autor", autorCadastrado.getDescricao());
    }

    @Test
    @DisplayName("Ao informar dados inválidos, o sistema não deve cadastrar o autor")
    public void autorInvalido() throws Exception {
        List<NovoAutorRequest> requisicoesInvalidas = Lists.newArrayList(
                new NovoAutorRequest(null, "nome do autor", "descrição do autor"),
                new NovoAutorRequest("emailinvalido", "nome do autor", "descrição do autor"),
                new NovoAutorRequest("teste@gmail.com", null, "descrição do autor"),
                new NovoAutorRequest("teste@gmail.com", "nome do autor", null),
                new NovoAutorRequest("teste@gmail.com", "nome do autor", Strings.repeat("A", 401))
        );

        for (NovoAutorRequest requisicao : requisicoesInvalidas) {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(API_PATH)
                    .contentType(APPLICATION_JSON_VALUE)
                    .locale(PT_BR)
                    .content(objectMapper.writeValueAsString(requisicao)))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
        }
    }

    @Test
    @DisplayName("Não deve permitir e-mails duplicados")
    public void validacaoEmail() throws Exception {
        Autor autorExistente = new Autor("autor.existente@gmail.com", "Autor existente", "Autor que já existe no banco");
        autorRepository.save(autorExistente);

        NovoAutorRequest novoAutor = new NovoAutorRequest("autor.existente@gmail.com", "nome do autor", "descrição do autor");
        mockMvc.perform(MockMvcRequestBuilders
                .post(API_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .locale(PT_BR)
                .content(objectMapper.writeValueAsString(novoAutor)))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details[0]").value("email: O e-mail já existe cadastrado"));
    }
}
