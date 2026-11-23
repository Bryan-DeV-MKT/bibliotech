package br.uniube.biblioteca.model;

/** Pessoa cadastrada que pode pegar livros emprestados. */
public class Leitor {

    private final String cpf;
    private final String nome;
    private final String email;

    public Leitor(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
