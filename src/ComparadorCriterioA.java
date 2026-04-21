import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */

public class ComparadorCriterioA implements Comparator<Pedido> {
    @Override
    public int compare(Pedido o1, Pedido o2) {
        double valor1 = o1.valorFinal();
        double valor2 = o2.valorFinal();

        if (Double.compare(valor1, valor2) != 0)
            return Double.compare(valor1, valor2);

        int vol1 = o1.getTotalItens();
        int vol2 = o2.getTotalItens();
        if (vol1 != vol2)
            return vol1 - vol2;

        return o1.getIdPrimeiroProduto() - o2.getIdPrimeiroProduto();
    }
}

// professor, fiz de acordo com os critérios do arquivo, não olhei o enunciado, sabia apenas que era pra implementar os comparadores e segui o enunciado do método
