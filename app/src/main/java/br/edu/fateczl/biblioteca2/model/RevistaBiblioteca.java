/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.model;

public class RevistaBiblioteca {
    private int exemplarId;
    private String exemplarNome;
    private int exemplarPaginas;
    private String revistaISSN;

    // Construtor
    public RevistaBiblioteca(int exemplarId, String exemplarNome, int exemplarPaginas, String revistaISSN) {
        this.exemplarId = exemplarId;
        this.exemplarNome = exemplarNome;
        this.exemplarPaginas = exemplarPaginas;
        this.revistaISSN = revistaISSN;
    }

    // Getters
    public int getExemplarId() { return exemplarId; }
    public String getExemplarNome() { return exemplarNome; }
    public int getExemplarPaginas() { return exemplarPaginas; }
    public String getRevistaISSN() { return revistaISSN; }

    // Setters
    public void setExemplarId(int exemplarId) { this.exemplarId = exemplarId; }
    public void setExemplarNome(String exemplarNome) { this.exemplarNome = exemplarNome; }
    public void setExemplarPaginas(int exemplarPaginas) { this.exemplarPaginas = exemplarPaginas; }
    public void setRevistaISSN(String revistaISSN) { this.revistaISSN = revistaISSN; }

    @Override
    public String toString() {
        return "Revista ID: " + exemplarId + ", Nome: " + exemplarNome + ", Paginas: " + exemplarPaginas + ", ISSN: " + revistaISSN;
    }
}
