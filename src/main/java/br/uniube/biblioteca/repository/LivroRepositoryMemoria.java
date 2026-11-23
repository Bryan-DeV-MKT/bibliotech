package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Livro;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Implementação em memória. Para usar banco de dados, basta criar outra implementação. */
public class LivroRepositoryMemoria implements LivroRepository {

    private final Map<String, Livro> livros = new LinkedHashMap<>();

    @Override
    public void salvar(Livro livro) {
        livros.put(livro.getIsbn(), livro);
    }

    @Override
    public Optional<Livro> buscarPorIsbn(String isbn) {
        return Optional.ofNullable(livros.get(isbn));
    }

    @Override
    public boolean existePorIsbn(String isbn) {
        return livros.containsKey(isbn);
    }

    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros.values());
    }
}
