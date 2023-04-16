/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package estga.poo.alexandrericardo;

import java.io.*;

/**
 *
 * @author alex
 * @author ricar
 */
public class LpProjeto1AlexandreRicardo {

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

        final int numAlunos = 20;
        final int numRespostas = 30;
        final String nomeFicheiroTurma = "respostasTurma";
        final String nomeFicheiroResposta = "respostas.txt";

        if (SistemaCalculoTurma.verificarExistenciaDeFicheiros(nomeFicheiroResposta, nomeFicheiroTurma) != true) {
            //gerar templates
            SistemaCalculoTurma.gerarFicheiroTurma(numAlunos, numRespostas, obterDiretorio(nomeFicheiroTurma));
            SistemaCalculoTurma.gerarFicheiroRespostas(numRespostas, obterDiretorio(nomeFicheiroResposta));
            System.out.println("Nenhum ficheiro turma/respostas foi encontrado, foram criados novos!");
        }

        //respostas
        resultado = new ResultadoCalculo(SistemaCalculoTurma.retornarFicheiroTurma(obterDiretorio(nomeFicheiroTurma)),
                SistemaCalculoTurma.retornarFicheiroRespostasCorretas(obterDiretorio(nomeFicheiroResposta)));

        System.out.println(resultado.toString());

    }
}
