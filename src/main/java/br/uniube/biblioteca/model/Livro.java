package br.uniube.biblioteca.model;

/** Livro do acervo. Guarda quantos exemplares existem e quantos estão na estante. */
public class Livro {

    private final String isbn;
    private final String titulo;
    private final String autor;
    private final int quantidadeTotal;
    private int quantidadeDisponivel;

    public Livro(String isbn, String titulo, String autor, int quantidadeTotal) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeTotal;
    }

    public boolean temExemplarDisponivel() {
        return quantidadeDisponivel > 0;
    }

    public void retirarExemplar() {
        if (!temExemplarDisponivel()) {
            throw new IllegalStateException("Não há exemplar disponível de " + titulo);
        }
        quantidadeDisponivel--;
    }

    public void devolverExemplar() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
        }
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getQuantidadeTotal() { return quantidadeTotal; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
}
