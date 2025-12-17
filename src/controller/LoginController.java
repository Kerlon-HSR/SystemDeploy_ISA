/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.DBconexao;
import DAO.FuncionarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Funcionario;
import view.CadastroView;
import view.LoginView;
import view.MenuView;

/**
 *
 * @author Kerlon
 */
public class LoginController {
    private final LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }
    
    public void autenticar() throws SQLException {
        
        // buscar funcionario da view
        String usuario = view.getJtUsuario().getText();
        String senha = view.getJtSenha().getText();
        
        Funcionario funcionarioAutenticar = new Funcionario(usuario, senha);
        
        // verificar se existe no banco de dados
        Connection conexao = new DBconexao().getConnection();
        FuncionarioDAO funcionariodao = new FuncionarioDAO(conexao);
        
        boolean existe = funcionariodao.existeNoBancoPorUsuarioESenha(funcionarioAutenticar);
        
        // se existir, direciona para o menu
        if (existe) {
            MenuView telaMenu = new MenuView();
            telaMenu.setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Funcionario não encontrado", "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void registrar() {
        CadastroView telaCadastro = new CadastroView();
        telaCadastro.setVisible(true);
    }
    
}
