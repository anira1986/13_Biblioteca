package br.edu.fateczl.biblioteca.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAO implements IRevistaDAO, ICRUDDAO<RevistaBiblioteca> {
    private final Context contexto;
    private BancoDadosDAO bancoDadosDAO;
    private SQLiteDatabase db;

    public RevistaDAO(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public RevistaDAO abrir() throws SQLException {
        bancoDadosDAO = new BancoDadosDAO(contexto);
        db = bancoDadosDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    @Override
    public void fechar() {
        bancoDadosDAO.close();
    }

    private static ContentValues getContentValues(RevistaBiblioteca revista, boolean isSuper) {
        ContentValues values = new ContentValues();
        if (isSuper) {
            values.put("id", revista.getId());
            values.put("nome", revista.getNome());
            values.put("paginas", revista.getPaginas());
        } else {
            values.put("issn", revista.getIssn());
            values.put("idExemplar", revista.getId());
        }
        return values;
    }

    @Override
    public void adicionar(RevistaBiblioteca revista) throws SQLException {
        db.insert("exemplar", null, getContentValues(revista, true));
        db.insert("revista", null, getContentValues(revista, false));
    }

    @Override
    public void atualizar(RevistaBiblioteca revista) throws SQLException {
        String[] args = { String.valueOf(revista.getId()) };
        db.update("exemplar", getContentValues(revista, true), "id = ?", args);
        db.update("revista", getContentValues(revista, false), "idExemplar = ?", args);
    }

    @Override
    public void remover(RevistaBiblioteca revista) throws SQLException {
        String[] args = { String.valueOf(revista.getId()) };
        db.delete("revista", "idExemplar = ?", args);
        db.delete("exemplar", "id = ?", args);
    }

    @SuppressLint("Range")
    @Override
    public RevistaBiblioteca buscar(RevistaBiblioteca revista) throws SQLException {
        String query = "SELECT exemplar.id, exemplar.nome, exemplar.paginas, revista.issn " +
                "FROM exemplar INNER JOIN revista ON exemplar.id = revista.idExemplar " +
                "WHERE exemplar.id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ String.valueOf(revista.getId()) });

        if (cursor.moveToFirst()) {
            revista.setId(cursor.getInt(cursor.getColumnIndex("id")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setPaginas(cursor.getInt(cursor.getColumnIndex("paginas")));
            revista.setIssn(cursor.getString(cursor.getColumnIndex("issn")));
        }

        cursor.close();
        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<RevistaBiblioteca> listar() throws SQLException {
        List<RevistaBiblioteca> revistas = new ArrayList<>();

        String query = "SELECT exemplar.id, exemplar.nome, exemplar.paginas, revista.issn " +
                "FROM exemplar INNER JOIN revista ON exemplar.id = revista.idExemplar";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            RevistaBiblioteca revista = new RevistaBiblioteca(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getInt(cursor.getColumnIndex("paginas")),
                    cursor.getString(cursor.getColumnIndex("issn"))
            );
            revistas.add(revista);
        }

        cursor.close();
        return revistas;
    }
}
