import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {
    @Override
    public int compare(Pedido o1, Pedido o2) {
        int vol1 = o1.getTotalItens();
        int vol2 = o2.getTotalItens();

        if (vol1 != vol2)
            return vol1 - vol2;

        int dataCmp = o1.getDataPedido().compareTo(o2.getDataPedido());
        if (dataCmp != 0)
            return dataCmp;

        return o1.getIdPedido() - o2.getIdPedido();
    }
}

// professor, fiz de acordo com os critérios do arquivo, não olhei o enunciado, sabia apenas que era pra implementar os comparadores e segui o enunciado do método