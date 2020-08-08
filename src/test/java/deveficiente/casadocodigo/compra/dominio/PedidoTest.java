package deveficiente.casadocodigo.compra.dominio;

import deveficiente.casadocodigo.commons.exceptions.BusinessException;
import deveficiente.casadocodigo.cupom_desconto.dominio.CupomDesconto;
import deveficiente.casadocodigo.livro.dominio.Livro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag("unit")
class PedidoTest {

    private final Livro livro1 = Livro.builder()
            .preco(BigDecimal.valueOf(33.27))
            .build();

    private final Livro livro2 = Livro.builder()
            .preco(BigDecimal.valueOf(25.68))
            .build();

    @Test
    @DisplayName("O cálculo do valor do pedido deve bater com os itens")
    void valorTotal() {
        BigDecimal valorTotalEsperado = BigDecimal.valueOf(92.22);
        Pedido pedido = new Pedido(valorTotalEsperado);
        pedido.addItem(livro1, 2);
        pedido.addItem(livro2, 1);
        BigDecimal valorTotalCalculado = pedido.getValorTotalCalculado();
        Assertions.assertEquals(0, valorTotalEsperado.compareTo(valorTotalCalculado));
    }

    @Test
    @DisplayName("A comparação do valor total deve funcionar independente da quantidade de zeros decimais")
    void comparacaoValorTotal() {
        Pedido pedido = new Pedido(BigDecimal.valueOf(92.2200));
        pedido.addItem(livro1, 2);
        pedido.addItem(livro2, 1);
        Assertions.assertTrue(pedido.valorTotalIgualAoCalculado());
    }

    @Test
    @DisplayName("A comparação do valor total deve retornar 'false' caso o valor informado no pedido seja diferente da soma dos produtos")
    void comparacaoValorTotalNegativa() {
        Pedido pedido = new Pedido(BigDecimal.valueOf(93));
        pedido.addItem(livro1, 2);
        pedido.addItem(livro2, 1);
        Assertions.assertFalse(pedido.valorTotalIgualAoCalculado());
    }

    @Test
    @DisplayName("Deve validar ao tentar aplicar o desconto de um cupom vencido")
    void descontoCupomVencido() {
        Pedido pedido = new Pedido(BigDecimal.ONE);
        CupomDesconto cupom = new CupomDesconto("ABC", BigDecimal.TEN, LocalDate.now().minusDays(1));
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> pedido.aplicarDesconto(cupom));
        Assertions.assertEquals("cupom.vencido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar ao tentar aplicar um cupom de desconto que ja foi utilizado")
    void descontoCupomUtilizado() {
        Pedido pedido = new Pedido(BigDecimal.valueOf(33.27));
        pedido.addItem(livro1, 1);
        CupomDesconto cupom = new CupomDesconto("ABC", BigDecimal.TEN, LocalDate.now().plusDays(1));
        pedido.aplicarDesconto(cupom);
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> pedido.aplicarDesconto(cupom));
        Assertions.assertEquals("cupom.ja.utilizado", exception.getMessage());
    }

    @Test
    @DisplayName("Após aplicar o desconto, o cupom deve ser marcado como 'já utilizado")
    void setaUtilizadoAposAplicarDesconto() {
        Pedido pedido = new Pedido(BigDecimal.valueOf(33.27));
        pedido.addItem(livro1, 1);
        CupomDesconto cupom = new CupomDesconto("ABC", BigDecimal.TEN, LocalDate.now().plusDays(1));
        pedido.aplicarDesconto(cupom);
        Assertions.assertTrue(cupom.isUtilizado());
    }

    @Test
    @DisplayName("Percentual de desconto deve ser aplicado corretamente")
    void calculoValorDesconto() {
        Pedido pedido = new Pedido(BigDecimal.valueOf(33.27));
        pedido.addItem(livro1, 1);
        CupomDesconto cupom = new CupomDesconto("ABC", BigDecimal.TEN, LocalDate.now().plusDays(1));
        pedido.aplicarDesconto(cupom);
        Assertions.assertEquals(0, pedido.getValorTotal().compareTo(BigDecimal.valueOf(29.94)));
    }
}
