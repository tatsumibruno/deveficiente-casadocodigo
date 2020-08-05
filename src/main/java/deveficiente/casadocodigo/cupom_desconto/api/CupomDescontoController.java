package deveficiente.casadocodigo.cupom_desconto.api;

import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDescontoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/cupons")
public class CupomDescontoController {

    private final CupomDescontoRepository cupomDescontoRepository;

    public CupomDescontoController(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    @PostMapping
    public ResponseEntity<CupomDescontoDTO> cadastrar(@RequestBody @Valid NovoCupomDescontoRequest novoCupom) {
        CupomDesconto cupomDesconto = novoCupom.entity();
        cupomDescontoRepository.save(cupomDesconto);
        return ResponseEntity.ok(CupomDescontoDTO.from(cupomDesconto));
    }
}
