/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */



package br.edu.fateczl.biblioteca.model;

import androidx.annotation.NonNull;

public class RevistaBiblioteca extends ExemplarBiblioteca {
    private String issn;

    public RevistaBiblioteca(int id, String nome, int paginas, String issn) {
        super(id, nome, paginas);
        this.issn = issn;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
