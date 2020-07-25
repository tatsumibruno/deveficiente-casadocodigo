package deveficiente.casadocodigo.livro.api;

import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
import deveficiente.casadocodigo.livro.dominio.Livro;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/livros")
public class LivroController {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;

    public LivroController(LivroRepository livroRepository, CategoriaRepository categoriaRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;
    }

    @PostMapping
    public ResponseEntity<LivroDTO> cadastrar(@RequestBody @Valid NovoLivroRequest novoLivro) {
        Livro livro = novoLivro.entity(categoriaRepository, autorRepository);
        livroRepository.save(livro);
        return ResponseEntity.ok(LivroDTO.from(livro));
    }
}
