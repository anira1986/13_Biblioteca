/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */



package br.edu.fateczl.biblioteca.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import br.edu.fateczl.biblioteca.R;

public class MainActivity extends AppCompatActivity {
    private Fragment fragmentAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            inicializarFragmentoPrincipal();
        } else {
            carregarFragmento(getIntent().getExtras());
        }
    }

    private void inicializarFragmentoPrincipal() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutFragment, new EmprestimoFragment());
        fragmentTransaction.commit();
    }

    private void carregarFragmento(Bundle bundle) {
        if (bundle == null) {
            fragmentAtual = new EmprestimoFragment();
        } else {
            String tipoFragmento = bundle.getString("tipo");
            fragmentAtual = obterFragmentoPorTipo(tipoFragmento);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragment, fragmentAtual);
        fragmentTransaction.commit();
    }

    private Fragment obterFragmentoPorTipo(String tipoFragmento) {
        switch (tipoFragmento) {
            case "livro":
                return new LivroFragment();
            case "revista":
                return new RevistaFragment();
            case "aluno":
                return new AlunoFragment();
            default:
                return new EmprestimoFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (id == R.id.itemLivro) {
            bundle.putString("tipo", "livro");
        } else if (id == R.id.itemRevista) {
            bundle.putString("tipo", "revista");
        } else if (id == R.id.itemAluno) {
            bundle.putString("tipo", "aluno");
        } else {
            bundle.putString("tipo", "emprestimo");
        }

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        return true;
    }
}
