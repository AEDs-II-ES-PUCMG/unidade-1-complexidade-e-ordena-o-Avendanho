import java.util.Comparator;

/**
 * Critério C - Índice de Economia (decrescente).
 * O índice de economia é a diferença entre o valor de catálogo atual e o valor efetivamente pago.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioC implements Comparator<Pedido> {
    @Override
    public int compare(Pedido o1, Pedido o2) {
        double econ1 = o1.indiceEconomia();
        double econ2 = o2.indiceEconomia();
        if (Double.compare(econ2, econ1) != 0)
            return Double.compare(econ2, econ1);

        double v1 = o1.valorFinal();
        double v2 = o2.valorFinal();
        if (Double.compare(v1, v2) != 0)
            return Double.compare(v1, v2);

        return o1.getIdPedido() - o2.getIdPedido();
    }
}

// professor, fiz de acordo com os critérios do arquivo, não olhei o enunciado, sabia apenas que era pra implementar os comparadores e segui o enunciado do método