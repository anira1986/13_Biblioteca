/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class EmprestimoBiblioteca {
    private AlunoBiblioteca aluno;
    private ExemplarBiblioteca exemplar;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;

    public EmprestimoBiblioteca() {
        super();
    }

    public EmprestimoBiblioteca(AlunoBiblioteca aluno, ExemplarBiblioteca exemplar, LocalDate dataRetirada) {
        this.aluno = aluno;
        this.exemplar = exemplar;
        this.dataRetirada = dataRetirada;
    }

    public EmprestimoBiblioteca(AlunoBiblioteca aluno, ExemplarBiblioteca exemplar, LocalDate dataRetirada, LocalDate dataDevolucao) {
        this.aluno = aluno;
        this.exemplar = exemplar;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
    }

    public AlunoBiblioteca getAluno() {
        return aluno;
    }

    public void setAluno(AlunoBiblioteca aluno) {
        this.aluno = aluno;
    }

    public ExemplarBiblioteca getExemplar() {
        return exemplar;
    }

    public void setExemplar(ExemplarBiblioteca exemplar) {
        this.exemplar = exemplar;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @NonNull
    @Override
    public String toString() {
        return "Emprestimo {" +
                "Aluno='" + aluno.getNome() + "', " +
                "Exemplar='" + exemplar.getNome() + "', " +
                "Data Retirada='" + dataRetirada + "'}";
    }
}
