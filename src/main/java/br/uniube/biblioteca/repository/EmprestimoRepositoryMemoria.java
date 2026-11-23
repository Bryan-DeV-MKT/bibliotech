package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Emprestimo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoRepositoryMemoria implements EmprestimoRepository {

    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private int ultimoId = 0;

    @Override
    public Emprestimo salvar(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
        return emprestimo;
    }

    @Override
    public Optional<Emprestimo> buscarPorId(int id) {
        return emprestimos.stream().filter(e -> e.getId() == id).findFirst();
    }

    @Override
    public List<Emprestimo> listarPorLeitor(String cpf) {
        return emprestimos.stream().filter(e -> e.getLeitor().getCpf().equals(cpf)).toList();
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    @Override
    public int proximoId() {
        return ++ultimoId;
    }
}
