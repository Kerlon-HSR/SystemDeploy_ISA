/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql = "insert into funcionario (usuario, senha) values (?, ?); ";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Proteção contra SQL inject
        statement.setString(1, funcionario.getUsuario());
        statement.setString(2, funcionario.getSenha());
        statement.execute();
    }

    public boolean buscarPorUsuarioESenha(Funcionario funcionario) throws SQLException {
        String sql = "select * from funcionario where usuario = ? and senha = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Proteção contra SQL inject
        statement.setString(1, funcionario.getUsuario());
        statement.setString(2, funcionario.getSenha());
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        return resultSet.next();
    }
    
    public void update(Funcionario funcionario) throws SQLException {
        String sql = "update funcionario set usuario = ?, senha = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Proteção contra SQL inject
        statement.setString(1, funcionario.getUsuario());
        statement.setString(2, funcionario.getSenha());
        statement.setInt(3, funcionario.getId());
        statement.execute();
    }
        
    public void delete(Funcionario funcionario) throws SQLException {
        String sql = "delete from funcionario where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        // Proteção contra SQL inject
        statement.setInt(1, funcionario.getId());
        statement.execute();
    }
    
    public ArrayList<Funcionario> buscarPorTodos() throws SQLException {
        String sql = "select * from funcionario";
        PreparedStatement statement = connection.prepareStatement(sql); 
        
        return pesquisa(statement);
    }

    private ArrayList<Funcionario> pesquisa(PreparedStatement statement) throws SQLException {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String usuario = resultSet.getString("usuario");
            String senha = resultSet.getString("senha");
            
            Funcionario funcionarioComDadosDoBanco = new Funcionario(id, usuario, senha);
            funcionarios.add(funcionarioComDadosDoBanco);
        }
        
        return funcionarios;
    }

    public Funcionario BuscarPorId(Funcionario funcionario) throws SQLException {
        String sql = "select * from funcionario where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql); 
    
        statement.setInt(1, funcionario.getId());
        
        return pesquisa(statement).get(0);
    }
}
