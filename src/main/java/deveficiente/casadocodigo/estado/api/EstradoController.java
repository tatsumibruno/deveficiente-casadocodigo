package deveficiente.casadocodigo.estado.api;

import deveficiente.casadocodigo.estado.dominio.Estado;
import deveficiente.casadocodigo.estado.dominio.EstadoRepository;
import deveficiente.casadocodigo.pais.dominio.PaisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/estados")
public class EstradoController {

    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;

    public EstradoController(PaisRepository paisRepository, EstadoRepository estadoRepository) {
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> cadastrar(@RequestBody @Valid NovoEstadoRequest novoEstado) {
        Estado estado = novoEstado.entity(paisRepository);
        estadoRepository.save(estado);
        return ResponseEntity.ok(EstadoDTO.from(estado));
    }
}
