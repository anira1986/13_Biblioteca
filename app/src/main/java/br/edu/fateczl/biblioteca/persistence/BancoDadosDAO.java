/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDadosDAO extends SQLiteOpenHelper {
    private static final String DATABASE = "BIBLIOTECA.DB";
    private static final int DATABASE_VER = 1;

    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE aluno( " +
                    "id INTEGER PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL);";

    private static final String CREATE_TABLE_EXEMPLAR =
            "CREATE TABLE exemplar( " +
                    "id INTEGER PRIMARY KEY, " +
                    "nome VARCHAR(50) NOT NULL, " +
                    "paginas INTEGER NOT NULL);";

    private static final String CREATE_TABLE_LIVRO =
            "CREATE TABLE livro( " +
                    "isbn CHAR(13) NOT NULL, " +
                    "edicao INTEGER NOT NULL, " +
                    "idExemplar INTEGER, " +
                    "FOREIGN KEY (idExemplar) REFERENCES exemplar (id) ON DELETE CASCADE);";

    private static final String CREATE_TABLE_REVISTA =
            "CREATE TABLE revista( " +
                    "issn CHAR(8) NOT NULL, " +
                    "idExemplar INTEGER, " +
                    "FOREIGN KEY (idExemplar) REFERENCES exemplar (id) ON DELETE CASCADE);";

    private static final String CREATE_TABLE_EMPRESTIMO =
            "CREATE TABLE emprestimo( " +
                    "dataRetirada VARCHAR(10) NOT NULL, " +
                    "dataDevolucao VARCHAR(10), " +
                    "idAluno INTEGER,  " +
                    "idExemplar INTEGER, " +
                    "FOREIGN KEY (idAluno) REFERENCES aluno (id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (idExemplar) REFERENCES exemplar (id) ON DELETE CASCADE, " +
                    "PRIMARY KEY (dataRetirada, idAluno, idExemplar));";

    public BancoDadosDAO(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_EXEMPLAR);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_REVISTA);
        db.execSQL(CREATE_TABLE_EMPRESTIMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS emprestimo");
            db.execSQL("DROP TABLE IF EXISTS revista");
            db.execSQL("DROP TABLE IF EXISTS livro");
            db.execSQL("DROP TABLE IF EXISTS exemplar");
            db.execSQL("DROP TABLE IF EXISTS aluno");

            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS emprestimo");
            db.execSQL("DROP TABLE IF EXISTS revista");
            db.execSQL("DROP TABLE IF EXISTS livro");
            db.execSQL("DROP TABLE IF EXISTS exemplar");
            db.execSQL("DROP TABLE IF EXISTS aluno");

            onCreate(db);
        }
    }
}
