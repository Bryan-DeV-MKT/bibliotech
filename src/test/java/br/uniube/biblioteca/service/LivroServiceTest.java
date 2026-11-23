package br.uniube.biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.repository.LivroRepositoryMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LivroServiceTest {

    private LivroService service;

    @BeforeEach
    void prepararCenario() {
        service = new LivroService(new LivroRepositoryMemoria());
        service.cadastrar("111", "Dom Casmurro", "Machado de Assis", 2);
    }

    @Test
    void naoDevePermitirCadastrarLivroComIsbnRepetido() {
        assertThrows(RegraNegocioException.class,
                () -> service.cadastrar("111", "Outro título", "Outro autor", 1));
    }

    @Test
    void naoDevePermitirQuantidadeZero() {
        assertThrows(RegraNegocioException.class,
                () -> service.cadastrar("999", "Livro Novo", "Autor", 0));
    }

    @Test
    void devePesquisarPorAutorSemDiferenciarMaiusculas() {
        assertEquals(1, service.pesquisar("MACHADO").size());
        assertTrue(service.pesquisar("tolkien").isEmpty());
    }
}
