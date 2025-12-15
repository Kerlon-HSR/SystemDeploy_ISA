/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Funcionario;

/**
 *
 * @author Kerlon
 */
public class FuncionarioDAO {
    private final Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Funcionario funcionario) throws SQLException {
        String sql = "insert into funcionario (nome, senha) values ('"+funcionario.getNome()+"', '"+funcionario.getSenha()+"'); ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        connection.close();   
    }

}
