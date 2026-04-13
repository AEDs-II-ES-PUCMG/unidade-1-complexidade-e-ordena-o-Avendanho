import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }


    public static void main(String[] args) {
        int opcao = 0;
        Scanner teclado = new Scanner(System.in);

        do {
            int tam = 20;
            Integer[] vetor = gerarVetorObjetos(tam);

            System.out.print("* 1. BubbleSort;\n* 2. InsertionSort;\n* 3. SelectionSort;\n* 4. MergeSort;\n* 0. Encerrar.\n\nSelecione a opção: ");
            opcao = Integer.parseInt(teclado.nextLine());

            switch(opcao) {
                case 1:
                    BubbleSort<Integer> bolha = new BubbleSort<>();
                    Integer[] vetorOrdenadoBolha = statsOrdenacao(bolha, vetor, "BubbleSort");
                    imprimirVetor(vetorOrdenadoBolha);
                    esperar(teclado);
                    break;
                case 2:
                    InsertionSort<Integer> insercao = new InsertionSort<>();
                    Integer[] vetorOrdenadoInsercao = statsOrdenacao(insercao, vetor, "InsertionSort");
                    imprimirVetor(vetorOrdenadoInsercao);
                    esperar(teclado);
                    break;
                case 3:
                    SelectionSort<Integer> selecao = new SelectionSort<>();
                    Integer[] vetorOrdenadoSelecao = statsOrdenacao(selecao, vetor, "SelectionSort");
                    imprimirVetor(vetorOrdenadoSelecao);
                    esperar(teclado);
                    break;
                case 4:
                    MergeSort<Integer> merge = new MergeSort<>();
                    Integer[] vetorOrdenadoMerge = statsOrdenacao(merge, vetor, "MergeSort");
                    imprimirVetor(vetorOrdenadoMerge);
                    esperar(teclado);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    esperar(teclado);
                    break;
            }
        } while(opcao != 0);

        teclado.close();


        /*
        

        

        


        */
    }

    private static <T extends Comparable<T>> T[] statsOrdenacao(IOrdenador<T> ordenador, T[] vetor, String nomeOrdenacao) {
        T[] vetorOrdenado = ordenador.ordenar(vetor);

        System.out.println("\nVetor ordenado método " + nomeOrdenacao + ":");
        System.out.println("Comparações: " + ordenador.getComparacoes());
        System.out.println("Movimentações: " + ordenador.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + ordenador.getTempoOrdenacao());

        return vetorOrdenado;
    }

    private static <T> void imprimirVetor(T[] vetor) {
        StringBuilder builder = new StringBuilder("Vetor: ");

        for(int i = 0; i < vetor.length; i++) {
            String pontuacao = i == vetor.length - 1 ? ". " : ", ";
            builder.append(vetor[i] + pontuacao);
        }

        System.out.println(builder.toString());
    }

    private static void esperar(Scanner teclado) {
        teclado.nextLine();
    }
}
