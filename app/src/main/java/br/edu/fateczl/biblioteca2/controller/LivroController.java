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
        try {
            dao.abrir();
            dao.adicionar(livro);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void atualizar(LivroBiblioteca livro) throws SQLException {
        try {
            dao.abrir();
            dao.atualizar(livro);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void remover(LivroBiblioteca livro) throws SQLException {
        try {
            dao.abrir();
            dao.remover(livro);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public LivroBiblioteca buscar(LivroBiblioteca livro) throws SQLException {
        try {
            dao.abrir();
            return dao.buscar(livro);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public List<LivroBiblioteca> listar() throws SQLException {
        try {
            dao.abrir();
            return dao.listar();
        } finally {
            dao.fechar();
        }
    }
}
