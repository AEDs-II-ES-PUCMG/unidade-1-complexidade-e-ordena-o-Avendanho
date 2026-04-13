import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {
    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0 / 1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar() {
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar() {
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        movimentacoes += 3;
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);

        iniciar();

        mergesort(dadosOrdenados, 0, dadosOrdenados.length - 1);

        terminar();
        return dadosOrdenados;
    }

    private void mergesort(T[] array, int esq, int dir) {
        if (esq < dir) {
            comparacoes++;
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
    }

    private void intercalar(T[] array, int esq, int meio, int dir) {
        int n1, n2, i, j, k;

        n1 = meio - esq + 1;
        n2 = dir - meio;

        T[] a1 = (T[]) new Comparable[n1];
        T[] a2 = (T[]) new Comparable[n2];

        for (i = 0; i < n1; i++) {
            a1[i] = array[esq + i];
            comparacoes++;
        }

        for (j = 0; j < n2; j++) {
            a2[j] = array[meio + j + 1];
            comparacoes++;
        }

        for (i = j = 0, k = esq; (i < n1 && j < n2); k++) {
            if (a1[i].compareTo(a2[j]) <= 0)
                array[k] = a1[i++];
            else
                array[k] = a2[j++];
            
            comparacoes += 3;

        }

        if (i == n1)
            for (; k <= dir; k++) {
                array[k] = a2[j++];
                comparacoes++;
            }
        else
            for (; k <= dir; k++) {
                array[k] = a1[i++];
                comparacoes++;
            }
        comparacoes++;
    }

}