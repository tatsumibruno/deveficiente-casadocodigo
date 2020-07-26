package deveficiente.casadocodigo.livro.api;

import com.google.common.collect.Lists;
import deveficiente.casadocodigo.autor.dominio.AutorRepository;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
import deveficiente.casadocodigo.commons.exceptions.RegistroNaoEncontradoException;
import deveficiente.casadocodigo.livro.dominio.Livro;
import deveficiente.casadocodigo.livro.dominio.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public Collection<LivroDTO> listarTodos() {
        return Lists.newArrayList(livroRepository.findAll())
                .stream()
                .map(LivroDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/detalhes")
    public DetalhesLivroResponse detalhes(@PathVariable UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        return DetalhesLivroResponse.from(livro);
    }
}
