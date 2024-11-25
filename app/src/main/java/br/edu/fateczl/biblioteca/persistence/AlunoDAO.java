
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

import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements IAlunoDAO, ICRUDDAO<AlunoBiblioteca> {
    private final Context contexto;
    private BancoDadosDAO bancoDadosDAO;
    private SQLiteDatabase db;

    public AlunoDAO(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public AlunoDAO abrir() throws SQLException {
        bancoDadosDAO = new BancoDadosDAO(contexto);
        db = bancoDadosDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    @Override
    public void fechar() {
        bancoDadosDAO.close();
    }

    private static ContentValues getContentValues(AlunoBiblioteca aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", aluno.getId());
        contentValues.put("nome", aluno.getNome());
        contentValues.put("email", aluno.getEmail());

        return contentValues;
    }

    @Override
    public void adicionar(AlunoBiblioteca aluno) throws SQLException {
        db.insert("aluno", null, getContentValues(aluno));
    }

    @Override
    public void atualizar(AlunoBiblioteca aluno) throws SQLException {
        db.update("aluno", getContentValues(aluno),
                "id = " + aluno.getId(), null);
    }

    @Override
    public void remover(AlunoBiblioteca aluno) throws SQLException {
        db.delete("aluno", "id = " + aluno.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public AlunoBiblioteca buscar(AlunoBiblioteca aluno) throws SQLException {
        String querySQL = "SELECT " +
                "id, nome, email " +
                "FROM aluno " +
                "WHERE id = " + aluno.getId();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null && cursor.moveToFirst()) {
            aluno.setId(cursor.getInt(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }

        if (cursor != null) {
            cursor.close();
        }

        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<AlunoBiblioteca> listar() throws SQLException {
        List<AlunoBiblioteca> alunos = new ArrayList<>();

        String querySQL = "SELECT " +
                "id, nome, email " +
                "FROM aluno";

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                AlunoBiblioteca aluno = new AlunoBiblioteca(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nome")),
                        cursor.getString(cursor.getColumnIndex("email"))
                );

                alunos.add(aluno);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return alunos;
    }
}
