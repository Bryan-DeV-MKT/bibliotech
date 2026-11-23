package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Leitor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LeitorRepositoryMemoria implements LeitorRepository {

    private final Map<String, Leitor> leitores = new LinkedHashMap<>();

    @Override
    public void salvar(Leitor leitor) {
        leitores.put(leitor.getCpf(), leitor);
    }

    @Override
    public Optional<Leitor> buscarPorCpf(String cpf) {
        return Optional.ofNullable(leitores.get(cpf));
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return leitores.containsKey(cpf);
    }

    @Override
    public List<Leitor> listarTodos() {
        return new ArrayList<>(leitores.values());
    }
}
