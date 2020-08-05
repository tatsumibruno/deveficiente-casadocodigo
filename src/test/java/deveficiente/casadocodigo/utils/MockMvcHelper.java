package deveficiente.casadocodigo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveficiente.casadocodigo.TestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

@Component
public class MockMvcHelper {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public ResultActions post(String apiPath, Object request) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(apiPath)
                .locale(TestConstants.PT_BR)
                .characterEncoding(TestConstants.DEFAULT_ENCODING)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public <T> T extractResponse(MvcResult result, Class<T> clazz) throws IOException {
        return objectMapper.readValue(result.getResponse().getContentAsByteArray(), clazz);
    }
}
