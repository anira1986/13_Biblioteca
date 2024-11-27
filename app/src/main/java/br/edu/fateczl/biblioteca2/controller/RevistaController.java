/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */

import br.edu.fateczl.biblioteca.persistence.RevistaDAO;

import java.sql.SQLException;
import java.util.List;

public class RevistaController implements IController<RevistaBiblioteca> {
    private final RevistaDAO dao;

    public RevistaController(RevistaDAO dao) {
        this.dao = dao;
    }

    @Override
    public void adicionar(RevistaBiblioteca revista) throws SQLException {
        try {
            dao.abrir();
            dao.adicionar(revista);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void atualizar(RevistaBiblioteca revista) throws SQLException {
        try {
            dao.abrir();
            dao.atualizar(revista);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public void remover(RevistaBiblioteca revista) throws SQLException {
        try {
            dao.abrir();
            dao.remover(revista);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public RevistaBiblioteca buscar(RevistaBiblioteca revista) throws SQLException {
        try {
            dao.abrir();
            return dao.buscar(revista);
        } finally {
            dao.fechar();
        }
    }

    @Override
    public List<RevistaBiblioteca> listar() throws SQLException {
        try {
            dao.abrir();
            return dao.listar();
        } finally {
            dao.fechar();
        }
    }
}
