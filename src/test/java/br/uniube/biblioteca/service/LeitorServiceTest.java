package br.uniube.biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.uniube.biblioteca.exception.RegraNegocioException;
import br.uniube.biblioteca.repository.LeitorRepositoryMemoria;
import org.junit.jupiter.api.Test;

class LeitorServiceTest {

    private final LeitorService service = new LeitorService(new LeitorRepositoryMemoria());

    @Test
    void deveCadastrarLeitorGuardandoCpfSomenteComNumeros() {
        assertEquals("12345678901", service.cadastrar("123.456.789-01", "Ana", "ana@email.com").getCpf());
    }

    @Test
    void naoDevePermitirCpfRepetido() {
        service.cadastrar("12345678901", "Ana", "ana@email.com");
        assertThrows(RegraNegocioException.class,
                () -> service.cadastrar("123.456.789-01", "Outra Ana", "outra@email.com"));
    }

    @Test
    void naoDeveAceitarEmailSemArroba() {
        assertThrows(RegraNegocioException.class,
                () -> service.cadastrar("98765432100", "Bruno", "bruno.email.com"));
    }
}
