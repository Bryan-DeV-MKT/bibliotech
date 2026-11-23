package br.uniube.biblioteca.repository;

import br.uniube.biblioteca.model.Livro;
import java.util.List;
import java.util.Optional;

/** Contrato de acesso aos livros (padrão DAO). Quem usa não sabe onde os dados ficam. */
public interface LivroRepository {
    void salvar(Livro livro);
    Optional<Livro> buscarPorIsbn(String isbn);
    boolean existePorIsbn(String isbn);
    List<Livro> listarTodos();
}
