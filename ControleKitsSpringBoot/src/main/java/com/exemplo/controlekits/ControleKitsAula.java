package com.exemplo.controlekits;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal que controla o fluxo do programa no terminal.
 * Implementa CommandLineRunner para ser executado automaticamente ao iniciar o Spring Boot.
 */
@Component
public class ControleKitsAula implements CommandLineRunner {

    // Lista para armazenar os professores cadastrados
    private List<Professor> professores = new ArrayList<>();

    // Lista para armazenar os kits cadastrados
    private List<KitAula> kits = new ArrayList<>();

    // Lista com o histórico de registros de uso (retiradas e devoluções)
    private List<RegistroUso> registros = new ArrayList<>();

    // Scanner para ler entradas do usuário via terminal
    private Scanner scanner = new Scanner(System.in);

    /**
     * Método executado automaticamente ao iniciar a aplicação.
     * Exibe um menu com opções para o usuário.
     */
    @Override
    public void run(String... args) throws Exception {
        int opcao;
        do {
            // Menu principal
            System.out.println("\n=== CONTROLE DE KITS DE AULA ===");
            System.out.println("1 - Cadastrar professor");
            System.out.println("2 - Cadastrar kit de aula");
            System.out.println("3 - Registrar retirada de kit");
            System.out.println("4 - Registrar devolução de kit");
            System.out.println("5 - Listar kits disponíveis");
            System.out.println("6 - Listar professores");
            System.out.println("7 - Histórico de uso");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            // Executa a ação conforme a opção escolhida
            switch (opcao) {
                case 1 -> cadastrarProfessor();
                case 2 -> cadastrarKit();
                case 3 -> registrarRetirada();
                case 4 -> registrarDevolucao();
                case 5 -> listarKits();
                case 6 -> listarProfessores();
                case 7 -> listarHistorico();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0); // Continua até o usuário escolher sair
    }

    // Método para cadastrar um novo professor
    private void cadastrarProfessor() {
        System.out.print("Nome do professor: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        professores.add(new Professor(nome, matricula));
        System.out.println("Professor cadastrado com sucesso!");
    }

    // Método para cadastrar um novo kit de aula
    private void cadastrarKit() {
        System.out.print("Número do kit: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Descrição do kit: ");
        String descricao = scanner.nextLine();

        System.out.print("Quantidade total disponível: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        kits.add(new KitAula(numero, descricao, quantidade));
        System.out.println("Kit cadastrado com sucesso!");
    }

    // Método para registrar a retirada de um kit por um professor
    private void registrarRetirada() {
        listarProfessores(); // Exibe lista de professores

        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }

        listarKitsDisponiveis(); // Exibe kits disponíveis

        if (kits.stream().noneMatch(KitAula::isDisponivel)) {
            System.out.println("Nenhum kit disponível para retirada.");
            return;
        }

        // Escolhe professor e kit
        System.out.print("Número do professor: ");
        int indexProfessor = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.print("Número do kit: ");
        int indexKit = scanner.nextInt() - 1;
        scanner.nextLine();

        // Verifica se os índices são válidos e o kit está disponível
        if (indexProfessor < 0 || indexProfessor >= professores.size() ||
                indexKit < 0 || indexKit >= kits.size() || !kits.get(indexKit).isDisponivel()) {
            System.out.println("Opção inválida ou kit indisponível.");
            return;
        }

        // Atualiza status e registra a retirada
        KitAula kit = kits.get(indexKit);
        kit.retirar();
        registros.add(new RegistroUso(kit, professores.get(indexProfessor)));
        System.out.println("Retirada registrada com sucesso!");
    }

    // Método para registrar a devolução de um kit
    private void registrarDevolucao() {
        // Filtra os registros que ainda não foram devolvidos
        List<RegistroUso> pendentes = registros.stream()
                .filter(r -> r.getDataHoraDevolucao() == null)
                .toList();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma retirada pendente de devolução.");
            return;
        }

        // Exibe os registros pendentes
        System.out.println("\n=== RETIRADAS PENDENTES ===");
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1) + " - " + pendentes.get(i));
        }

        // Escolhe o registro para devolução
        System.out.print("Número do registro para devolução: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index < 0 || index >= pendentes.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        // Atualiza status de devolução
        RegistroUso registro = pendentes.get(index);
        registro.registrarDevolucao();
        registro.getKit().devolver();
        System.out.println("Devolução registrada com sucesso!");
    }

    // Exibe todos os kits cadastrados
    private void listarKits() {
        if (kits.isEmpty()) {
            System.out.println("Nenhum kit cadastrado.");
            return;
        }
        System.out.println("\n=== LISTA DE KITS ===");
        kits.forEach(System.out::println);
    }

    // Exibe somente os kits com quantidade disponível
    private void listarKitsDisponiveis() {
        List<KitAula> disponiveis = kits.stream()
                .filter(KitAula::isDisponivel)
                .toList();

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum kit disponível no momento.");
            return;
        }

        System.out.println("\n=== KITS DISPONÍVEIS ===");
        for (int i = 0; i < disponiveis.size(); i++) {
            System.out.println((i + 1) + " - " + disponiveis.get(i));
        }
    }

    // Exibe todos os professores cadastrados
    private void listarProfessores() {
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE PROFESSORES ===");
        for (int i = 0; i < professores.size(); i++) {
            System.out.println((i + 1) + " - " + professores.get(i));
        }
    }

    // Exibe o histórico de retiradas e devoluções
    private void listarHistorico() {
        if (registros.isEmpty()) {
            System.out.println("Nenhum registro de uso encontrado.");
            return;
        }

        System.out.println("\n=== HISTÓRICO DE USO ===");
        registros.forEach(System.out::println);
    }
}
