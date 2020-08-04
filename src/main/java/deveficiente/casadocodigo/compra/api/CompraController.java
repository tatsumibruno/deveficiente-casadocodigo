package deveficiente.casadocodigo.compra.api;

import deveficiente.casadocodigo.compra.dominio.Compra;
import deveficiente.casadocodigo.compra.dominio.CompraRepository;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/pagamento")
public class CompraController {

    private final CompraRepository compraRepository;
    private final EstadoRepository estadoRepository;
    private final LivroRepository livroRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CompraDTO> realizarPagamento(@RequestBody @Valid CompraRequest request) {
        Compra compra = request.entity(estadoRepository, livroRepository);
        compraRepository.save(compra);
        return ResponseEntity.ok(CompraDTO.from(compra));
    }
}
