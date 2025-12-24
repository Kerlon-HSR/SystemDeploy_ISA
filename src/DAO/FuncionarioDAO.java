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
    
    public Funcionario insert(Funcionario funcionario) throws SQLException {
        String sql = "insert into funcionario (usuario, senha) values (?, ?); ";

        try (PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            // Proteção contra SQL inject
            statement.setString(1, funcionario.getUsuario());
            statement.setString(2, funcionario.getSenha());
            statement.execute();
            
            // Recuperar o ID gerado pelo Banco de Dados
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
            
                if (resultSet.next()) {
                    // O primeiro campo do ResultSet de chaves geradas é o ID
                   int id = resultSet.getInt(1);
                    funcionario.setId(id);
                }
            }
        }
    
        return funcionario;
    }

    public boolean buscarPorUsuarioESenha(Funcionario funcionario) throws SQLException {
        String sql = "select * from funcionario where usuario = ? and senha = ?";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, funcionario.getUsuario());
            statement.setString(2, funcionario.getSenha());
        
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Retorna true se encontrar o usuário
            }
        }
    }
    
    public void update(Funcionario funcionario) throws SQLException {
        String sql = "update funcionario set usuario = ?, senha = ? where id = ?";

        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Proteção contra SQL inject
            statement.setString(1, funcionario.getUsuario());
            statement.setString(2, funcionario.getSenha());
            statement.setInt(3, funcionario.getId());
            statement.execute();
        }
    }
        
    public void delete(Funcionario funcionario) throws SQLException {
        String sql = "delete from funcionario where id = ?";

        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Proteção contra SQL inject
            statement.setInt(1, funcionario.getId());
            statement.execute();
        }
    }
    
    public ArrayList<Funcionario> buscarPorTodos() throws SQLException {
        String sql = "select * from funcionario";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            return pesquisa(statement);
    
        }
    }
        
    private ArrayList<Funcionario> pesquisa(PreparedStatement statement) throws SQLException {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String usuario = resultSet.getString("usuario");
                String senha = resultSet.getString("senha");
                
                Funcionario funcionarioComDadosDoBanco = new Funcionario(id, usuario, senha);
                funcionarios.add(funcionarioComDadosDoBanco);
            }
        }
        return funcionarios;
    }

    public Funcionario BuscarPorId(Funcionario funcionario) throws SQLException {
        String sql = "select * from funcionario where id = ?";
        ArrayList<Funcionario> resultado;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, funcionario.getId());
            resultado = pesquisa(statement);
        }
        
        // verifica se retornou algo
        if (!resultado.isEmpty()) {
            return resultado.get(0);
        }
    
        // Se não achou ninguém, retorna null
        return null;
    }
    
    public ArrayList<Funcionario> buscarPorUsuarios(String nomeFiltro) throws SQLException {
        String sql = "select * from funcionario where usuario LIKE ?";
        
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
        
            statement.setString(1, "%" + nomeFiltro + "%");
        
            return pesquisa(statement);
        }
    }
}
