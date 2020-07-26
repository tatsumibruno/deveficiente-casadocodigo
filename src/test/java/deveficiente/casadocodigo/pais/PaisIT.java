package deveficiente.casadocodigo.pais;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.TestConstants;
import deveficiente.casadocodigo.pais.api.NovoPaisRequest;
import deveficiente.casadocodigo.pais.api.PaisDTO;
import deveficiente.casadocodigo.pais.dominio.Pais;
import deveficiente.casadocodigo.pais.dominio.PaisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@Tag("integration")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PaisIT {

    private static final String API_PATH = "/api/v1/paises";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Ao informar o nome corretamente, o país deve ser cadastrado")
    public void novoPais() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(new NovoPaisRequest("Brasil"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        PaisDTO resposta = objectMapper.readValue(result.getResponse().getContentAsByteArray(), PaisDTO.class);
        Pais paisCadastrado = paisRepository.findById(resposta.getId())
                .orElseThrow();
        Assertions.assertEquals(resposta.getNome(), paisCadastrado.getNome());
    }

    @Test
    @DisplayName("O nome do país deve ser obrigatório")
    public void semNome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(new NovoPaisRequest(null))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("nome: não deve estar vazio"));
    }

    @Test
    @DisplayName("O nome do país deve ser único")
    public void duplicado() throws Exception {
        paisRepository.save(new Pais("Argentina"));

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .locale(TestConstants.PT_BR)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .content(objectMapper.writeValueAsString(new NovoPaisRequest("Argentina"))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("nome: O país informado já existe"));
    }
}
