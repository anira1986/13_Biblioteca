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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Spinner spinnerAluno;
    private Spinner spinnerExemplar;
    private EditText editTextDataRetirada;
    private EditText editTextDataDevolucao;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emprestimo, container, false);

        spinnerAluno = view.findViewById(R.id.spinnerAluno);
        spinnerExemplar = view.findViewById(R.id.spinnerExemplar);
        editTextDataRetirada = view.findViewById(R.id.editTextDataRetirada);
        editTextDataDevolucao = view.findViewById(R.id.editTextDataDevolucao);
        textViewSaidaEmprestimo = view.findViewById(R.id.textViewSaidaEmprestimo);
        textViewSaidaEmprestimo.setMovementMethod(new ScrollingMovementMethod());

        emprestimoController = new EmprestimoController(new EmprestimoDAO(this.getContext()));
        alunoController = new AlunoController(new AlunoDAO(this.getContext()));
        livroController = new LivroController(new LivroDAO(this.getContext()));
        revistaController = new RevistaController(new RevistaDAO(this.getContext()));

        carregarSpinnerAluno();
        carregarSpinnerExemplar();

        view.findViewById(R.id.buttonBuscarEmprestimo).setOnClickListener(buscar -> buscarRegistro());
        view.findViewById(R.id.buttonRegistrarEmprestimo).setOnClickListener(registrar -> registrarRegistro());
        view.findViewById(R.id.buttonAtualizarEmprestimo).setOnClickListener(atualizar -> atualizarRegistro());
        view.findViewById(R.id.buttonRemoverEmprestimo).setOnClickListener(remover -> removerRegistro());
        view.findViewById(R.id.buttonListarEmprestimos).setOnClickListener(listar -> listarRegistros());

        return view;
    }

    private void carregarSpinnerAluno() {
        AlunoBiblioteca alunoInicial = new AlunoBiblioteca(0, "Selecione o Aluno", null);
        List<AlunoBiblioteca> alunosOpcoes = new ArrayList<>();

        try {
            alunos = alunoController.listarRegistros();
            alunosOpcoes.add(0, alunoInicial);
            alunosOpcoes.addAll(alunos);

            ArrayAdapter<AlunoBiblioteca> arrayAdapter = new ArrayAdapter<>(
                    view.getContext(), android.R.layout.simple_spinner_item, alunosOpcoes);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAluno.setAdapter(arrayAdapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void carregarSpinnerExemplar() {
        RevistaBiblioteca revistaInicial = new RevistaBiblioteca(0, "Selecione o Exemplar", 0, null);
        List<ExemplarBiblioteca> exemplaresOpcoes = new ArrayList<>();

        try {
            exemplares = new ArrayList<>();
            exemplares.addAll(livroController.listarRegistros());
            exemplares.addAll(revistaController.listarRegistros());

            exemplaresOpcoes.add(0, revistaInicial);
            exemplaresOpcoes.addAll(exemplares);

            ArrayAdapter<ExemplarBiblioteca> arrayAdapter = new ArrayAdapter<>(
                    view.getContext(), android.R.layout.simple_spinner_item, exemplaresOpcoes);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerExemplar.setAdapter(arrayAdapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscarRegistro() {
        EmprestimoBiblioteca emprestimo;

        try {
            emprestimo = emprestimoController.buscarRegistro(new EmprestimoBiblioteca(
                    (AlunoBiblioteca) spinnerAluno.getSelectedItem(),
                    obterExemplar(),
                    LocalDate.parse(editTextDataRetirada.getText().toString())));

            if (emprestimo.getAluno().getNome() != null) {
                preencherCampos(emprestimo);

            } else {
                Toast.makeText(
                        view.getContext(), "Empréstimo não encontrado", Toast.LENGTH_LONG).show();

                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void registrarRegistro() {
        try {
            emprestimoController.registrarRegistro(escreverEmprestimo());

            Toast.makeText(
                    view.getContext(), "Empréstimo registrado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void atualizarRegistro() {
        try {
            emprestimoController.atualizarRegistro(escreverEmprestimo());

            Toast.makeText(
                    view.getContext(), "Empréstimo atualizado com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void removerRegistro() {
        try {
            emprestimoController.removerRegistro(new EmprestimoBiblioteca(
                    (AlunoBiblioteca) spinnerAluno.getSelectedItem(),
                    obterExemplar(),
                    LocalDate.parse(editTextDataRetirada.getText().toString()))
            );

            Toast.makeText(
                    view.getContext(), "Empréstimo removido com sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private EmprestimoBiblioteca escreverEmprestimo() throws SQLException {
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
        if (editTextDataRetirada.length() == 0) {
            return false;
        }

        if (editTextDataDevolucao.length() == 0) {
            return false;
        }

        if (spinnerAluno.getSelectedItemPosition() == 0) {
            return false;
        }

        if (spinnerExemplar.getSelectedItemPosition() == 0) {
            return false;
        }

        return true;
    }

    private ExemplarBiblioteca obterExemplar() {
        if (spinnerExemplar.getSelectedItem() instanceof LivroBiblioteca) {
            return (LivroBiblioteca) spinnerExemplar.getSelectedItem();
        } else {
            return (RevistaBiblioteca) spinnerExemplar.getSelectedItem();
        }
    }

    private void preencherCampos(EmprestimoBiblioteca emprestimo) {
        definirSpinnerAlunoId(emprestimo);
        definirSpinnerExemplarId(emprestimo);
        editTextDataRetirada.setText(String.valueOf(emprestimo.getDataRetirada()));
        editTextDataDevolucao.setText(String.valueOf(emprestimo.getDataDevolucao()));
    }

    private void definirSpinnerAlunoId(EmprestimoBiblioteca emprestimo) {
        int i = 1;
        for (AlunoBiblioteca aluno : alunos) {
            if (aluno.getId() == emprestimo.getAluno().getId()) {
                spinnerAluno.setSelection(i);
            } else {
                i++;
            }
        }
        if (i > alunos.size()) {
            spinnerAluno.setSelection(0);
        }
    }

    private void definirSpinnerExemplarId(EmprestimoBiblioteca emprestimo) {
        int i = 1;
        for (ExemplarBiblioteca exemplar : exemplares) {
            if (exemplar.getId() == emprestimo.getExemplar().getId()) {
                spinnerExemplar.setSelection(i);
            } else {
                i++;
            }
        }
        if (i > exemplares.size()) {
            spinnerExemplar.setSelection(0);
        }
    }

    private void limparCampos() {
        spinnerAluno.setSelection(0);
        spinnerExemplar.setSelection(0);
        editTextDataRetirada.setText("");
        editTextDataDevolucao.setText("");
    }

}
