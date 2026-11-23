package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Leitor;
import java.util.List;
import java.util.Optional;

public interface LeitorRepository {
    void salvar(Leitor leitor);
    Optional<Leitor> buscarPorCpf(String cpf);
    boolean existePorCpf(String cpf);
    List<Leitor> listarTodos();
}
