/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.controller;

import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;
import br.edu.fateczl.biblioteca.persistence.AlunoDAO;

import java.sql.SQLException;
import java.util.List;

public class AlunoController implements IController<AlunoBiblioteca> {
    private final AlunoDAO dao;

    public AlunoController(AlunoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void adicionar(AlunoBiblioteca aluno) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.adicionar(aluno);

        dao.fechar();
    }

    @Override
    public void atualizar(AlunoBiblioteca aluno) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.atualizar(aluno);

        dao.fechar();
    }

    @Override
    public void remover(AlunoBiblioteca aluno) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.remover(aluno);

        dao.fechar();
    }

    @Override
    public AlunoBiblioteca buscar(AlunoBiblioteca aluno) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        AlunoBiblioteca resultado = dao.buscar(aluno);

        dao.fechar();
        return resultado;
    }

    @Override
    public List<AlunoBiblioteca> listar() throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        List<AlunoBiblioteca> lista = dao.listar();

        dao.fechar();
        return lista;
    }
}
