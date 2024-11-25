

/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

package br.edu.fateczl.biblioteca.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fateczl.biblioteca.R;
import br.edu.fateczl.biblioteca.controller.LivroController;
import br.edu.fateczl.biblioteca.model.LivroBiblioteca;
import br.edu.fateczl.biblioteca.persistence.LivroDAO;

import java.sql.SQLException;
import java.util.List;

public class LivroFragment extends Fragment {
    private View view;
    private EditText editTextLivroId;
    private EditText editTextLivroNome;
    private EditText editTextLivroPaginas;
    private EditText editTextLivroISBN;
    private EditText editTextLivroEdicao;
    private TextView textViewLivroSaida;
    private LivroController livroController;

    public LivroFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_livro, container, false);

        editTextLivroId = view.findViewById(R.id.editTextLivroId);
        editTextLivroNome = view.findViewById(R.id.editTextLivroNome);
        editTextLivroPaginas = view.findViewById(R.id.editTextLivroPaginas);
        editTextLivroISBN = view.findViewById(R.id.editTextLivroISBN);
        editTextLivroEdicao = view.findViewById(R.id.editTextLivroEdicao);
        textViewLivroSaida = view.findViewById(R.id.textViewLivroSaida);
        textViewLivroSaida.setMovementMethod(new ScrollingMovementMethod());

        livroController = new LivroController(new LivroDAO(this.getContext()));

        view.findViewById(R.id.buttonLivroBuscar).setOnClickListener(buscar -> buscarEntrada());
        view.findViewById(R.id.buttonLivroCadastrar).setOnClickListener(cadastrar -> cadastrarEntrada());
        view.findViewById(R.id.buttonLivroAtualizar).setOnClickListener(atualizar -> atualizarEntrada());
        view.findViewById(R.id.buttonLivroRemover).setOnClickListener(remover -> removerEntrada());
        view.findViewById(R.id.buttonLivroListar).setOnClickListener(listar -> listarEntrada());

        return view;
    }

    private void buscarEntrada() {
        LivroBiblioteca livro;

        try {
            livro = livroController.buscarEntrada(new LivroBiblioteca(
                    Integer.parseInt(editTextLivroId.getText().toString()),
                    null,
                    0,
                    null,
                    0)
            );

            if (livro.getNome() != null) {
                preencherCampos(livro);
            } else {
                Toast.makeText(
                        view.getContext(), "Livro não encontrado", Toast.LENGTH_LONG).show();

                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void cadastrarEntrada() {
        try {
            livroController.cadastrarEntrada(lerCampos());

            Toast.makeText(
                    view.getContext(), "Livro cadastrado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void atualizarEntrada() {
        try {
            livroController.atualizarEntrada(lerCampos());

            Toast.makeText(
                    view.getContext(), "Livro atualizado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void removerEntrada() {
        try {
            livroController.removerEntrada(
                    new LivroBiblioteca(Integer.parseInt(editTextLivroId.getText().toString()), null, 0, null, 0));

            Toast.makeText(
                    view.getContext(), "Livro removido com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private LivroBiblioteca lerCampos() throws SQLException {
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
}
