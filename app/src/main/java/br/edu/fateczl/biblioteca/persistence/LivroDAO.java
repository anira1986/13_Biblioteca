/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.biblioteca.model.LivroBiblioteca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements ICRUDDAO<LivroBiblioteca> {
    private final Context contexto;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public LivroDAO(Context contexto) {
        this.contexto = contexto;
    }

    public LivroDAO abrir() throws SQLException {
        genericDAO = new GenericDAO(contexto);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    public void fechar() {
        genericDAO.close();
    }

    private static ContentValues getContentValues(LivroBiblioteca livro, boolean isSuper) {
        ContentValues values = new ContentValues();
        if (isSuper) {
            values.put("id", livro.getId());
            values.put("nome", livro.getNome());
            values.put("paginas", livro.getPaginas());
        } else {
            values.put("isbn", livro.getIsbn());
            values.put("edicao", livro.getEdicao());
            values.put("idExemplar", livro.getId());
        }
        return values;
    }

    @Override
    public void adicionar(LivroBiblioteca livro) throws SQLException {
        db.insert("exemplar", null, getContentValues(livro, true));
        db.insert("livro", null, getContentValues(livro, false));
    }

    @Override
    public void atualizar(LivroBiblioteca livro) throws SQLException {
        db.update("exemplar", getContentValues(livro, true), "id = ?", new String[]{String.valueOf(livro.getId())});
        db.update("livro", getContentValues(livro, false), "idExemplar = ?", new String[]{String.valueOf(livro.getId())});
    }

    @Override
    public void remover(LivroBiblioteca livro) throws SQLException {
        db.delete("livro", "idExemplar = ?", new String[]{String.valueOf(livro.getId())});
        db.delete("exemplar", "id = ?", new String[]{String.valueOf(livro.getId())});
    }

    @SuppressLint("Range")
    @Override
    public LivroBiblioteca buscar(LivroBiblioteca livro) throws SQLException {
        String query = "SELECT exemplar.id, exemplar.nome, exemplar.paginas, livro.isbn, livro.edicao " +
                "FROM exemplar INNER JOIN livro ON exemplar.id = livro.idExemplar " +
                "WHERE exemplar.id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(livro.getId())});

        if (cursor.moveToFirst()) {
            livro.setId(cursor.getInt(cursor.getColumnIndex("id")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setPaginas(cursor.getInt(cursor.getColumnIndex("paginas")));
            livro.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
        }

        cursor.close();
        return livro;
    }

    @SuppressLint("Range")
    @Override
    public List<LivroBiblioteca> listar() throws SQLException {
        List<LivroBiblioteca> livros = new ArrayList<>();

        String query = "SELECT exemplar.id, exemplar.nome, exemplar.paginas, livro.isbn, livro.edicao " +
                "FROM exemplar INNER JOIN livro ON exemplar.id = livro.idExemplar";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            LivroBiblioteca livro = new LivroBiblioteca(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getInt(cursor.getColumnIndex("paginas")),
                    cursor.getString(cursor.getColumnIndex("isbn")),
                    cursor.getInt(cursor.getColumnIndex("edicao"))
            );
            livros.add(livro);
        }

        cursor.close();
        return livros;
    }
}
