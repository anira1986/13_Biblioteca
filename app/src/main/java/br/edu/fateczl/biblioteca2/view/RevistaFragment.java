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
import br.edu.fateczl.biblioteca.controller.RevistaController;
import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;
import br.edu.fateczl.biblioteca.persistence.RevistaDAO;

import java.sql.SQLException;
import java.util.List;

public class RevistaFragment extends Fragment {
    private EditText editTextRevistaId, editTextRevistaNome, editTextRevistaPaginas, editTextRevistaISSN;
    private TextView textViewSaidaRevista;
    private RevistaController revistaController;

    public RevistaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revista, container, false);

        initializeViews(view);
        initializeController();
        setListeners();

        return view;
    }

    private void initializeViews(View view) {
        editTextRevistaId = view.findViewById(R.id.editTextRevistaId);
        editTextRevistaNome = view.findViewById(R.id.editTextRevistaNome);
        editTextRevistaPaginas = view.findViewById(R.id.editTextRevistaPaginas);
        editTextRevistaISSN = view.findViewById(R.id.editTextRevistaISSN);
        textViewSaidaRevista = view.findViewById(R.id.textViewSaidaRevista);
        textViewSaidaRevista.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initializeController() {
        revistaController = new RevistaController(new RevistaDAO(this.getContext()));
    }

    private void setListeners() {
        view.findViewById(R.id.buttonRevistaBuscar).setOnClickListener(v -> buscarRegistro());
        view.findViewById(R.id.buttonRevistaRegistrar).setOnClickListener(v -> registrarRegistro());
        view.findViewById(R.id.buttonRevistaAtualizar).setOnClickListener(v -> atualizarRegistro());
        view.findViewById(R.id.buttonRevistaRemover).setOnClickListener(v -> removerRegistro());
        view.findViewById(R.id.buttonRevistaListar).setOnClickListener(v -> listarRegistros());
    }

    private void buscarRegistro() {
        try {
            RevistaBiblioteca revista = revistaController.buscarRegistro(createRevistaFromFields());
            if (revista.getExemplarNome() != null) {
                preencherCampos(revista);
            } else {
                showToast("Revista não encontrada");
                limparCampos();
            }
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private void registrarRegistro() {
        try {
            revistaController.registrarRegistro(createRevistaFromFields());
            showToast("Revista registrada com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void atualizarRegistro() {
        try {
            revistaController.atualizarRegistro(createRevistaFromFields());
            showToast("Revista atualizada com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void removerRegistro() {
        try {
            revistaController.removerRegistro(createRevistaFromFields());
            showToast("Revista removida com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void listarRegistros() {
        try {
            List<RevistaBiblioteca> revistas = revistaController.listarRegistros();
            StringBuilder stringBuffer = new StringBuilder();
            for (RevistaBiblioteca revista : revistas) {
                stringBuffer.append(revista.toString()).append("\n");
            }
            textViewSaidaRevista.setText(stringBuffer.toString());
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private RevistaBiblioteca createRevistaFromFields() throws SQLException {
        if (!validarCampos()) {
            throw new SQLException("Entrada inválida");
        }

        return new RevistaBiblioteca(
                Integer.parseInt(editTextRevistaId.getText().toString()),
                editTextRevistaNome.getText().toString(),
                Integer.parseInt(editTextRevistaPaginas.getText().toString()),
                editTextRevistaISSN.getText().toString()
        );
    }

    private boolean validarCampos() {
        return !editTextRevistaId.getText().toString().isEmpty() &&
                !editTextRevistaNome.getText().toString().isEmpty() &&
                !editTextRevistaPaginas.getText().toString().isEmpty() &&
                !editTextRevistaISSN.getText().toString().isEmpty();
    }

    private void preencherCampos(RevistaBiblioteca revista) {
        editTextRevistaId.setText(String.valueOf(revista.getExemplarId()));
        editTextRevistaNome.setText(revista.getExemplarNome());
        editTextRevistaPaginas.setText(String.valueOf(revista.getExemplarPaginas()));
        editTextRevistaISSN.setText(revista.getRevistaISSN());
    }

    private void limparCampos() {
        editTextRevistaId.setText("");
        editTextRevistaNome.setText("");
        editTextRevistaPaginas.setText("");
        editTextRevistaISSN.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
