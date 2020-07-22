package deveficiente.casadocodigo.categoria.api;

import deveficiente.casadocodigo.categoria.dominio.Categoria;
import deveficiente.casadocodigo.categoria.dominio.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> cadastrar(@RequestBody @Valid NovaCategoriaRequest novaCategoria) {
        Categoria categoria = categoriaRepository.save(novaCategoria.entity());
        return ResponseEntity.ok(CategoriaDTO.from(categoria));
    }
}
