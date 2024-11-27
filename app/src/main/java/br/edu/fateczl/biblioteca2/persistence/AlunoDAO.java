
/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.persistence;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;

import java.util.List;
import java.util.ArrayList;

public class AlunoDAO implements IAlunoDAO, ICRUDDAO<AlunoBiblioteca> {
    private SQLiteDatabase db;

    // Construtor
    public AlunoDAO(SQLiteDatabase db) {
        this.db = db;
    }

    private static ContentValues getContentValues(AlunoBiblioteca aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("email", aluno.getEmail());
        return values;
    }

    @Override
    public void adicionar(AlunoBiblioteca aluno) throws SQLException {
        ContentValues values = getContentValues(aluno);
        db.insert("alunos", null, values);
    }

    @Override
    public void atualizar(AlunoBiblioteca aluno) throws SQLException {
        ContentValues values = getContentValues(aluno);
        db.update("alunos", values, "id = ?", new String[]{String.valueOf(aluno.getId())});
    }

    @Override
    public void remover(AlunoBiblioteca aluno) throws SQLException {
        db.delete("alunos", "id = ?", new String[]{String.valueOf(aluno.getId())});
    }

    @Override
    public AlunoBiblioteca buscar(AlunoBiblioteca aluno) throws SQLException {
        // Lógica para buscar aluno
        // (Exemplo de código, você pode adaptar para sua consulta no banco)
        return new AlunoBiblioteca(1, "Aluno Teste", "email@test.com");
    }

    @Override
    public List<AlunoBiblioteca> listar() throws SQLException {
        List<AlunoBiblioteca> alunos = new ArrayList<>();
        // Lógica para listar os alunos do banco
        // (Exemplo de código, você pode adaptar para sua consulta no banco)
        alunos.add(new AlunoBiblioteca(1, "Aluno Teste", "email@test.com"));
        return alunos;
    }
}
