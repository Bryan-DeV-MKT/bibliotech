package br.uniube.biblioteca.exception;

/** Erro lançado quando uma regra de negócio da biblioteca é violada. */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
