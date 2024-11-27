/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.model;

public class LivroBiblioteca {
    private int id;
    private String nome;
    private int paginas;
    private String isbn;
    private int edicao;


    public LivroBiblioteca(int id, String nome, int paginas, String isbn, int edicao) {
        this.id = id;
        this.nome = nome;
        this.paginas = paginas;
        this.isbn = isbn;
        this.edicao = edicao;
    }

   
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPaginas() { return paginas; }
    public String getIsbn() { return isbn; }
    public int getEdicao() { return edicao; }


    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPaginas(int paginas) { this.paginas = paginas; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setEdicao(int edicao) { this.edicao = edicao; }

    @Override
    public String toString() {
        return "Livro ID: " + id + ", Nome: " + nome + ", Paginas: " + paginas + ", ISBN: " + isbn + ", Edição: " + edicao;
    }
}
