/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.poo.alexandrericardo;

import java.io.*;
import java.util.*;

class ResultadoCalculo {

    private transient ArrayList<Integer> respostasCorretas;
    private transient ArrayList<Double> resultados;

    private transient double maiorNota;
    private transient double menorNota;
    private transient double mediaNotas;

    private transient int numeroPositivasInt;
    private transient int numeroNegativasInt;

    public ResultadoCalculo(ArrayList<ArrayList<Integer>> turma, ArrayList<Integer> respostasCorretas) {

        this.respostasCorretas = respostasCorretas;

        this.resultados = calcularNotas(turma);

        this.maiorNota = SistemaCalculoTurma.numeroMaiorArrayInt(this.resultados);
        this.menorNota = SistemaCalculoTurma.numeroMenorArrayInt(this.resultados);
        this.mediaNotas = SistemaCalculoTurma.numeroMediaArrayInt(this.resultados);
    }

    /**
     *
     * Função que avalia e processa os dados a popular na pauta de turma.
     *
     * @param turma-> Objeto turma a ser processado
     * @return
     */
    private ArrayList<Double> calcularNotas(ArrayList<ArrayList<Integer>> turma) {
        ArrayList<Double> tmp = new ArrayList<>();

        for (ArrayList<Integer> aluno : turma) {
            //Calcular a nota
            Double resultado = SistemaCalculoTurma.calcularNotas(aluno, respostasCorretas);

            //Avaliar resultado obtido
            if (resultado > 10) {
                this.numeroPositivasInt++;
            } else {
                this.numeroNegativasInt++;
            }

            //guardar resultado
            tmp.add(resultado);
        }
        return tmp;
    }

    /**
     * Como um dos requerimentos foi exibir resultados de varias maneiras,
     * acabamos por usar esta abordagem, podendo re-usar a função
     * Object.toString em qualquer necessidade de exibir.
     *
     */
    @Override
    public String toString() {

        String tmp = "turma{\n" + "alunos:\n";

        for (int i = 0; i < resultados.size(); i++) {
            tmp += ((i + 1) + " - " + Double.toString(resultados.get(i)) + '\n');
        }

        tmp += "Nota mais alta: " + this.maiorNota + '\n'
                + "Nota mais baixa: " + this.menorNota + '\n'
                + "Média das notas: " + this.mediaNotas + '\n'
                + "Número de positivas e respetiva percentagem: " + this.numeroPositivasInt
                + " - " + (this.numeroPositivasInt / this.respostasCorretas.size()) * 100 + " %\n"
                + "Número de negativas e respetiva percentagem: " + this.numeroNegativasInt
                + " - " + (this.numeroNegativasInt / this.respostasCorretas.size()) * 100 + " %\n"
                + '\n';

        return tmp;
    }
}

/**
 *
 * @author ricar
 */
public class SistemaCalculoTurma {

    /**
     * Função que calcula a nota do aluno, irá ser adicionado 1 a cada resposta
     * certa, descontado 0.5 a cada nota errada, ou 0 se tiver em branco.
     *
     * @param listaRespostas -> Lista de inteiros com respostas a ser avaliadas
     * @param listaRespostasCertas -> Lista de inteiros com respostas corretas
     * @return Retorna o valor calculado da nota
     */
    static Double calcularNotas(ArrayList<Integer> listaRespostas, ArrayList<Integer> listaRespostasCertas) {
        Double resultado = 0.0;

        if (listaRespostas.size() <= 0) {
            System.out.println("Lista de respostas vazias");
            return null;
        }

        //loop pelas respostas do aluno
        for (int i = 0; i < listaRespostas.size(); i++) {
            //caso seja 0, não avalia
            if (listaRespostas.get(i) != 0) {
                //caso sejam iguais
                if (Integer.compare(listaRespostas.get(i), listaRespostasCertas.get(i)) == 0) {
                    resultado += 1;
                } else {
                    //apenas subtrai se o resultado não for negativo
                    resultado = (resultado - 0.5 < 0) ? resultado : resultado - 0.5;
                }
            }
        }
        return resultado;
    }

    /**
     *
     * Função apenas para qualidade de vida, Serve para gerar uma lista de
     * respostas para teste
     *
     * @param quantidade -> Quantidade de numeros/respostas a gerar
     * @return Irá retornar a lista de numeros gerados
     */
    /**
     * Função que obtem o maior numero de um array fornecido.
     *
     * @param arr -> Array para ser calulado o menor numero
     * @return Retorna o maior numero da lista
     */
    static Double numeroMaiorArrayInt(ArrayList<Double> arr) {
        //verificar erros
        if (arr.isEmpty()) {
            return null;
        }

        Double maior = arr.get(0);

        for (Double curr_numero : arr) {
            maior = Double.max(maior, curr_numero);
        }

        return maior;
    }

    /**
     *
     * @param arr -> Array para ser calulado o menor numero
     * @return -> Irá retornar o menor numero do array
     */
    static Double numeroMenorArrayInt(ArrayList<Double> arr) {
        //verificar erros
        if (arr.isEmpty()) {
            return null;
        }

        double menor = arr.get(0);

        for (double curr_numero : arr) {
            menor = Double.min(menor, curr_numero);
        }

        return menor;
    }

    /**
     *
     * Função de media <br>
     *
     * Simples Função que calcula a media dos elementos do array
     *
     * @param arr -> O array para ser calculado a media
     * @return
     */
    static Double numeroMediaArrayInt(ArrayList<Double> arr) {
        //verificar erros
        if (arr.isEmpty()) {
            return null;
        }

        return arr.stream().mapToDouble(a -> a).average().getAsDouble();
    }

    /**
     *
     * Usada para verificar a necessidade de criação de Objectos temporarios de
     * teste.
     *
     * @param diretorio -> Diretorio a ser verificado a existencia de ficheiros
     * disponiveis
     *
     * @return Retorna a quantidade de turmas disponiveis, ou null se não achar
     * nenhuma
     */
    static boolean verificarExistenciaDeFicheiros(String nomeFicheiroResposta, String nomeFicheiroTurma) {
        String curr_directory;

        try {
            curr_directory = new java.io.File(".").getCanonicalPath() + "\\src\\";

            File ficheiroResultado = new File(curr_directory + nomeFicheiroResposta);
            File ficheiroTurma = new File(curr_directory + nomeFicheiroTurma);

            return ficheiroResultado.exists() && ficheiroTurma.exists();
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     *
     * Função que processa e guarda a turma em um ficheiro.<br>
     *
     * Ficheiro guardado na diretoria /src/ do projeto
     *
     * @param turmaEscolhida -> objeto da class turma
     * @param nomeFicheiro -> guardado com extensão .dat
     * @return retorna se foi sucedido ou não
     */
    static boolean gerarFicheiroTurma(int numAlunos, int numRespostas, File filePath) {

        if (numAlunos < 0 || numRespostas < 0) {
            return false;
        }

        // gera lista temporaria para guardar dados
        ArrayList<ArrayList<Integer>> turma = new ArrayList<>(numAlunos);
        Random randResposta = new Random();

        // enche a "turma" gerada com notas de alunos de exemplo
        for (int curr_aluno = 0; curr_aluno < numAlunos; curr_aluno++) {
            ArrayList<Integer> aluno = new ArrayList<>(numRespostas);

            // gerar nota para cada aluno
            for (int curr_nota = 0; curr_nota < numRespostas; curr_nota++) {
                aluno.add(randResposta.nextInt(5) + 0);
            }
            System.out.println(aluno);
            turma.add(aluno);
        }

        //gera ficheiro turma, retorna false em caso de insucesso
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));
            output.writeObject(turma);
            output.close();

        } catch (IOException ioe) {
            return false;
        }
        return true;

    }

    /**
     *
     * @param numRespostas
     * @param filePath
     * @return
     */
    static boolean gerarFicheiroRespostas(int numRespostas, File filePath) {

        if (numRespostas < 0) {
            return false;
        }

        int[] respostas = new int[numRespostas];
        ArrayList<Integer> Respuestas = new ArrayList<Integer>();
        Random randResposta = new Random();

        for (int curr_nota = 0; curr_nota < numRespostas; curr_nota++) {
            respostas[curr_nota] = randResposta.nextInt(5) + 0;
            Respuestas.add(respostas[curr_nota]);
        }

        //gera ficheiro Respostas, retorna false em caso de insucesso
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int resposta : Respuestas) {
                writer.write(resposta + "\n");
            }
            writer.close();

        } catch (IOException ioe) {
            return false;
        }
        return true;

    }

    /**
     * Função que acede ao ficheiro turma e passa os valores dos alunos para uma
     * ArrayList<ArrayList<Integer>>.
     *
     * @param Ficheiro -> Caminho do ficheiro
     *
     * @return Retorna valores do ficheiro turma
     */
    static ArrayList<ArrayList<Integer>> retornarFicheiroTurma(File Ficheiro) {

        try {
            ArrayList<ArrayList<Integer>> turma;
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(Ficheiro));
            turma = (ArrayList<ArrayList<Integer>>) ois.readObject();
            ois.close();
            return turma;

        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /**
     * Função que acede ao ficheiro respostas.txt e passa os numeros inteiros de
     * respostas.txt para uma arrayList de Integer.
     *
     * @param Ficheiro -> Caminho do ficheiro
     *
     * @return Retorna ArrayList de Integer das respostas Corretas
     */
    static ArrayList<Integer> retornarFicheiroRespostasCorretas(File Ficheiro) {
        BufferedReader reader = null;
        try {

            ArrayList<Integer> RespostasCorretas = new ArrayList<Integer>();
            reader = new BufferedReader(new FileReader(Ficheiro));
            String line;
            while ((line = reader.readLine()) != null) {
                //Converte Linhas de texto do arquivo para Numeros Inteiros e armazena na array list
                RespostasCorretas.add(Integer.valueOf(line));
            }
            reader.close();

            return RespostasCorretas;

        } catch (IOException ex) {
            return null;
        }
    }

}
