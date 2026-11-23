package br.uniube.biblioteca.service;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.model.Livro;
import br.uniube.biblioteca.repository.LivroRepository;
import java.util.List;

/** Casos de uso do acervo: US01 (cadastrar livro) e US03 (pesquisar livro). */
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public Livro cadastrar(String isbn, String titulo, String autor, int quantidade) {
        if (vazio(isbn) || vazio(titulo) || vazio(autor)) {
            throw new RegraNegocioException("ISBN, título e autor são obrigatórios.");
        }
        if (quantidade < 1) {
            throw new RegraNegocioException("A quantidade de exemplares deve ser pelo menos 1.");
        }
        if (repository.existePorIsbn(isbn)) {
            throw new RegraNegocioException("Já existe um livro com o ISBN " + isbn + ".");
        }
        Livro livro = new Livro(isbn.trim(), titulo.trim(), autor.trim(), quantidade);
        repository.salvar(livro);
        return livro;
    }

    /** Procura pelo título ou pelo autor, sem diferenciar maiúsculas de minúsculas. */
    public List<Livro> pesquisar(String termo) {
        String busca = termo == null ? "" : termo.trim().toLowerCase();
        return repository.listarTodos().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(busca)
                        || l.getAutor().toLowerCase().contains(busca))
                .toList();
    }

    public List<Livro> listarTodos() {
        return repository.listarTodos();
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
