package debug;

import DAO.DBconexao;
import DAO.FuncionarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;
import view.MenuView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kerlon
 */
public class Debugger {
    private final MenuView view;

    public Debugger(MenuView view) {
        this.view = view;
    }
    
    public void testarFuncionarioDAO() {
        try {
            Connection conexao = new DBconexao().getConnection();
            FuncionarioDAO funcionarioDao = new FuncionarioDAO(conexao);
            
            // Teste insert
            Funcionario funcionarioInsert = new Funcionario("testeusuarioinsert", "654321");
            Funcionario funcionarioInserido = funcionarioDao.insert(funcionarioInsert);
            
            // Teste Select Id
            Funcionario funcionarioSelecionado = funcionarioDao.BuscarPorId(funcionarioInserido);
            if (funcionarioSelecionado == null) {
                System.err.println("Erro na hora de procurar o funcionario no DB");
            }
            // Teste Delete
            funcionarioDao.delete(funcionarioSelecionado);
            
            // Teste Select All
            ArrayList<Funcionario> funcionarios = funcionarioDao.buscarPorTodos();
            
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario.getUsuario());
            }
            
        } catch (SQLException ex) {
            System.getLogger(MenuView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
}
