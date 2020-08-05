package deveficiente.casadocodigo.cupom_desconto;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.cupom_desconto.api.CupomDescontoDTO;
import deveficiente.casadocodigo.cupom_desconto.api.NovoCupomDescontoRequest;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDescontoRepository;
import deveficiente.casadocodigo.utils.MockMvcHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Tag("integration")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class CupomDescontoIT {

    private static final String API_PATH = "/api/v1/cupons";

    @Autowired
    private MockMvcHelper mockMvcHelper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    @Test
    @DisplayName("Cadastrar um cupom com os dados informados corretamente")
    void cadastrar() throws Exception {
        LocalDate validade = LocalDate.now().plusDays(10);
        NovoCupomDescontoRequest request = new NovoCupomDescontoRequest("CUPOM1", BigDecimal.valueOf(10), validade);
        MvcResult result = mockMvcHelper.post(API_PATH, request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        CupomDescontoDTO response = mockMvcHelper.extractResponse(result, CupomDescontoDTO.class);
        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(response.getId()).orElseThrow();
        Assertions.assertEquals("CUPOM1", cupomDesconto.getCodigo());
        Assertions.assertEquals(BigDecimal.valueOf(10), cupomDesconto.getPercentualDesconto());
        Assertions.assertEquals(validade, cupomDesconto.getValidoAte());
    }

    @Test
    @DisplayName("O código do cupom deve ser único")
    void cupomUnico() throws Exception {
        cupomDescontoRepository.save(new CupomDesconto("CUPOM1", BigDecimal.valueOf(20), LocalDate.now().plusDays(1)));
        NovoCupomDescontoRequest request = new NovoCupomDescontoRequest("CUPOM1", BigDecimal.valueOf(10), LocalDate.now().plusDays(10));
        mockMvcHelper.post(API_PATH, request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("codigo: Cupom já cadastrado"));
    }

    @Test
    @DisplayName("O percentual de desconto deve ser maior que 0")
    void descontoPositivo() throws Exception {
        NovoCupomDescontoRequest request = new NovoCupomDescontoRequest("CUPOM1", BigDecimal.valueOf(-10), LocalDate.now().plusDays(10));
        mockMvcHelper.post(API_PATH, request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("percentualDesconto: deve ser maior que 0"));
    }

    @Test
    @DisplayName("Data de validade do cupom deve estar no futuro")
    void validadeNoFuturo() throws Exception {
        NovoCupomDescontoRequest request = new NovoCupomDescontoRequest("CUPOM1", BigDecimal.valueOf(10), LocalDate.now().minusDays(1));
        mockMvcHelper.post(API_PATH, request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[0]").value("validoAte: deve ser uma data futura"));
    }
}
