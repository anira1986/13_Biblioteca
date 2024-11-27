/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.persistence;

import android.content.ContentValues;
import android.content.Context;
import br.edu.fateczl.biblioteca.model.LivroBiblioteca;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private GenericDAO genericDAO;

    public LivroDAO(Context contexto) {
        genericDAO = new GenericDAO(contexto);
    }

    private static ContentValues getContentValues(LivroBiblioteca livro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", livro.getNome());
        contentValues.put("paginas", livro.getPaginas());
        contentValues.put("isbn", livro.getIsbn());
        contentValues.put("edicao", livro.getEdicao());
        return contentValues;
    }

    public void adicionar(LivroBiblioteca livro) throws SQLException {
        genericDAO.inserir("livros", getContentValues(livro));
    }

    public void atualizar(LivroBiblioteca livro) throws SQLException {
        genericDAO.atualizar("livros", getContentValues(livro), "id = ?", new String[]{String.valueOf(livro.getId())});
    }

    public void remover(LivroBiblioteca livro) throws SQLException {
        genericDAO.remover("livros", "id = ?", new String[]{String.valueOf(livro.getId())});
    }

    public LivroBiblioteca buscar(LivroBiblioteca livro) throws SQLException {
        List<LivroBiblioteca> livros = genericDAO.buscar("livros", LivroBiblioteca.class, "id = ?", new String[]{String.valueOf(livro.getId())});
        return livros.isEmpty() ? null : livros.get(0);
    }

    public List<LivroBiblioteca> listar() throws SQLException {
        return genericDAO.listar("livros", LivroBiblioteca.class);
    }
}
