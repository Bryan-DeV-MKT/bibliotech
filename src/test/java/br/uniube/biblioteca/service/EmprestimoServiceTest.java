package br.uniube.biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.model.Emprestimo;
import br.uniube.biblioteca.model.Leitor;
import br.uniube.biblioteca.model.Livro;
import br.uniube.biblioteca.repository.EmprestimoRepositoryMemoria;
import br.uniube.biblioteca.repository.LeitorRepositoryMemoria;
import br.uniube.biblioteca.repository.LivroRepositoryMemoria;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmprestimoServiceTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 2);
    private static final String CPF = "12345678901";

    private LivroRepositoryMemoria livros;
    private EmprestimoService service;

    @BeforeEach
    void prepararCenario() {
        livros = new LivroRepositoryMemoria();
        LeitorRepositoryMemoria leitores = new LeitorRepositoryMemoria();
        service = new EmprestimoService(new EmprestimoRepositoryMemoria(), leitores, livros);

        leitores.salvar(new Leitor(CPF, "Ana Souza", "ana@email.com"));
        livros.salvar(new Livro("111", "Dom Casmurro", "Machado de Assis", 2));
        livros.salvar(new Livro("222", "O Cortiço", "Aluísio Azevedo", 1));
        livros.salvar(new Livro("333", "Iracema", "José de Alencar", 1));
        livros.salvar(new Livro("444", "Memórias Póstumas", "Machado de Assis", 1));
    }

    @Test
    void deveRegistrarEmprestimoComPrazoDeSeteDiasEReduzirDisponiveis() {
        // Arrange (cenário montado no @BeforeEach)
        // Act
        Emprestimo emprestimo = service.registrarEmprestimo(CPF, "111", HOJE);
        // Assert
        assertEquals(HOJE.plusDays(7), emprestimo.getDataPrevista());
        assertEquals(1, livros.buscarPorIsbn("111").get().getQuantidadeDisponivel());
    }

    @Test
    void naoDevePermitirEmprestimoQuandoNaoHaExemplarDisponivel() {
        service.registrarEmprestimo(CPF, "222", HOJE);   // único exemplar sai

        RegraNegocioException erro = assertThrows(RegraNegocioException.class,
                () -> service.registrarEmprestimo(CPF, "222", HOJE));
        assertEquals("Não há exemplar disponível de \"O Cortiço\".", erro.getMessage());
    }

    @Test
    void devePermitirAteTresEmprestimosAtivosEBloquearOQuarto() {
        service.registrarEmprestimo(CPF, "111", HOJE);
        service.registrarEmprestimo(CPF, "222", HOJE);
        service.registrarEmprestimo(CPF, "333", HOJE);   // fronteira: o 3º ainda é permitido

        assertEquals(3, service.listarAtivosDoLeitor(CPF).size());
        assertThrows(RegraNegocioException.class, () -> service.registrarEmprestimo(CPF, "444", HOJE));
    }

    @Test
    void naoDeveCobrarMultaQuandoDevolvidoNoUltimoDiaDoPrazo() {
        Emprestimo emprestimo = service.registrarEmprestimo(CPF, "111", HOJE);

        double multa = service.registrarDevolucao(emprestimo.getId(), HOJE.plusDays(7));

        assertEquals(0.0, multa);
    }

    @Test
    void deveCobrarUmRealPorDiaDeAtraso() {
        Emprestimo emprestimo = service.registrarEmprestimo(CPF, "111", HOJE);

        double multa = service.registrarDevolucao(emprestimo.getId(), HOJE.plusDays(10));   // 3 dias de atraso

        assertEquals(3.0, multa);
        assertEquals(2, livros.buscarPorIsbn("111").get().getQuantidadeDisponivel());
    }

    @Test
    void naoDevePermitirNovoEmprestimoParaLeitorComLivroEmAtraso() {
        service.registrarEmprestimo(CPF, "111", HOJE);
        LocalDate depoisDoPrazo = HOJE.plusDays(8);

        assertThrows(RegraNegocioException.class, () -> service.registrarEmprestimo(CPF, "222", depoisDoPrazo));
    }

    @Test
    void naoDevePermitirDevolverOMesmoEmprestimoDuasVezes() {
        Emprestimo emprestimo = service.registrarEmprestimo(CPF, "111", HOJE);
        service.registrarDevolucao(emprestimo.getId(), HOJE.plusDays(2));

        assertThrows(RegraNegocioException.class, () -> service.registrarDevolucao(emprestimo.getId(), HOJE.plusDays(3)));
    }

    @Test
    void deveListarApenasEmprestimosAtrasadosNoRelatorio() {
        service.registrarEmprestimo(CPF, "111", HOJE);
        service.registrarEmprestimo(CPF, "222", HOJE.plusDays(5));

        assertEquals(1, service.listarAtrasados(HOJE.plusDays(9)).size());
    }
}
