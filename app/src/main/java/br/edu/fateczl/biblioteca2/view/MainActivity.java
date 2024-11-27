package br.edu.fateczl.biblioteca.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.fateczl.biblioteca.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAluno:
                // Exemplo de navegação para o fragmento de Aluno
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AlunoFragment())
                        .commit();
                return true;
            case R.id.itemLivro:
                // Exemplo de navegação para o fragmento de Livro
                return true;
            case R.id.itemRevista:
                // Exemplo de navegação para o fragmento de Revista
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
