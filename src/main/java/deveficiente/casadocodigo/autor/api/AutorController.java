package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static deveficiente.casadocodigo.autor.api.AutorDTO.from;

@Validated
@RestController
@RequestMapping(path = "/api/v1/autores")
public class AutorController {

    private final AutorRepository repositorio;

    public AutorController(AutorRepository repositorio) {
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<AutorDTO> cadastrar(@RequestBody @Valid NovoAutorRequest novoAutor) {
        Autor autor = novoAutor.entity();
        repositorio.save(autor);
        return ResponseEntity.ok(from(autor));
    }
}
