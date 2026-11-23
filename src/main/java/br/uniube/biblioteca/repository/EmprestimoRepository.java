package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Emprestimo;
import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository {
    Emprestimo salvar(Emprestimo emprestimo);
    Optional<Emprestimo> buscarPorId(int id);
    List<Emprestimo> listarPorLeitor(String cpf);
    List<Emprestimo> listarTodos();
    int proximoId();
}
