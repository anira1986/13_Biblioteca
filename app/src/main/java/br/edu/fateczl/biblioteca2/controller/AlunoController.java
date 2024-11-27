package br.edu.fateczl.biblioteca.controller;

import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;
import br.edu.fateczl.biblioteca.persistence.AlunoDAO;

import java.sql.SQLException;
import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public void registrar(AlunoBiblioteca aluno) throws SQLException {
        alunoDAO.adicionar(aluno);
    }

    public void atualizar(AlunoBiblioteca aluno) throws SQLException {
        alunoDAO.atualizar(aluno);
    }

    public void remover(AlunoBiblioteca aluno) throws SQLException {
        alunoDAO.remover(aluno);
    }

    public List<AlunoBiblioteca> listar() throws SQLException {
        return alunoDAO.listar();
    }
}
