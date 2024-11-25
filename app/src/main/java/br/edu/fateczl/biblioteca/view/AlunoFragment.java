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
import br.edu.fateczl.biblioteca.controller.AlunoController;
import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;
import br.edu.fateczl.biblioteca.persistence.AlunoDAO;

import java.sql.SQLException;
import java.util.List;

public class AlunoFragment extends Fragment {
    private View view;
    private EditText editTextAlunoId;
    private EditText editTextAlunoNome;
    private EditText editTextAlunoEmail;
    private TextView textViewAlunoSaida;
    private AlunoController alunoController;

    public AlunoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluno, container, false);

        editTextAlunoId = view.findViewById(R.id.editTextAlunoId);
        editTextAlunoNome = view.findViewById(R.id.editTextAlunoNome);
        editTextAlunoEmail = view.findViewById(R.id.editTextAlunoEmail);
        textViewAlunoSaida = view.findViewById(R.id.textViewAlunoSaida);
        textViewAlunoSaida.setMovementMethod(new ScrollingMovementMethod());

        alunoController = new AlunoController(new AlunoDAO(view.getContext()));

        view.findViewById(R.id.buttonBuscarAluno).setOnClickListener(buscar -> buscarAluno());
        view.findViewById(R.id.buttonRegistrarAluno).setOnClickListener(registrar -> registrarAluno());
        view.findViewById(R.id.buttonAtualizarAluno).setOnClickListener(atualizar -> atualizarAluno());
        view.findViewById(R.id.buttonRemoverAluno).setOnClickListener(remover -> removerAluno());
        view.findViewById(R.id.buttonListarAlunos).setOnClickListener(listar -> listarAlunos());

        return view;
    }

    private void buscarAluno() {
        AlunoBiblioteca aluno;

        try {
            aluno = alunoController.buscarRegistro(new AlunoBiblioteca(
                    Integer.parseInt(editTextAlunoId.getText().toString()),
                    null,
                    null)
            );

            if (aluno.getNome() != null) {
                preencherCampos(aluno);

            } else {
                Toast.makeText(
                        view.getContext(), "Aluno não encontrado", Toast.LENGTH_LONG).show();

                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrarAluno() {
        try {
            alunoController.registrarRegistro(escreverAluno());

            Toast.makeText(
                    view.getContext(), "Aluno registrado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void atualizarAluno() {
        try {
            alunoController.atualizarRegistro(escreverAluno());

            Toast.makeText(
                    view.getContext(), "Aluno atualizado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void removerAluno() {
        try {
            alunoController.removerRegistro(new AlunoBiblioteca(
                    Integer.parseInt(editTextAlunoId.getText().toString()), null, null));

            Toast.makeText(
                    view.getContext(), "Aluno removido com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void listarAlunos() {
        try {
            List<AlunoBiblioteca> alunos = alunoController.listarRegistros();

            StringBuilder stringBuffer = new StringBuilder();

            for (AlunoBiblioteca aluno : alunos) {
                stringBuffer.append(aluno.toString()).append("\n");
            }

            textViewAlunoSaida.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private AlunoBiblioteca escreverAluno() throws SQLException {
        if (!validarCampos()) {
            throw new SQLException("Entrada inválida");
        }

        return new AlunoBiblioteca(
                Integer.parseInt(editTextAlunoId.getText().toString()),
                editTextAlunoNome.getText().toString(),
                editTextAlunoEmail.getText().toString()
        );
    }

    private boolean validarCampos() {
        if (editTextAlunoId.length() == 0) {
            return false;
        }

        if (editTextAlunoNome.length() == 0) {
            return false;
        }

        if (editTextAlunoEmail.length() == 0) {
            return false;
        }

        return true;
    }

    private void preencherCampos(AlunoBiblioteca aluno) {
        editTextAlunoId.setText(String.valueOf(aluno.getId()));
        editTextAlunoNome.setText(aluno.getNome());
        editTextAlunoEmail.setText(aluno.getEmail());
    }

    private void limparCampos() {
        editTextAlunoId.setText("");
        editTextAlunoNome.setText("");
        editTextAlunoEmail.setText("");
    }
}
