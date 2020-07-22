package deveficiente.casadocodigo.categoria;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.categoria.api.CategoriaDTO;
import deveficiente.casadocodigo.categoria.api.NovaCategoriaRequest;
import deveficiente.casadocodigo.categoria.dominio.Categoria;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
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

import static deveficiente.casadocodigo.TestConstants.PT_BR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Tag("integration")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaIT {

    private static final String API_PATH = "/api/v1/categorias";

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar uma categoria com os dados informados corretamente")
    public void criarCategoria() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(API_PATH)
                .characterEncoding("UTF-8")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new NovaCategoriaRequest("nova categoria")))
                .locale(PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        CategoriaDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsByteArray(), CategoriaDTO.class);
        Assertions.assertNotNull(responseDTO);
        Assertions.assertNotNull(responseDTO.getId());
        Categoria categoria = categoriaRepository.findById(responseDTO.getId()).orElseThrow();

        Assertions.assertEquals("nova categoria", categoria.getNome());
    }

    @Test
    @DisplayName("Ao informar dados inválidos, o sistema não deve cadastrar a categoria")
    public void categoriaInvalida() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(API_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new NovaCategoriaRequest("")))
                .locale(PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @DisplayName("Não deve permitir categorias com nomes duplicados")
    public void categoriaJaExistente() throws Exception {
        categoriaRepository.save(new Categoria("categoria existente"));

        mockMvc.perform(MockMvcRequestBuilders
                .post(API_PATH)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new NovaCategoriaRequest("categoria existente")))
                .locale(PT_BR))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details[0]").value("nome: A informação já existe no sistema"));
    }
}
