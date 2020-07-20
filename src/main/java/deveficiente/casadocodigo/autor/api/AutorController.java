package deveficiente.casadocodigo.autor.api;

import deveficiente.casadocodigo.autor.api.validators.AutorComEmailRepetidoValidator;
import deveficiente.casadocodigo.autor.dominio.Autor;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static deveficiente.casadocodigo.autor.api.AutorDTO.from;

@Validated
@RestController
@RequestMapping(path = "/api/v1/autores")
public class AutorController {

    private final AutorRepository repositorio;
    private final AutorComEmailRepetidoValidator emailRepetidoValidator;

    public AutorController(AutorRepository repositorio, AutorComEmailRepetidoValidator emailRepetidoValidator) {
        this.repositorio = repositorio;
        this.emailRepetidoValidator = emailRepetidoValidator;
    }

    @InitBinder
    void initValidator(WebDataBinder binder) {
        binder.addValidators(emailRepetidoValidator);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> cadastrar(@RequestBody @Valid NovoAutorRequest novoAutor) {
        Autor autor = novoAutor.entity();
        repositorio.save(autor);
        return ResponseEntity.ok(from(autor));
    }
}
