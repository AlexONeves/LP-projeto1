package estga.poo.novouniverso.mavenproject1;

import java.io.*;
import java.util.*;

class ResultadoCalculo implements Serializable {

    private transient ArrayList<Integer> respostasCorretas;
    private transient ArrayList<Double> resultados;

    private transient double maiorNota;
    private transient double menorNota;
    private transient double mediaNotas;

    private transient int numeroPositivasInt;
    private transient int numeroNegativasInt;

    public ResultadoCalculo(ArrayList<ArrayList<Integer>> turma) {

        this.respostasCorretas = SistemaCalculoTurma.gerarRespostas(30);

        this.resultados = calcularNotas(turma);

        this.maiorNota = SistemaCalculoTurma.numeroMaiorArrayInt(this.resultados);
        this.menorNota = SistemaCalculoTurma.numeroMenorArrayInt(this.resultados);
        this.mediaNotas = SistemaCalculoTurma.numeroMediaArrayInt(this.resultados);
    }

    private ArrayList<Double> calcularNotas(ArrayList<ArrayList<Integer>> turma) {
        ArrayList<Double> tmp = new ArrayList<>();

        //loop pelo aluno
        for (ArrayList<Integer> aluno : turma) {
            Double resultado = SistemaCalculoTurma.calcularNotas(aluno, respostasCorretas);

            if (resultado > 10) {
                this.numeroPositivasInt++;
            } else {
                this.numeroNegativasInt++;
            }
            tmp.add(resultado);
        }
        return tmp;
    }

    @Override
    public String toString() {

        String tmp = "turma{\n" + "alunos:\n";

        for (int i = 0; i < resultados.size(); i++) {
            tmp += (i + " - " + Double.toString(resultados.get(i)) + '\n');
        }

        tmp += "Nota mais alta: " + this.maiorNota + '\n'
                + "Nota mais baixa: " + this.menorNota + '\n'
                + "Média das notas: " + this.mediaNotas + '\n'
                + "Número de positivas e respetiva percentagem: " + this.numeroPositivasInt
                + " - " + (this.numeroPositivasInt / this.respostasCorretas.size()) + " %\n"
                + "Número de negativas e respetiva percentagem: " + this.numeroNegativasInt
                + " - " + (this.numeroNegativasInt / this.respostasCorretas.size()) + " %\n"
                + '\n';

        return tmp;
    }
}

/**
 *
 * @author ricar
 */
public class SistemaCalculoTurma implements Serializable {

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
                    resultado = (resultado - 0.5 < 0) ? resultado - 0.5 : resultado;
                }
            }
        }
        return resultado;
    }

    static ArrayList<Integer> gerarRespostas(int quantidade) {
        ArrayList<Integer> tmp = new ArrayList<>();
        Random r = new Random();

        //cria numero de 1 a 5 com class Random
        for (int i = 0; i < quantidade; i++) {
            tmp.add(r.nextInt(5));
        }

        return tmp;
    }

    /**
     *
     * @param arr -> Array para ser calulado o menor numero
     * @return
     */
    static Double numeroMaiorArrayInt(ArrayList<Double> arr) {
        Double maior = arr.get(0);

        for (Double curr_numero : arr) {
            maior = Double.max(maior, curr_numero);
        }

        return maior;
    }

    /**
     *
     * @param arr -> Array para ser calulado o menor numero
     * @return
     */
    static Double numeroMenorArrayInt(ArrayList<Double> arr) {
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
        return arr.stream().mapToDouble(a -> a).average().getAsDouble();
    }

    /**
     * @param diretorio -> Diretorio a ser verificado a existencia de ficheiros
     * disponiveis
     *
     * @return Retorna a quantidade de turmas disponiveis, ou null se não achar
     * nenhuma
     */
    static boolean verificarExistenciaDeFicheiros() {
        String curr_directory;

        try {
            curr_directory = new java.io.File(".").getCanonicalPath() + "\\src\\";

            File ficheiroResultado = new File(curr_directory + "resultado.dat");
            File ficheiroTurma = new File(curr_directory + "turma.dat");

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

        ArrayList<ArrayList<Integer>> turma = new ArrayList<>(numAlunos);
        Random randResposta = new Random();

        for (ArrayList<Integer> curr_aluno : turma) {
            // gerar nota para cada aluno
            for (int curr_nota = 0; curr_nota < numRespostas; curr_nota++) {
                curr_aluno.add(randResposta.nextInt(5) + 0);
            }
        }

        //gera ficheiro
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));
            output.writeObject(turma);
            output.close();

        } catch (IOException ioe) {
            return false;
        }
        return true;

    }

    static boolean gerarFicheiroRespostas(int numRespostas, File filePath) {

        if (numRespostas < 0) {
            return false;
        }

        int[] respostas = new int[numRespostas];

        Random randResposta = new Random();

        for (int curr_nota = 0; curr_nota < numRespostas; curr_nota++) {
            respostas[curr_nota] = randResposta.nextInt(5) + 0;
        }

        //TO DO
        return true;

    }
}
