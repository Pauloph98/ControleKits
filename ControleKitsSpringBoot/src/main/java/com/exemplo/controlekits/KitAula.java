package com.exemplo.controlekits;

// Classe que representa um kit de aula
public class KitAula {
    private int numero; // Número identificador do kit
    private String descricao; // Descrição do kit (ex: Kit de Robótica)
    private int quantidadeTotal; // Quantidade total de unidades do kit
    private int quantidadeDisponivel; // Quantidade atualmente disponível para uso

    // Construtor do kit de aula
    public KitAula(int numero, String descricao, int quantidade) {
        this.numero = numero;
        this.descricao = descricao;
        this.quantidadeTotal = quantidade;
        this.quantidadeDisponivel = quantidade; // Inicialmente, tudo está disponível
    }

    // Getters(obter o valor de um atributo) (acessores)
    public int getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    // Verifica se ainda há unidades disponíveis do kit
    public boolean isDisponivel() {
        return quantidadeDisponivel > 0;
    }

    // Realiza uma retirada de uma unidade do kit (se houver disponível)
    public void retirar() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
        }
    }

    // Devolve uma unidade ao estoque do kit (se não estiver completo)
    public void devolver() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
        }
    }

    // Representação em string do kit para exibição no terminal
    @Override
    public String toString() {
        return "Kit #" + numero + " - " + descricao + " (Disponíveis: " + quantidadeDisponivel + ")";
    }
}
