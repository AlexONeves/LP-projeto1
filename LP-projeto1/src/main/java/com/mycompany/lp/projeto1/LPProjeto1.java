/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lp.projeto1;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
class Turma {

    private List<Aluno> alunos;

    public Turma() {
        this.alunos = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Aluno al = new Aluno();
            alunos.add(al);
        }
    }

    public List getTurma() {
        return alunos;
    }
}

class Aluno {

    private int[] respostas;
    private int res;

    public Aluno() {
        this.respostas = new int[20];
        this.respostas = generateArrayRespostas();
    }

    private int[] generateArrayRespostas() {
        Random randResposta = new Random();
        for (int i = 0; i < respostas.length; i++) {
            respostas[i] = randResposta.nextInt(5) + 0;
        }
        return respostas;
    }

    public int[] getRespostas() {
        return respostas;
    }
}

public class LPProjeto1 {

    public static void main(String[] args) {
        Turma turma = new Turma();
        List<Aluno> alunos = turma.getTurma(); // get Lista
        int j = 1;
        for (Aluno aluno : alunos) { // Por cada Aluno na Lista de Alunos
            int[] respostas = aluno.getRespostas(); // get Respostas de Cada Aluno
            System.out.print("Respostas do aluno " + j + " : ");
            for (int i = 0; i < respostas.length; i++) {
                System.out.print(respostas[i] + " ");
            }
            System.out.println();
            j++;
        }

    }
}
