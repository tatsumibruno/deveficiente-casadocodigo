package deveficiente.casadocodigo.pais.api;

import deveficiente.casadocodigo.pais.dominio.Pais;
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
@RequestMapping(path = "/api/v1/paises")
public class PaisController {

    private PaisRepository paisRepository;

    public PaisController(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    @PostMapping
    public ResponseEntity<PaisDTO> cadastrar(@RequestBody @Valid NovoPaisRequest novoPais) {
        Pais pais = novoPais.entity();
        paisRepository.save(pais);
        return ResponseEntity.ok(PaisDTO.from(pais));
    }
}
