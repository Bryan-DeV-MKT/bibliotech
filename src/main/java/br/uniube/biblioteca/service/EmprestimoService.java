package br.uniube.biblioteca.service;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.model.Emprestimo;
import br.uniube.biblioteca.model.Leitor;
import br.uniube.biblioteca.model.Livro;
import br.uniube.biblioteca.repository.EmprestimoRepository;
import br.uniube.biblioteca.repository.LeitorRepository;
import br.uniube.biblioteca.repository.LivroRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Casos de uso US04 (empréstimo), US05 (devolução), US06 (multa),
 * US07 (meus empréstimos) e US08 (relatório de atrasos).
 * Todas as regras de negócio do empréstimo ficam aqui, e não no Controller.
 */
public class EmprestimoService {

    public static final int MAX_EMPRESTIMOS_ATIVOS = 3;   // RN01
    public static final int PRAZO_DIAS = 7;               // RN02
    public static final double MULTA_POR_DIA = 1.00;      // RN03

    private final EmprestimoRepository emprestimos;
    private final LeitorRepository leitores;
    private final LivroRepository livros;

    public EmprestimoService(EmprestimoRepository emprestimos, LeitorRepository leitores, LivroRepository livros) {
        this.emprestimos = emprestimos;
        this.leitores = leitores;
        this.livros = livros;
    }

    public Emprestimo registrarEmprestimo(String cpf, String isbn, LocalDate data) {
        Leitor leitor = leitores.buscarPorCpf(cpf)
                .orElseThrow(() -> new RegraNegocioException("Leitor não encontrado."));
        Livro livro = livros.buscarPorIsbn(isbn)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado."));

        if (!livro.temExemplarDisponivel()) {
            throw new RegraNegocioException("Não há exemplar disponível de \"" + livro.getTitulo() + "\".");
        }
        List<Emprestimo> ativos = listarAtivosDoLeitor(cpf);
        if (ativos.stream().anyMatch(e -> e.estaAtrasado(data))) {   // RN04
            throw new RegraNegocioException("Leitor possui empréstimo em atraso e não pode pegar novos livros.");
        }
        if (ativos.size() >= MAX_EMPRESTIMOS_ATIVOS) {               // RN01
            throw new RegraNegocioException("Limite de " + MAX_EMPRESTIMOS_ATIVOS + " empréstimos ativos atingido.");
        }

        livro.retirarExemplar();
        Emprestimo emprestimo = new Emprestimo(emprestimos.proximoId(), leitor, livro, data, data.plusDays(PRAZO_DIAS));
        return emprestimos.salvar(emprestimo);
    }

    /** Registra a devolução e devolve o valor da multa (zero se estiver no prazo). */
    public double registrarDevolucao(int idEmprestimo, LocalDate data) {
        Emprestimo emprestimo = emprestimos.buscarPorId(idEmprestimo)
                .orElseThrow(() -> new RegraNegocioException("Empréstimo não encontrado."));
        if (!emprestimo.isAtivo()) {
            throw new RegraNegocioException("Esse empréstimo já foi devolvido.");
        }
        double multa = calcularMulta(emprestimo.getDataPrevista(), data);
        emprestimo.registrarDevolucao(data, multa);
        emprestimo.getLivro().devolverExemplar();
        return multa;
    }

    /** RN03: R$ 1,00 por dia de atraso. Devolver até a data prevista não gera multa. */
    public double calcularMulta(LocalDate dataPrevista, LocalDate dataDevolucao) {
        long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
        return diasAtraso > 0 ? diasAtraso * MULTA_POR_DIA : 0.0;
    }

    public List<Emprestimo> listarAtivosDoLeitor(String cpf) {
        return emprestimos.listarPorLeitor(cpf).stream().filter(Emprestimo::isAtivo).toList();
    }

    public List<Emprestimo> listarAtrasados(LocalDate hoje) {
        return emprestimos.listarTodos().stream().filter(e -> e.estaAtrasado(hoje)).toList();
    }
}
