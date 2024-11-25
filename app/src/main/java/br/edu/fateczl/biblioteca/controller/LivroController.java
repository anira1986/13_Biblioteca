/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */



package br.edu.fateczl.biblioteca.controller;

import br.edu.fateczl.biblioteca.model.LivroBiblioteca;

import java.sql.SQLException;
import java.util.List;

public class LivroController implements IController<LivroBiblioteca> {
    private final LivroDAO dao;

    public LivroController(LivroDAO dao) {
        this.dao = dao;
    }

    @Override
    public void adicionar(LivroBiblioteca livro) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.adicionar(livro);

        dao.fechar();
    }

    @Override
    public void atualizar(LivroBiblioteca livro) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.atualizar(livro);

        dao.fechar();
    }

    @Override
    public void remover(LivroBiblioteca livro) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        dao.remover(livro);

        dao.fechar();
    }

    @Override
    public LivroBiblioteca buscar(LivroBiblioteca livro) throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        LivroBiblioteca resultado = dao.buscar(livro);

        dao.fechar();
        return resultado;
    }

    @Override
    public List<LivroBiblioteca> listar() throws SQLException {
        if (dao.abrir() == null) {
            dao.abrir();
        }

        List<LivroBiblioteca> lista = dao.listar();

        dao.fechar();
        return lista;
    }
}
