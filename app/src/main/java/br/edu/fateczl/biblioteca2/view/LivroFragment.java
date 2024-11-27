/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.edu.fateczl.biblioteca.R;
import br.edu.fateczl.biblioteca.controller.LivroController;
import br.edu.fateczl.biblioteca.model.LivroBiblioteca;
import br.edu.fateczl.biblioteca.persistence.LivroDAO;

import java.sql.SQLException;
import java.util.List;

public class LivroFragment extends Fragment {
    private View view;
    private EditText editTextLivroId, editTextLivroNome, editTextLivroPaginas, editTextLivroISBN, editTextLivroEdicao;
    private TextView textViewLivroSaida;
    private LivroController livroController;

    public LivroFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_livro, container, false);
        initializeViews();
        setupController();
        setButtonListeners();

        return view;
    }

    private void initializeViews() {
        editTextLivroId = view.findViewById(R.id.editTextLivroId);
        editTextLivroNome = view.findViewById(R.id.editTextLivroNome);
        editTextLivroPaginas = view.findViewById(R.id.editTextLivroPaginas);
        editTextLivroISBN = view.findViewById(R.id.editTextLivroISBN);
        editTextLivroEdicao = view.findViewById(R.id.editTextLivroEdicao);
        textViewLivroSaida = view.findViewById(R.id.textViewLivroSaida);
        textViewLivroSaida.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setupController() {
        livroController = new LivroController(new LivroDAO(this.getContext()));
    }

    private void setButtonListeners() {
        view.findViewById(R.id.buttonLivroBuscar).setOnClickListener(v -> buscarEntrada());
        view.findViewById(R.id.buttonLivroCadastrar).setOnClickListener(v -> cadastrarEntrada());
        view.findViewById(R.id.buttonLivroAtualizar).setOnClickListener(v -> atualizarEntrada());
        view.findViewById(R.id.buttonLivroRemover).setOnClickListener(v -> removerEntrada());
        view.findViewById(R.id.buttonLivroListar).setOnClickListener(v -> listarEntrada());
    }

    private void buscarEntrada() {
        try {
            LivroBiblioteca livro = livroController.buscarEntrada(createLivroFromFields());

            if (livro.getNome() != null) {
                preencherCampos(livro);
            } else {
                showToast("Livro não encontrado");
                limparCampos();
            }
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private void cadastrarEntrada() {
        try {
            livroController.cadastrarEntrada(createLivroFromFields());
            showToast("Livro cadastrado com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void atualizarEntrada() {
        try {
            livroController.atualizarEntrada(createLivroFromFields());
            showToast("Livro atualizado com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void removerEntrada() {
        try {
            livroController.removerEntrada(createLivroFromFields());
            showToast("Livro removido com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void listarEntrada() {
        try {
            List<LivroBiblioteca> livros = livroController.listarEntrada();
            StringBuilder stringBuffer = new StringBuilder();
            for (LivroBiblioteca livro : livros) {
                stringBuffer.append(livro.toString()).append("\n");
            }
            textViewLivroSaida.setText(stringBuffer.toString());
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private LivroBiblioteca createLivroFromFields() throws SQLException {
        if (!validarCampos()) {
            throw new SQLException("Entrada inválida");
        }

        return new LivroBiblioteca(
                Integer.parseInt(editTextLivroId.getText().toString()),
                editTextLivroNome.getText().toString(),
                Integer.parseInt(editTextLivroPaginas.getText().toString()),
                editTextLivroISBN.getText().toString(),
                Integer.parseInt(editTextLivroEdicao.getText().toString())
        );
    }

    private boolean validarCampos() {
        return editTextLivroId.length() > 0 &&
                editTextLivroNome.length() > 0 &&
                editTextLivroPaginas.length() > 0 &&
                editTextLivroISBN.length() > 0 &&
                editTextLivroEdicao.length() > 0;
    }

    private void preencherCampos(LivroBiblioteca livro) {
        editTextLivroId.setText(String.valueOf(livro.getId()));
        editTextLivroNome.setText(livro.getNome());
        editTextLivroPaginas.setText(String.valueOf(livro.getPaginas()));
        editTextLivroISBN.setText(livro.getISBN());
        editTextLivroEdicao.setText(String.valueOf(livro.getEdicao()));
    }

    private void limparCampos() {
        editTextLivroId.setText("");
        editTextLivroNome.setText("");
        editTextLivroPaginas.setText("");
        editTextLivroISBN.setText("");
        editTextLivroEdicao.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
