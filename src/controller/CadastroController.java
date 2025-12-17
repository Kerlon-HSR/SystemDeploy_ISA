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

/**
 *
 * @author Kerlon
 */
public class CadastroController {
    private final CadastroView view;

    public CadastroController(CadastroView view) {
        this.view = view;
    }
    
    public void salvarUsuario() {
        
        String usuario = view.getJtUsuario().getText();
        String senha = view.getJtSenha().getText();
        String senhaRep = view.getJtSenhaRepetida().getText();
        
        // verificar se a senha é a mesma
        if (!senha.equals(senhaRep)) {
            JOptionPane.showMessageDialog(view, "Senhas diferentes", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Funcionario funcionarioCadastrar = new Funcionario(usuario, senha);
        
        
        try {
            Connection conexao = new DBconexao().getConnection();
            FuncionarioDAO funcionariodao = new FuncionarioDAO(conexao);
            funcionariodao.insert(funcionarioCadastrar);
            
        } catch (SQLException ex) {
            System.getLogger(CadastroView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        JOptionPane.showMessageDialog(view, "Cadastro realizado com sucesso");
        view.dispose();
        
    }

}
