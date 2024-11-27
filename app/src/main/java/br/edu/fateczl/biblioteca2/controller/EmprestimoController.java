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
        try {
            dao.abrir();
            dao.adicionar(emprestimo);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void atualizar(EmprestimoBiblioteca emprestimo) throws SQLException {
        try {
            dao.abrir();
            dao.atualizar(emprestimo);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void remover(EmprestimoBiblioteca emprestimo) throws SQLException {
        try {
            dao.abrir();
            dao.remover(emprestimo);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public EmprestimoBiblioteca buscar(EmprestimoBiblioteca emprestimo) throws SQLException {
        try {
            dao.abrir();
            return dao.buscar(emprestimo);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public List<EmprestimoBiblioteca> listar() throws SQLException {
        try {
            dao.abrir();
            return dao.listar();
        } finally {
            dao.fechar();
        }
    }
}
