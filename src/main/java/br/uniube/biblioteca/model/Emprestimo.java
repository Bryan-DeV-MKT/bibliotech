package br.uniube.biblioteca.model;

import java.time.LocalDate;

/** Registro da saída de um livro para um leitor. */
public class Emprestimo {

    private final int id;
    private final Leitor leitor;
    private final Livro livro;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private double multa;

    public Emprestimo(int id, Leitor leitor, Livro livro, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this.id = id;
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
    }

    public boolean isAtivo() {
        return dataDevolucao == null;
    }

    /** Ativo e com a data prevista já ultrapassada na data informada. */
    public boolean estaAtrasado(LocalDate hoje) {
        return isAtivo() && hoje.isAfter(dataPrevista);
    }

    public void registrarDevolucao(LocalDate data, double multa) {
        this.dataDevolucao = data;
        this.multa = multa;
    }

    public int getId() { return id; }
    public Leitor getLeitor() { return leitor; }
    public Livro getLivro() { return livro; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public double getMulta() { return multa; }
}
