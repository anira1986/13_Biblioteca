/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.model;

import androidx.annotation.NonNull;

public class LivroBiblioteca extends ExemplarBiblioteca {
    private String isbn;
    private int edicao;

    public LivroBiblioteca(int id, String nome, int paginas, String isbn, int edicao) {
        super(id, nome, paginas);
        this.isbn = isbn;
        this.edicao = edicao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
