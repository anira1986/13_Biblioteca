/**
 *@author:<ANA PAULA DE OLIVEIRA SILVA>
 *RA1110482123028
 *ANA PAULA DE OLIVEIRA SILVA
 */


package br.edu.fateczl.biblioteca.persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICRUDDAO<T> {
    void adicionar(T t) throws SQLException;

    void atualizar(T t) throws SQLException;

    void remover(T t) throws SQLException;

    T buscar(T t) throws SQLException;

    List<T> listar() throws SQLException;
}
