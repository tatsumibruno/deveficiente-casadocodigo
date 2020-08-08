package deveficiente.casadocodigo.compra.api;

import deveficiente.casadocodigo.compra.dominio.Compra;
import deveficiente.casadocodigo.compra.dominio.CompraRepository;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDescontoRepository;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/compra")
public class CompraController {

    private final CompraRepository compraRepository;
    private final EstadoRepository estadoRepository;
    private final LivroRepository livroRepository;
    private final CupomDescontoRepository cupomDescontoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CompraDTO> realizarPedido(@RequestBody @Valid CompraRequest request) {
        Compra compra = request.entity(estadoRepository, livroRepository);
        compraRepository.save(compra);
        return ResponseEntity.ok(CompraDTO.from(compra));
    }

    @PostMapping("/{idCompra}/cupom/{codigoCupom}")
    @Transactional
    public ResponseEntity<Void> aplicarDesconto(@PathVariable @NotNull UUID idCompra, @PathVariable @NotEmpty String codigoCupom) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new IllegalArgumentException("compra.nao.encontrada"));
        CupomDesconto cupom = cupomDescontoRepository.findByCodigo(codigoCupom)
                .orElseThrow(() -> new IllegalArgumentException("cupom.nao.encontrado"));
        compra.aplicarDesconto(cupom);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> detalhesCompra(@PathVariable UUID id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("compra.nao.encontrada"));
        return ResponseEntity.ok(CompraDTO.from(compra));
    }
}
