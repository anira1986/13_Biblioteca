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
import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;
import br.edu.fateczl.biblioteca.model.EmprestimoBiblioteca;
import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements IEmprestimoDAO, ICRUDDAO<EmprestimoBiblioteca> {
    private final Context contexto;
    private BancoDadosDAO bancoDadosDAO;
    private SQLiteDatabase db;

    public EmprestimoDAO(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public EmprestimoDAO abrir() throws SQLException {
        bancoDadosDAO = new BancoDadosDAO(contexto);
        db = bancoDadosDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    @Override
    public void fechar() {
        bancoDadosDAO.close();
    }

    private static ContentValues getContentValues(EmprestimoBiblioteca emprestimo) {
        ContentValues values = new ContentValues();
        values.put("dataRetirada", emprestimo.getDataRetirada().toString());
        values.put("dataDevolucao", emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao().toString() : null);
        values.put("idAluno", emprestimo.getAluno().getId());
        values.put("idExemplar", emprestimo.getExemplar().getId());
        return values;
    }

    @Override
    public void adicionar(EmprestimoBiblioteca emprestimo) throws SQLException {
        db.insert("emprestimo", null, getContentValues(emprestimo));
    }

    @Override
    public void atualizar(EmprestimoBiblioteca emprestimo) throws SQLException {
        String whereClause = "dataRetirada = ? AND idAluno = ? AND idExemplar = ?";
        String[] whereArgs = {
                emprestimo.getDataRetirada().toString(),
                String.valueOf(emprestimo.getAluno().getId()),
                String.valueOf(emprestimo.getExemplar().getId())
        };
        db.update("emprestimo", getContentValues(emprestimo), whereClause, whereArgs);
    }

    @Override
    public void remover(EmprestimoBiblioteca emprestimo) throws SQLException {
        String whereClause = "dataRetirada = ? AND idAluno = ? AND idExemplar = ?";
        String[] whereArgs = {
                emprestimo.getDataRetirada().toString(),
                String.valueOf(emprestimo.getAluno().getId()),
                String.valueOf(emprestimo.getExemplar().getId())
        };
        db.delete("emprestimo", whereClause, whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public EmprestimoBiblioteca buscar(EmprestimoBiblioteca emprestimo) throws SQLException {
        String query = "SELECT emprestimo.dataRetirada, emprestimo.dataDevolucao, " +
                "aluno.id AS aId, aluno.nome AS aNome, aluno.email AS aEmail, " +
                "exemplar.id AS eId, exemplar.nome AS eNome, exemplar.paginas AS ePaginas, " +
                "livro.isbn AS lIsbn, livro.edicao AS lEdicao, " +
                "revista.issn AS rIssn " +
                "FROM emprestimo " +
                "INNER JOIN aluno ON emprestimo.idAluno = aluno.id " +
                "INNER JOIN exemplar ON emprestimo.idExemplar = exemplar.id " +
                "LEFT JOIN livro ON exemplar.id = livro.idExemplar " +
                "LEFT JOIN revista ON exemplar.id = revista.idExemplar " +
                "WHERE emprestimo.dataRetirada = ? AND emprestimo.idAluno = ? AND emprestimo.idExemplar = ?";

        String[] args = {
                emprestimo.getDataRetirada().toString(),
                String.valueOf(emprestimo.getAluno().getId()),
                String.valueOf(emprestimo.getExemplar().getId())
        };

        Cursor cursor = db.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            AlunoBiblioteca aluno = new AlunoBiblioteca(
                    cursor.getInt(cursor.getColumnIndex("aId")),
                    cursor.getString(cursor.getColumnIndex("aNome")),
                    cursor.getString(cursor.getColumnIndex("aEmail"))
            );
            emprestimo.setAluno(aluno);

            if (!cursor.isNull(cursor.getColumnIndex("lIsbn"))) {
                LivroBiblioteca livro = new LivroBiblioteca(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePaginas")),
                        cursor.getString(cursor.getColumnIndex("lIsbn")),
                        cursor.getInt(cursor.getColumnIndex("lEdicao"))
                );
                emprestimo.setExemplar(livro);
            } else {
                RevistaBiblioteca revista = new RevistaBiblioteca(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePaginas")),
                        cursor.getString(cursor.getColumnIndex("rIssn"))
                );
                emprestimo.setExemplar(revista);
            }

            emprestimo.setDataRetirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataRetirada"))));
            if (!cursor.isNull(cursor.getColumnIndex("dataDevolucao"))) {
                emprestimo.setDataDevolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataDevolucao"))));
            }
        }

        cursor.close();
        return emprestimo;
    }

    @SuppressLint("Range")
    @Override
    public List<EmprestimoBiblioteca> listar() throws SQLException {
        List<EmprestimoBiblioteca> emprestimos = new ArrayList<>();

        String query = "SELECT emprestimo.dataRetirada, emprestimo.dataDevolucao, " +
                "aluno.id AS aId, aluno.nome AS aNome, aluno.email AS aEmail, " +
                "exemplar.id AS eId, exemplar.nome AS eNome, exemplar.paginas AS ePaginas, " +
                "livro.isbn AS lIsbn, livro.edicao AS lEdicao, " +
                "revista.issn AS rIssn " +
                "FROM emprestimo " +
                "INNER JOIN aluno ON emprestimo.idAluno = aluno.id " +
                "INNER JOIN exemplar ON emprestimo.idExemplar = exemplar.id " +
                "LEFT JOIN livro ON exemplar.id = livro.idExemplar " +
                "LEFT JOIN revista ON exemplar.id = revista.idExemplar";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                EmprestimoBiblioteca emprestimo = new EmprestimoBiblioteca();

                AlunoBiblioteca aluno = new AlunoBiblioteca(
                        cursor.getInt(cursor.getColumnIndex("aId")),
                        cursor.getString(cursor.getColumnIndex("aNome")),
                        cursor.getString(cursor.getColumnIndex("aEmail"))
                );
                emprestimo.setAluno(aluno);

                if (!cursor.isNull(cursor.getColumnIndex("lIsbn"))) {
                    LivroBiblioteca livro = new LivroBiblioteca(
                            cursor.getInt(cursor.getColumnIndex("eId")),
                            cursor.getString(cursor.getColumnIndex("eNome")),
                            cursor.getInt(cursor.getColumnIndex("ePaginas")),
                            cursor.getString(cursor.getColumnIndex("lIsbn")),
                            cursor.getInt(cursor.getColumnIndex("lEdicao"))
                    );
                    emprestimo.setExemplar(livro);
                } else {
                    RevistaBiblioteca revista = new RevistaBiblioteca(
                            cursor.getInt(cursor.getColumnIndex("eId")),
                            cursor.getString(cursor.getColumnIndex("eNome")),
                            cursor.getInt(cursor.getColumnIndex("ePaginas")),
                            cursor.getString(cursor.getColumnIndex("rIssn"))
                    );
                    emprestimo.setExemplar(revista);
                }

                emprestimo.setDataRetirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataRetirada"))));
                if (!cursor.isNull(cursor.getColumnIndex("dataDevolucao"))) {
                    emprestimo.setDataDevolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataDevolucao"))));
                }

                emprestimos.add(emprestimo);

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return emprestimos;
    }
}
