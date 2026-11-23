package br.uniube.biblioteca;

import br.uniube.biblioteca.controller.BibliotecaController;
import br.uniube.biblioteca.repository.EmprestimoRepositoryMemoria;
import br.uniube.biblioteca.repository.LeitorRepositoryMemoria;
import br.uniube.biblioteca.repository.LivroRepositoryMemoria;
import br.uniube.biblioteca.service.EmprestimoService;
import br.uniube.biblioteca.service.LeitorService;
import br.uniube.biblioteca.service.LivroService;
import br.uniube.biblioteca.view.ConsoleView;
import java.util.Scanner;

/** Ponto de entrada: monta as camadas (Repository -> Service -> Controller -> View) e inicia. */
public class App {
    public static void main(String[] args) {
        LivroRepositoryMemoria livros = new LivroRepositoryMemoria();
        LeitorRepositoryMemoria leitores = new LeitorRepositoryMemoria();
        EmprestimoRepositoryMemoria emprestimos = new EmprestimoRepositoryMemoria();

        BibliotecaController controller = new BibliotecaController(
                new LivroService(livros),
                new LeitorService(leitores),
                new EmprestimoService(emprestimos, leitores, livros));

        new ConsoleView(controller, new Scanner(System.in)).iniciar();
    }
}
