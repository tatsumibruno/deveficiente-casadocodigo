package deveficiente.casadocodigo.estado;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.TestConstants;
import deveficiente.casadocodigo.estado.api.EstadoDTO;
import deveficiente.casadocodigo.estado.api.NovoEstadoRequest;
import deveficiente.casadocodigo.estado.dominio.Estado;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.pais.dominio.Pais;
import deveficiente.casadocodigo.pais.dominio.PaisRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.UUID;

@Tag("integration")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class EstadoIT {

    private static final String API_PATH = "/api/v1/estados";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    private Pais pais;

    @BeforeEach
    public void setup() {
        pais = paisRepository.save(new Pais("Brasil"));
    }

    @Test
    @DisplayName("Ao informar os dados corretamente, o estado deve ser cadastrado")
    public void novoEstado() throws Exception {
        NovoEstadoRequest estado = new NovoEstadoRequest("São Paulo", pais.getId());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(estado))
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        EstadoDTO resposta = objectMapper.readValue(result.getResponse().getContentAsByteArray(), EstadoDTO.class);
        Estado estadoCriado = estadoRepository.findById(resposta.getId()).orElseThrow();
        Assertions.assertEquals("São Paulo", estadoCriado.getNome());
        Assertions.assertEquals("Brasil", resposta.getPais());
    }

    @Test
    @DisplayName("O nome deve ser obrigatório")
    public void nomeObrigatorio() throws Exception {
        NovoEstadoRequest estado = new NovoEstadoRequest(null, pais.getId());
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(estado))
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("nome: não deve estar vazio"));
    }

    @Test
    @DisplayName("O nome deve ser único")
    public void nomeUnico() throws Exception {
        estadoRepository.save(new Estado("São Paulo", pais));
        NovoEstadoRequest estado = new NovoEstadoRequest("São Paulo", pais.getId());
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(estado))
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("nome: O estado informado já existe"));
    }

    @Test
    @DisplayName("O país deve ser obrigatório")
    public void paisObrigatorio() throws Exception {
        NovoEstadoRequest estado = new NovoEstadoRequest("São Paulo", null);
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(estado))
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("idPais: não deve ser nulo"));
    }

    @Test
    @DisplayName("O país deve existir")
    public void paisDeveExistir() throws Exception {
        NovoEstadoRequest estado = new NovoEstadoRequest("São Paulo", UUID.randomUUID());
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(estado))
                .locale(TestConstants.PT_BR))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("País não encontrado"));
    }
}
