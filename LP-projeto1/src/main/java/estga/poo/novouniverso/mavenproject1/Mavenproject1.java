/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package estga.poo.novouniverso.mavenproject1;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author alex
 * @author ricar
 */
public class Mavenproject1 implements Serializable {

    /**
     * função criada para facilitar dar o caminho de ficheiros
     *
     * @param nomeFicheiro -> Nome do arquivo (Default: respostasTurma e
     * respostas.txt)
     * @return
     */
    static File obterDiretorio(String nomeFicheiro) {
        try {
            return new File(new java.io.File(".").getCanonicalPath() + "\\src\\" + nomeFicheiro);
        } catch (IOException ex) {
            System.out.println("Erro ao obter diretorio!");
            return null;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ResultadoCalculo resultado = null;
        ArrayList<ArrayList<Integer>> turma;

        final int numAlunos = 20;
        final int numRespostas = 30;
        final String nomeFicheiroTurma = "respostasTurma";
        final String nomeFicheiroResposta = "respostas.txt";

        if (SistemaCalculoTurma.verificarExistenciaDeFicheiros(nomeFicheiroResposta, nomeFicheiroTurma) != true) {
            //gerar templates
            SistemaCalculoTurma.gerarFicheiroTurma(numAlunos, numRespostas, obterDiretorio(nomeFicheiroTurma));
            //to do

            System.out.println("Nenhum ficheiro turma foi encontrado, foram criados novos!");
        }

        //to do: remover throws e criar função para ler ficheiro turma
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(obterDiretorio(nomeFicheiroTurma)));
        turma = (ArrayList<ArrayList<Integer>>) ois.readObject();
        ois.close();

        //respostas
        resultado = new ResultadoCalculo(turma);

        //criar leitura de texto
        BufferedReader br = new BufferedReader(new FileReader(obterDiretorio("respostas.txt")));

        System.out.println(resultado.toString());

    }
}
