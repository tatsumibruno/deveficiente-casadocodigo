package deveficiente.casadocodigo.compra.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/pagamento")
public class PagamentoController {

    @PostMapping
    public void realizar(@RequestBody @Valid PagamentoRequest pagamento) {
        System.out.println(pagamento);
    }
}
