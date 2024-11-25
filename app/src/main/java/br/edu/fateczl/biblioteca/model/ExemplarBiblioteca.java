/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */



package br.edu.fateczl.biblioteca.model;

import androidx.annotation.NonNull;

public abstract class ExemplarBiblioteca {
    private int id;
    private String nome;
    private int paginas;

    public ExemplarBiblioteca(int id, String nome, int paginas) {
        this.id = id;
        this.nome = nome;
        this.paginas = paginas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
