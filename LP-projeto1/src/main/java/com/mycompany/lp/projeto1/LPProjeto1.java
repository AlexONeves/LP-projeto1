/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lp.projeto1;

import java.util.Random;

/**
 *
 * @author alex
 */
class Aluno {

    private int[] respostas = new int[20];
    private int res;

    public Aluno() {
        this.respostas = generateArrayRespostas();
    }

    private int[] generateArrayRespostas() {
        for (int i = 0; i < respostas.length; i++) {
            respostas[i] = generateRandomResposta();
        }
        return respostas;
    }

    private int generateRandomResposta() {
        Random randResposta = new Random();
        return randResposta.nextInt(5) + 0;
    }

    public int[] getRespostas() {
        return respostas;
    }
}

public class LPProjeto1 {

    public static void main(String[] args) {
        Aluno al = new Aluno();
        int[] respostas = al.getRespostas();

        // print the values of the respostas array
        System.out.println("Respostas:");
        for (int i = 0; i < respostas.length; i++) {
            System.out.print(respostas[i] + " ");
        }
        System.out.println();
    }
}
