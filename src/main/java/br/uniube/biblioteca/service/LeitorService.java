package br.uniube.biblioteca.service;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.model.Leitor;
import br.uniube.biblioteca.repository.LeitorRepository;
import java.util.List;

/** Caso de uso US02: cadastrar leitor. */
public class LeitorService {

    private final LeitorRepository repository;

    public LeitorService(LeitorRepository repository) {
        this.repository = repository;
    }

    public Leitor cadastrar(String cpf, String nome, String email) {
        String cpfLimpo = cpf == null ? "" : cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new RegraNegocioException("O CPF deve ter 11 dígitos.");
        }
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("O nome é obrigatório.");
        }
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new RegraNegocioException("E-mail inválido.");
        }
        if (repository.existePorCpf(cpfLimpo)) {
            throw new RegraNegocioException("Já existe um leitor com esse CPF.");
        }
        Leitor leitor = new Leitor(cpfLimpo, nome.trim(), email.trim());
        repository.salvar(leitor);
        return leitor;
    }

    public List<Leitor> listarTodos() {
        return repository.listarTodos();
    }
}
