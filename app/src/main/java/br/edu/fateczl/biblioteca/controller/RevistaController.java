/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.controller;

import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;
import br.edu.fateczl.biblioteca.persistence.RevistaDAO;

import java.sql.SQLException;
import java.util.List;

public class RevistaController implements IController<RevistaBiblioteca> {
    private final RevistaDAO dao;

    public RevistaController(RevistaDAO dao) {
        this.dao = dao;
    }

    @Override
    public void adicionar(RevistaBiblioteca revista) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.adicionar(revista);

        dao.fechar();
    }

    @Override
    public void atualizar(RevistaBiblioteca revista) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.atualizar(revista);

        dao.fechar();
    }

    @Override
    public void remover(RevistaBiblioteca revista) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.remover(revista);

        dao.fechar();
    }

    @Override
    public RevistaBiblioteca buscar(RevistaBiblioteca revista) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        RevistaBiblioteca resultado = dao.buscar(revista);

        dao.fechar();
        return resultado;
    }

    @Override
    public List<RevistaBiblioteca> listar() throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        List<RevistaBiblioteca> lista = dao.listar();

        dao.fechar();
        return lista;
    }
}
