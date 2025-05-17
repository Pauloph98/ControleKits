package com.exemplo.controlekits;

// Classe que representa um professor
public class Professor {
    private String nome; // Nome do professor
    private String matricula; // Matrícula do professor

    // Construtor da classe
    public Professor(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    // Representação em string do professor (exibida em listagens)
    public String toString() {
        return nome + " (Matrícula: " + matricula + ")";
    }
}
