package deveficiente.casadocodigo.compra.api;

import deveficiente.casadocodigo.compra.dominio.Compra;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, setterPrefix = "com")
public class CompraDTO {
    private UUID id;
    private String email;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String cnpj;
    private UUID idEstado;
    private String cidade;
    private String cep;
    private String endereco;
    private String complemento;
    private String telefone;
    private BigDecimal total;
    private Set<CompraItemDTO> itens = new HashSet<>();

    public static CompraDTO from(Compra compra) {
        return CompraDTO.builder()
                .comId(compra.getId())
                .comEmail(compra.getEmail())
                .comNome(compra.getNome())
                .comSobrenome(compra.getSobrenome())
                .comCpf(compra.getCpf())
                .comCnpj(compra.getCnpj())
                .comIdEstado(compra.getIdEstado())
                .comCidade(compra.getCidade())
                .comCep(compra.getCep())
                .comEndereco(compra.getEndereco())
                .comComplemento(compra.getComplemento())
                .comTelefone(compra.getTelefone())
                .comTotal(compra.getTotal())
                .comItens(compra.getItens()
                        .stream()
                        .map(item -> new CompraItemDTO(item.getLivro().getId(), item.getLivro().getTitulo(), item.getQuantidade()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
