package br.uniube.biblioteca.controller;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.model.Emprestimo;
import br.uniube.biblioteca.model.Livro;
import br.uniube.biblioteca.service.EmprestimoService;
import br.uniube.biblioteca.service.LeitorService;
import br.uniube.biblioteca.service.LivroService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller: recebe o pedido da View, chama o Service certo e devolve a resposta em texto.
 * Não contém regra de negócio; apenas coordena e traduz erros em mensagens.
 */
public class BibliotecaController {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LivroService livroService;
    private final LeitorService leitorService;
    private final EmprestimoService emprestimoService;

    public BibliotecaController(LivroService livroService, LeitorService leitorService,
                                EmprestimoService emprestimoService) {
        this.livroService = livroService;
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;
    }

    public String cadastrarLivro(String isbn, String titulo, String autor, int quantidade) {
        try {
            Livro livro = livroService.cadastrar(isbn, titulo, autor, quantidade);
            return "Livro cadastrado: " + livro.getTitulo();
        } catch (RegraNegocioException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String cadastrarLeitor(String cpf, String nome, String email) {
        try {
            return "Leitor cadastrado: " + leitorService.cadastrar(cpf, nome, email).getNome();
        } catch (RegraNegocioException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String pesquisarLivros(String termo) {
        List<Livro> encontrados = livroService.pesquisar(termo);
        if (encontrados.isEmpty()) {
            return "Nenhum livro encontrado para \"" + termo + "\".";
        }
        StringBuilder sb = new StringBuilder();
        for (Livro l : encontrados) {
            sb.append(String.format("%s | %s - %s | disponíveis: %d de %d%n", l.getIsbn(), l.getTitulo(),
                    l.getAutor(), l.getQuantidadeDisponivel(), l.getQuantidadeTotal()));
        }
        return sb.toString().trim();
    }

    public String emprestar(String cpf, String isbn, LocalDate data) {
        try {
            Emprestimo e = emprestimoService.registrarEmprestimo(cpf, isbn, data);
            return "Empréstimo nº " + e.getId() + " registrado. Devolver até " + e.getDataPrevista().format(DATA) + ".";
        } catch (RegraNegocioException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String devolver(int idEmprestimo, LocalDate data) {
        try {
            double multa = emprestimoService.registrarDevolucao(idEmprestimo, data);
            return multa > 0
                    ? String.format("Devolução registrada com atraso. Multa: R$ %.2f", multa)
                    : "Devolução registrada no prazo. Sem multa.";
        } catch (RegraNegocioException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String meusEmprestimos(String cpf) {
        List<Emprestimo> ativos = emprestimoService.listarAtivosDoLeitor(cpf);
        if (ativos.isEmpty()) {
            return "Nenhum empréstimo ativo.";
        }
        StringBuilder sb = new StringBuilder();
        for (Emprestimo e : ativos) {
            sb.append(String.format("nº %d | %s | devolver até %s%n", e.getId(), e.getLivro().getTitulo(),
                    e.getDataPrevista().format(DATA)));
        }
        return sb.toString().trim();
    }

    public String relatorioAtrasados(LocalDate hoje) {
        List<Emprestimo> atrasados = emprestimoService.listarAtrasados(hoje);
        if (atrasados.isEmpty()) {
            return "Nenhum empréstimo em atraso.";
        }
        StringBuilder sb = new StringBuilder();
        for (Emprestimo e : atrasados) {
            double multaAteHoje = emprestimoService.calcularMulta(e.getDataPrevista(), hoje);
            sb.append(String.format("nº %d | %s | %s | venceu em %s | multa até hoje: R$ %.2f%n", e.getId(),
                    e.getLeitor().getNome(), e.getLivro().getTitulo(), e.getDataPrevista().format(DATA), multaAteHoje));
        }
        return sb.toString().trim();
    }
}
