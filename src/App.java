
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class App {

    static final int MAX_PEDIDOS = 100;
    static Pedido[] pedidos;
    static int quantPedidos = 0;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Produto[] ordenadosPorId;
    static Produto[] ordenadosPorDescricao;

    // #region utilidades
    static Scanner teclado;

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }     

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
    cabecalho();
    System.out.println("1 - Bolha");
    System.out.println("2 - Inserção");
    System.out.println("3 - Seleção");
    System.out.println("4 - Mergesort");
    System.out.println("5 - Heapsort");
    System.out.println("0 - Voltar");

    return lerNumero("Digite sua opção", Integer.class);
}

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        ordenadosPorId = new Mergesort<Produto>().ordenar(dadosCarregados, new ComparadorPorCodigo());
        ordenadosPorDescricao = new Mergesort<Produto>().ordenar(dadosCarregados, new ComparadorPorDescricao());
        return dadosCarregados;
    }


    static Produto localizarProduto() {
    cabecalho();
    System.out.println("1 - Buscar por código");
    System.out.println("2 - Buscar por descrição");
    int criterio = lerNumero("Critério", Integer.class);

    if (criterio == 1) {
        int codigo = lerNumero("Digite o código", Integer.class);
        int ini = 0, fim = quantProdutos - 1;
        while (ini <= fim) {
            int meio = (ini + fim) / 2;
            int idMeio = ordenadosPorId[meio].hashCode();
            if (idMeio == codigo) return ordenadosPorId[meio];
            else if (idMeio < codigo) ini = meio + 1;
            else fim = meio - 1;
        }
    } else {
        System.out.print("Digite a descrição: ");
        String desc = teclado.nextLine();
        int ini = 0, fim = quantProdutos - 1;
        while (ini <= fim) {
            int meio = (ini + fim) / 2;
            int cmp = ordenadosPorDescricao[meio].compareTo(
                new ProdutoNaoPerecivel(desc, 0.01)
            );
            if (cmp == 0) return ordenadosPorDescricao[meio];
            else if (cmp < 0) ini = meio + 1;
            else fim = meio - 1;
        }
    }
    return null;
}

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos(){
        cabecalho();
        
        int opcao = exibirMenuOrdenadores();
        IOrdenador<Produto> ordenador;

        switch (opcao) {
            case 1:
                ordenador = new Bubblesort<Produto>();
                break;
            case 2:
                ordenador = new InsertionSort<Produto>();
                break;
            case 3:
                ordenador = new SelectionSort<Produto>();
                break;
            default:
                ordenador = new Mergesort<Produto>();
                break;
        }
        ComparadorPorCodigo comp = new ComparadorPorCodigo();
        Produto[] ListaOrdenada = ordenador.ordenar(produtos, comp);
        produtos = ListaOrdenada;
    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            dadosOriginais = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }         

static void ordenarPedidos() {
    cabecalho();

    int opcaoOrdenador = exibirMenuOrdenadores();
    if (opcaoOrdenador == 0) return;

    IOrdenador<Pedido> ordenador;
    switch (opcaoOrdenador) {
        case 1:  ordenador = new Bubblesort<>();    
        break;
        case 2:  ordenador = new InsertionSort<>();    
        break;
        case 3:  ordenador = new SelectionSort<>(); 
        break;
        case 4:  ordenador = new Mergesort<>();     
        break;
        default: ordenador = new Heapsort<>();      
        break;
    }

    cabecalho();
    System.out.println("1 - Valor Final do Pedido");
    System.out.println("2 - Volume Total de Itens");
    System.out.println("3 - Índice de Economia (decrescente)");
    int criterio = lerNumero("Critério", Integer.class);

    Comparator<Pedido> comparador;
    switch (criterio) {
        case 2:  comparador = new ComparadorCriterioB();     
        break;
        case 3:  comparador = new ComparadorCriterioC();   
        break;
        default: comparador = new ComparadorCriterioA(); 
        break;
    }

    ordenador.setComparador(comparador);

    long inicio = System.currentTimeMillis();
    pedidos = ordenador.ordenar(pedidos);
    long fim = System.currentTimeMillis();

    System.out.printf("Ordenação concluída em %d ms. Comparações: %d | Movimentações: %d%n",
            fim - inicio, ordenador.getComparacoes(), ordenador.getMovimentacoes());
}

static void localizarPedidosPremium() {
    cabecalho();
    double corte = lerNumero("Digite o valor de corte", Double.class);

    Mergesort<Pedido> sorter = new Mergesort<>();
    sorter.setComparador(new ComparadorCriterioA());
    Pedido[] ordenadosPorValor = sorter.ordenar(pedidos.clone());

    int ini = 0, fim = quantPedidos - 1, inicioFaixa = quantPedidos;
    while (ini <= fim) {
        int meio = (ini + fim) / 2;
        if (ordenadosPorValor[meio].valorFinal() >= corte) {
            inicioFaixa = meio;
            fim = meio - 1;
        } else {
            ini = meio + 1;
        }
    }

    if (inicioFaixa == quantPedidos) {
        System.out.println("Nenhum pedido encontrado acima do valor informado.");
    } else {
        for (int i = inicioFaixa; i < quantPedidos; i++) {
            System.out.println(ordenadosPorValor[i]);
        }
    }
}
}
