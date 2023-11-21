//Arthur Utpadel

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class grafoEuleriano {

    private int[][] grafo = {
        //   V0 V1 V2 V3 V4 V5 V6 V7 V8 V9 V10 V11 V12  
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  // V0
            { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},  // V1
            { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},  // V2
            { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},  // V3
            { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},  // V4
            { 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},  // V5
            { 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0},  // V6
            { 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},  // V7
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},  // V8
            { 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},  // V9
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0},  // V10
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},  // V11
            { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}   // V12
    };
    
    private int[] peso = {2, 10, 4, 4, 4, 4, 4, 8, 10, 6, 10, 4, 10, 4, 4, 10, 4};
    private ArrayList<Aresta> arestas = getArestasDoGrafo();

    public static void main(String[] args) {
        new grafoEuleriano();
    }
    
    private String getVerticesDeGrauImpar() {
        List<Integer> verticesGrauImpar = new ArrayList<>();

        for (int i = 0; i < grafo.length; i++) {
            if (grauVerticeImpar(i)) {
                verticesGrauImpar.add(i);
            }
        }
        
        String str = "Vertices de grau impar: "+ verticesGrauImpar + "\n";

        return str;
    }


    private grafoEuleriano() {
    	//Vertices de grau impar
    	System.out.println(getVerticesDeGrauImpar());
    	
    	//Arestas e seus vertices
       exibirVerticesArestasPesos();
        
        

        //Dijkstra
        System.out.println("\n-------------Dijkstra-------------");

        int v = grafo.length;
        int[][] matrizCustoTotal = new int[v][v];

        for (int i = 0; i < v; i++) {
            if (grauVerticeImpar(i)) {
                dijkstraERegistrarCustos(i, matrizCustoTotal);
            }
        }

        //Matriz de custo
        System.out.println("\n-------------Matriz de Custo-------------");
        exibirMatriz(matrizCustoTotal);

        //Caminho euleriano
      obterCaminhoEuleriano();
    }

    private void exibirVerticesArestasPesos() {
        System.out.println("-------------Vértices, Arestas e Pesos------------");
        for (int i = 0; i < grafo.length; i++) {
            System.out.print("V" + i + ": ");

            for (int j = 0; j < grafo[i].length; j++) {
                if (grafo[i][j] == 1) {
                    int pesoAresta = peso[getIndexAresta(i, j)];
                    System.out.print("(V" + i + " -> V" + j + " Peso: " + pesoAresta + ") ");
                }
            }

            System.out.println();
        }
    }

    private void dijkstraERegistrarCustos(int origem, int[][] matrizCustoTotal) {

        int v = grafo.length;
        int[] distancias = new int[v];
        int[] pais = new int[v];
        boolean[] visitados = new boolean[v];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(pais, -1);

        distancias[origem] = 0;



        while (true) {

            int u = -1;
            int menorDistancia = Integer.MAX_VALUE;

            for (int i = 0; i < v; i++) {
                if (!visitados[i] && distancias[i] < menorDistancia) {
                    u = i;
                    menorDistancia = distancias[i];
                }
            }

            if (u == -1) {
                break;  
            }

            visitados[u] = true;



            for (int w = 0; w < v; w++) {
                if (grafo[u][w] == 1 && !visitados[w]) {
                    int pesoUW = peso[getIndexAresta(u, w)];
                    if (distancias[w] > distancias[u] + pesoUW) {
                        distancias[w] = distancias[u] + pesoUW;
                        pais[w] = u;
                    }
                }
            }
        }

        System.out.print("\nDijkstra no V" + origem+ ":\n\t" );
        for (int i = 0; i < v; i++) {
            System.out.printf("V%d\t", i);
        }
        System.out.println();

        System.out.print("D\t");
        for (int distancia : distancias) {
            System.out.printf("%d\t", distancia);
        }
        System.out.println();

        System.out.print("Pai\t");
        for (int pai : pais) {
            System.out.printf("%s\t", pai == -1 ? "-" : "V" + pai);
        }
        System.out.println();

        System.out.print("Q\t");
        for (boolean visitado : visitados) {
            System.out.printf("%s\t", visitado ? "X" : "-");
        }
        System.out.println();

        System.out.print("S\t");
        for (boolean visitado : visitados) {
            System.out.printf("%s\t", visitado ? "X" : "-");
        }
        System.out.println();

        // Copiar distâncias para a matrizCustoTotal
        System.arraycopy(distancias, 0, matrizCustoTotal[origem], 0, v);
    }




    private void exibirMatriz(int[][] matriz) {
        int v = matriz.length;

        // Imprimir cabeçalho
        imprimirCabecalho(v);

        // Imprimir linhas
        for (int i = 0; i < v; i++) {
            imprimirLinhaMatriz(matriz, i);
        }
    }

    private void imprimirCabecalho(int tamanho) {
        System.out.print("\t");
        for (int i = 0; i < tamanho; i++) {
            System.out.print("V" + i + "\t");
        }
        System.out.println();
    }

    private void imprimirLinhaMatriz(int[][] matriz, int linha) {
        int v = matriz.length;
        System.out.print("V" + linha + "\t");

        for (int coluna = 0; coluna < v; coluna++) {
            int elemento = matriz[linha][coluna];
            System.out.print(elemento + "\t");
        }

        System.out.println();
    }



    private ArrayList<Aresta> getArestasDoGrafo() {
        ArrayList<Aresta> arestas = new ArrayList<>();
        int pesoCount = 0;

        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (grafo[i][j] == 1) {
                    int origem = j;
                    int destino = i;
                    int pesoAresta = peso[pesoCount];

                    Aresta novaAresta = new Aresta(origem, destino, pesoAresta);
                    arestas.add(novaAresta);

                    pesoCount++;
                }
            }
        }

        return arestas;
    }



    private int getIndexAresta(int u, int v) {
        for (int i = 0; i < arestas.size(); i++) {
            Aresta aresta = arestas.get(i);
            if ((aresta.getU() == u && aresta.getW() == v) ||
                (aresta.getU() == v && aresta.getW() == u)) {
                return i;
            }
        }
        return -1;
    }

    private boolean grauVerticeImpar(int vertice) {
        // Verificar se o grau do vértice é ímpar
        int grau = 0;
        for (int i = 0; i < grafo[vertice].length; i++) {
            if (grafo[vertice][i] == 1) {
                grau++;
            }
        }
        return grau % 2 != 0;
    }
    
   
    private List<Integer> obterCaminhoEuleriano() {
        int[][] grafoClone = clonarMatriz(grafo);
        List<Integer> caminhoEuleriano = executarFleury(grafoClone);

        if (caminhoEuleriano == null) {
            System.out.println("Não foi possível encontrar um ciclo Euleriano.");
            return null;
        }

        System.out.println("\n-------------Caminho Euleriano-------------");
        for (int i = 0; i < caminhoEuleriano.size() - 1; i++) {
            int origem = caminhoEuleriano.get(i);

            System.out.print("V" + origem + " -> ");
        }
        System.out.println("V" + caminhoEuleriano.get(caminhoEuleriano.size() - 1));

        int custoTotal = calcularCustoTotal(caminhoEuleriano);
        System.out.println("Custo Total do Caminho Euleriano: " + custoTotal);

        return caminhoEuleriano;
    }


    private int calcularCustoTotal(List<Integer> caminhoEuleriano) {
        int custoTotal = 0;

        for (int i = 0; i < caminhoEuleriano.size() - 1; i++) {
            int origem = caminhoEuleriano.get(i);
            int destino = caminhoEuleriano.get(i + 1);
            
            if (grafo[origem][destino] != 1 && grafo[destino][origem] != 1) {
                System.out.println("Aresta Atrificial entre " + origem + " e " + destino);

                continue;
            }

            int indexAresta = getIndexAresta(origem, destino);

            if (indexAresta != -1 && indexAresta < peso.length) {
                int pesoAresta = peso[indexAresta];
                custoTotal += pesoAresta;
            } else {
                System.out.println("Índice de aresta fora dos limites.");
                // Trate o caso em que o índice está fora dos limites
            }
        }

        return custoTotal;
    }

 

    private int[][] clonarMatriz(int[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        int[][] clone = new int[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                clone[i][j] = matriz[i][j];
            }
        }
        
        return clone;
    }

    private List<Integer> executarFleury(int[][] grafo) {
        int v = grafo.length;

        // Encontrar um vértice com grau ímpar (ou use 0 se todos os vértices tiverem grau par)
        int verticeInicial = 0;
        for (int i = 0; i < v; i++) {
            if (grauVertice(i, grafo) % 2 != 0) {
                verticeInicial = i;
                break;
            }
        }

        // Iniciar o caminho euleriano
        List<Integer> caminhoEuleriano = new LinkedList<>();
        Stack<Integer> pilha = new Stack<>();
        pilha.push(verticeInicial);

        while (!pilha.isEmpty()) {
            int u = pilha.peek();

            List<Integer> vizinhosNaoIsolados = encontrarVizinhosNaoIsolados(u, grafo);

            if (!vizinhosNaoIsolados.isEmpty()) {
                int w = vizinhosNaoIsolados.get(0);
                pilha.push(w);
                removerAresta(u, w, grafo);
            } else {
                pilha.pop();
                caminhoEuleriano.add(u);
            }
        }

        return caminhoEuleriano.size() == arestas.size() + 1 ? caminhoEuleriano : null;
    }
    private List<Integer> encontrarVizinhosNaoIsolados(int vertice, int[][] grafo) {
        List<Integer> vizinhos = new ArrayList<>();
        for (int i = 0; i < grafo[vertice].length; i++) {
            if (grafo[vertice][i] == 1) {
                vizinhos.add(i);
            }
        }
        return vizinhos;
    }
    


    private int grauVertice(int vertice, int[][] grafo) {
        int grau = 0;
        for (int i = 0; i < grafo[vertice].length; i++) {
            if (grafo[vertice][i] == 1) {
                grau++;
            }
        }
        return grau;
    }

    private void removerAresta(int u, int v, int[][] grafo) {
        grafo[u][v] = 0;
        grafo[v][u] = 0;
    }
    
    
    
    
    


}