/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.controller;

import br.edu.fateczl.biblioteca.model.EmprestimoBiblioteca;
import br.edu.fateczl.biblioteca.persistence.EmprestimoDAO;

import java.sql.SQLException;
import java.util.List;

public class EmprestimoController implements IController<EmprestimoBiblioteca> {
    private final EmprestimoDAO dao;

    public EmprestimoController(EmprestimoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void adicionar(EmprestimoBiblioteca emprestimo) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.adicionar(emprestimo);

        dao.fechar();
    }

    @Override
    public void atualizar(EmprestimoBiblioteca emprestimo) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.atualizar(emprestimo);

        dao.fechar();
    }

    @Override
    public void remover(EmprestimoBiblioteca emprestimo) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.remover(emprestimo);

        dao.fechar();
    }

    @Override
    public EmprestimoBiblioteca buscar(EmprestimoBiblioteca emprestimo) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        EmprestimoBiblioteca resultado = dao.buscar(emprestimo);

        dao.fechar();
        return resultado;
    }

    @Override
    public List<EmprestimoBiblioteca> listar() throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        List<EmprestimoBiblioteca> lista = dao.listar();

        dao.fechar();
        return lista;
    }
}
