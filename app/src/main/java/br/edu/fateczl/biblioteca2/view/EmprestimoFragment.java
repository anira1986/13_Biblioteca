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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.edu.fateczl.biblioteca.R;
import br.edu.fateczl.biblioteca.controller.AlunoController;
import br.edu.fateczl.biblioteca.controller.EmprestimoController;
import br.edu.fateczl.biblioteca.controller.LivroController;
import br.edu.fateczl.biblioteca.controller.RevistaController;
import br.edu.fateczl.biblioteca.model.AlunoBiblioteca;
import br.edu.fateczl.biblioteca.model.EmprestimoBiblioteca;
import br.edu.fateczl.biblioteca.model.ExemplarBiblioteca;
import br.edu.fateczl.biblioteca.model.LivroBiblioteca;
import br.edu.fateczl.biblioteca.model.RevistaBiblioteca;
import br.edu.fateczl.biblioteca.persistence.AlunoDAO;
import br.edu.fateczl.biblioteca.persistence.EmprestimoDAO;
import br.edu.fateczl.biblioteca.persistence.LivroDAO;
import br.edu.fateczl.biblioteca.persistence.RevistaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoFragment extends Fragment {
    private View view;
    private Spinner spinnerAluno, spinnerExemplar;
    private EditText editTextDataRetirada, editTextDataDevolucao;
    private TextView textViewSaidaEmprestimo;
    private EmprestimoController emprestimoController;
    private AlunoController alunoController;
    private LivroController livroController;
    private RevistaController revistaController;
    private List<AlunoBiblioteca> alunos;
    private List<ExemplarBiblioteca> exemplares;

    public EmprestimoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emprestimo, container, false);

        initializeViews();
        setupControllers();
        loadSpinners();

        setButtonListeners();

        return view;
    }

    private void initializeViews() {
        spinnerAluno = view.findViewById(R.id.spinnerAluno);
        spinnerExemplar = view.findViewById(R.id.spinnerExemplar);
        editTextDataRetirada = view.findViewById(R.id.editTextDataRetirada);
        editTextDataDevolucao = view.findViewById(R.id.editTextDataDevolucao);
        textViewSaidaEmprestimo = view.findViewById(R.id.textViewSaidaEmprestimo);
        textViewSaidaEmprestimo.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setupControllers() {
        emprestimoController = new EmprestimoController(new EmprestimoDAO(this.getContext()));
        alunoController = new AlunoController(new AlunoDAO(this.getContext()));
        livroController = new LivroController(new LivroDAO(this.getContext()));
        revistaController = new RevistaController(new RevistaDAO(this.getContext()));
    }

    private void loadSpinners() {
        loadSpinnerAluno();
        loadSpinnerExemplar();
    }

    private void loadSpinnerAluno() {
        try {
            alunos = alunoController.listarRegistros();
            alunos.add(0, new AlunoBiblioteca(0, "Selecione o Aluno", null));

            ArrayAdapter<AlunoBiblioteca> arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, alunos);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAluno.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private void loadSpinnerExemplar() {
        try {
            exemplares = new ArrayList<>();
            exemplares.addAll(livroController.listarRegistros());
            exemplares.addAll(revistaController.listarRegistros());

            exemplares.add(0, new RevistaBiblioteca(0, "Selecione o Exemplar", 0, null));

            ArrayAdapter<ExemplarBiblioteca> arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, exemplares);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerExemplar.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private void setButtonListeners() {
        view.findViewById(R.id.buttonBuscarEmprestimo).setOnClickListener(v -> buscarRegistro());
        view.findViewById(R.id.buttonRegistrarEmprestimo).setOnClickListener(v -> registrarRegistro());
        view.findViewById(R.id.buttonAtualizarEmprestimo).setOnClickListener(v -> atualizarRegistro());
        view.findViewById(R.id.buttonRemoverEmprestimo).setOnClickListener(v -> removerRegistro());
        view.findViewById(R.id.buttonListarEmprestimos).setOnClickListener(v -> listarRegistros());
    }

    private void buscarRegistro() {
        try {
            EmprestimoBiblioteca emprestimo = emprestimoController.buscarRegistro(createEmprestimoFromFields());

            if (emprestimo.getAluno().getNome() != null) {
                preencherCampos(emprestimo);
            } else {
                showToast("Empréstimo não encontrado");
                limparCampos();
            }
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private void registrarRegistro() {
        try {
            emprestimoController.registrarRegistro(createEmprestimoFromFields());
            showToast("Empréstimo registrado com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void atualizarRegistro() {
        try {
            emprestimoController.atualizarRegistro(createEmprestimoFromFields());
            showToast("Empréstimo atualizado com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void removerRegistro() {
        try {
            emprestimoController.removerRegistro(createEmprestimoFromFields());
            showToast("Empréstimo removido com sucesso");
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
        limparCampos();
    }

    private void listarRegistros() {
        try {
            List<EmprestimoBiblioteca> emprestimos = emprestimoController.listarRegistros();

            StringBuilder stringBuffer = new StringBuilder();
            for (EmprestimoBiblioteca emprestimo : emprestimos) {
                stringBuffer.append(emprestimo.toString()).append("\n");
            }

            textViewSaidaEmprestimo.setText(stringBuffer.toString());
        } catch (SQLException e) {
            showToast(e.getMessage());
        }
    }

    private EmprestimoBiblioteca createEmprestimoFromFields() throws SQLException {
        if (!validarCampos()) {
            throw new SQLException("Entrada inválida");
        }

        return new EmprestimoBiblioteca(
                (AlunoBiblioteca) spinnerAluno.getSelectedItem(),
                obterExemplar(),
                LocalDate.parse(editTextDataRetirada.getText().toString()),
                LocalDate.parse(editTextDataDevolucao.getText().toString())
        );
    }

    private boolean validarCampos() {
        return editTextDataRetirada.length() > 0 && editTextDataDevolucao.length() > 0 &&
                spinnerAluno.getSelectedItemPosition() != 0 && spinnerExemplar.getSelectedItemPosition() != 0;
    }

    private ExemplarBiblioteca obterExemplar() {
        if (spinnerExemplar.getSelectedItem() instanceof LivroBiblioteca) {
            return (LivroBiblioteca) spinnerExemplar.getSelectedItem();
        } else {
            return (RevistaBiblioteca) spinnerExemplar.getSelectedItem();
        }
    }

    private void preencherCampos(EmprestimoBiblioteca emprestimo) {
        setSpinnerSelection(spinnerAluno, emprestimo.getAluno().getId(), alunos);
        setSpinnerSelection(spinnerExemplar, emprestimo.getExemplar().getId(), exemplares);

        editTextDataRetirada.setText(String.valueOf(emprestimo.getDataRetirada()));
        editTextDataDevolucao.setText(String.valueOf(emprestimo.getDataDevolucao()));
    }

    private void setSpinnerSelection(Spinner spinner, int id, List<?> list) {
        int i = 0;
        for (Object item : list) {
            if ((item instanceof AlunoBiblioteca && ((AlunoBiblioteca) item).getId() == id) ||
                    (item instanceof ExemplarBiblioteca && ((ExemplarBiblioteca) item).getId() == id)) {
                spinner.setSelection(i + 1);
                return;
            }
            i++;
        }
        spinner.setSelection(0);
    }

    private void limparCampos() {
        spinnerAluno.setSelection(0);
        spinnerExemplar.setSelection(0);
        editTextDataRetirada.setText("");
        editTextDataDevolucao.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}



