package br.edu.fateczl.biblioteca.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import br.edu.fateczl.biblioteca.controller.AlunoController;
import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;
import br.edu.fateczl.biblioteca.persistence.AlunoDAO;

import java.sql.SQLException;

public class AlunoFragment extends Fragment {

    private EditText editTextAlunoNome;
    private EditText editTextAlunoEmail;
    private TextView textViewAlunoSaida;
    private AlunoController alunoController;

    public AlunoFragment() {
    
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);

       
        editTextAlunoNome = view.findViewById(R.id.editTextAlunoNome);
        editTextAlunoEmail = view.findViewById(R.id.editTextAlunoEmail);
        textViewAlunoSaida = view.findViewById(R.id.textViewAlunoSaida);

        alunoController = new AlunoController(new AlunoDAO(getContext()));

       
        view.findViewById(R.id.buttonRegistrarAluno).setOnClickListener(v -> registrarAluno());
        view.findViewById(R.id.buttonBuscarAluno).setOnClickListener(v -> buscarAluno());

        return view;
    }

    private void registrarAluno() {
        String nome = editTextAlunoNome.getText().toString();
        String email = editTextAlunoEmail.getText().toString();

        AlunoBiblioteca aluno = new AlunoBiblioteca(0, nome, email);
        try {
            alunoController.registrar(aluno);
            textViewAlunoSaida.setText("Aluno registrado com sucesso!");
        } catch (SQLException e) {
            textViewAlunoSaida.setText("Erro ao registrar aluno.");
        }
    }

    private void buscarAluno() {
        String nome = editTextAlunoNome.getText().toString();

        try {
            AlunoBiblioteca aluno = alunoController.buscar(new AlunoBiblioteca(0, nome, null));
            textViewAlunoSaida.setText(aluno.toString());
        } catch (SQLException e) {
            textViewAlunoSaida.setText("Erro ao buscar aluno.");
        }
    }
}
