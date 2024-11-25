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
import br.edu.fateczl.biblioteca.controller.RevistaController;
import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;
import br.edu.fateczl.biblioteca.persistence.RevistaDAO;

import java.sql.SQLException;
import java.util.List;

public class RevistaFragment extends Fragment {
    private View view;
    private EditText editTextRevistaId;
    private EditText editTextRevistaNome;
    private EditText editTextRevistaPaginas;
    private EditText editTextRevistaISSN;
    private TextView textViewSaidaRevista;
    private RevistaController revistaController;

    public RevistaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_revista, container, false);

        editTextRevistaId = view.findViewById(R.id.editTextRevistaId);
        editTextRevistaNome = view.findViewById(R.id.editTextRevistaNome);
        editTextRevistaPaginas = view.findViewById(R.id.editTextRevistaPaginas);
        editTextRevistaISSN = view.findViewById(R.id.editTextRevistaISSN);
        textViewSaidaRevista = view.findViewById(R.id.textViewSaidaRevista);
        textViewSaidaRevista.setMovementMethod(new ScrollingMovementMethod());

        revistaController = new RevistaController(new RevistaDAO(this.getContext()));

        view.findViewById(R.id.buttonRevistaBuscar).setOnClickListener(buscar -> buscarRegistro());
        view.findViewById(R.id.buttonRevistaRegistrar).setOnClickListener(registrar -> registrarRegistro());
        view.findViewById(R.id.buttonRevistaAtualizar).setOnClickListener(atualizar -> atualizarRegistro());
        view.findViewById(R.id.buttonRevistaRemover).setOnClickListener(remover -> removerRegistro());
        view.findViewById(R.id.buttonRevistaListar).setOnClickListener(listar -> listarRegistros());

        return view;
    }

    private void buscarRegistro() {
        RevistaBiblioteca revista;

        try {
            revista = revistaController.buscarRegistro(new RevistaBiblioteca(
                    Integer.parseInt(editTextRevistaId.getText().toString()),
                    null,
                    0,
                    null)
            );

            if (revista.getExemplarNome() != null) {
                preencherCampos(revista);

            } else {
                Toast.makeText(
                        view.getContext(), "Revista não encontrada", Toast.LENGTH_LONG).show();

                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrarRegistro() {
        try {
            revistaController.registrarRegistro(criarRevista());

            Toast.makeText(
                    view.getContext(), "Revista registrada com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void atualizarRegistro() {
        try {
            revistaController.atualizarRegistro(criarRevista());

            Toast.makeText(
                    view.getContext(), "Revista atualizada com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void removerRegistro() {
        try {
            revistaController.removerRegistro(
                    new RevistaBiblioteca(
                            Integer.parseInt(editTextRevistaId.getText().toString()), null, 0, null));

            Toast.makeText(
                    view.getContext(), "Revista removida com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private RevistaBiblioteca criarRevista() throws SQLException {
        if (!camposValidos()) {
            throw new SQLException("Entrada inválida");
        }

        return new RevistaBiblioteca(
                Integer.parseInt(editTextRevistaId.getText().toString()),
                editTextRevistaNome.getText().toString(),
                Integer.parseInt(editTextRevistaPaginas.getText().toString()),
                editTextRevistaISSN.getText().toString()
        );
    }

    private boolean camposValidos() {
        return editTextRevistaId.length() > 0 &&
                editTextRevistaNome.length() > 0 &&
                editTextRevistaPaginas.length() > 0 &&
                editTextRevistaISSN.length() > 0;
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
}
