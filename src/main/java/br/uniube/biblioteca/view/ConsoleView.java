package br.uniube.biblioteca.view;

import br.uniube.biblioteca.controller.BibliotecaController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** View: só mostra o menu, lê o que o usuário digita e exibe a resposta do Controller. */
public class ConsoleView {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final BibliotecaController controller;
    private final Scanner entrada;

    public ConsoleView(BibliotecaController controller, Scanner entrada) {
        this.controller = controller;
        this.entrada = entrada;
    }

    public void iniciar() {
        String opcao;
        do {
            System.out.println("\n===== BiblioTech =====");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Cadastrar leitor");
            System.out.println("3 - Pesquisar livro");
            System.out.println("4 - Registrar empréstimo");
            System.out.println("5 - Registrar devolução");
            System.out.println("6 - Meus empréstimos");
            System.out.println("7 - Relatório de atrasos");
            System.out.println("0 - Sair");
            opcao = ler("Opção: ");

            switch (opcao) {
                case "1" -> System.out.println(controller.cadastrarLivro(ler("ISBN: "), ler("Título: "),
                        ler("Autor: "), lerInteiro("Quantidade: ")));
                case "2" -> System.out.println(controller.cadastrarLeitor(ler("CPF: "), ler("Nome: "), ler("E-mail: ")));
                case "3" -> System.out.println(controller.pesquisarLivros(ler("Título ou autor: ")));
                case "4" -> System.out.println(controller.emprestar(ler("CPF do leitor: "), ler("ISBN: "),
                        lerData("Data do empréstimo (dd/mm/aaaa): ")));
                case "5" -> System.out.println(controller.devolver(lerInteiro("Nº do empréstimo: "),
                        lerData("Data da devolução (dd/mm/aaaa): ")));
                case "6" -> System.out.println(controller.meusEmprestimos(ler("CPF do leitor: ")));
                case "7" -> System.out.println(controller.relatorioAtrasados(lerData("Data de hoje (dd/mm/aaaa): ")));
                case "0" -> System.out.println("Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (!opcao.equals("0"));
    }

    private String ler(String mensagem) {
        System.out.print(mensagem);
        return entrada.hasNextLine() ? entrada.nextLine().trim() : "0";
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(ler(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private LocalDate lerData(String mensagem) {
        while (true) {
            try {
                return LocalDate.parse(ler(mensagem), DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/mm/aaaa.");
            }
        }
    }
}
