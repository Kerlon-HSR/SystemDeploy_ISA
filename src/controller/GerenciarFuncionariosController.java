/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.DBconexao;
import DAO.FuncionarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Funcionario;
import view.GerenciarFuncionariosView;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author kerlon
 */
public class GerenciarFuncionariosController {
    private final GerenciarFuncionariosView view;

    public GerenciarFuncionariosController(GerenciarFuncionariosView view) {
        this.view = view;
    }

    private FuncionarioDAO fazerConexao() {
        Connection conexao = new DBconexao().getConnection();
        return new FuncionarioDAO(conexao);
    }
    
    private void preencherTabela(ArrayList<Funcionario> listaFuncionarios) {
        DefaultTableModel modelo = (DefaultTableModel) view.getJtfuncionarios().getModel();
        modelo.setNumRows(0);
        
        for (Funcionario f : listaFuncionarios) {
            modelo.addRow(new Object[]{
                f.getUsuario(),
                f.getSenha(),
                f.getId(),
            });
        }
    }
    
    public void carregarTabelaFuncionarios() throws SQLException {
        FuncionarioDAO dao = fazerConexao();
        ArrayList<Funcionario> listaFuncionarios = dao.buscarPorTodos();
        
        preencherTabela(listaFuncionarios);
    }    
    
    private void atualizarTabelaFuncionarios(FuncionarioDAO dao) throws SQLException {
        ArrayList<Funcionario> listaFuncionarios = dao.buscarPorTodos();
        preencherTabela(listaFuncionarios);
    }

    private Funcionario extrairDadosTabelaFuncionarios() {
        int linhaSelecionada = view.getJtfuncionarios().getSelectedRow();
    
        if (linhaSelecionada != -1) { // -1 significa que nada foi selecionado
            Object usuarioObj = view.getJtfuncionarios().getValueAt(linhaSelecionada, 0);
            Object senhaObj = view.getJtfuncionarios().getValueAt(linhaSelecionada, 1);
            Object idObj = view.getJtfuncionarios().getValueAt(linhaSelecionada, 2);
            
            String usuario = usuarioObj.toString();
            String senha = senhaObj.toString();
            int id = Integer.parseInt(idObj.toString());
        
            return new Funcionario(id, usuario, senha);
        }
        
        return null;
    }
    
    public void preencherFormularioFuncionario() {
        Funcionario selecionado = extrairDadosTabelaFuncionarios();
        
        if (selecionado != null) {
            view.getJtUsuario().setText(selecionado.getUsuario());
            view.getJtSenha().setText(selecionado.getSenha());
            view.getJtId().setText(String.valueOf(selecionado.getId()));
        }
    }
    
    public void registrarFuncionario(JTextField jtUsuario, JTextField jtSenha) throws SQLException {
        FuncionarioDAO dao = fazerConexao();
        
        String usuario = jtUsuario.getText();
        String senha = jtSenha.getText();
        Funcionario escolhido = new Funcionario(usuario, senha);
        
        dao.insert(escolhido);
        atualizarTabelaFuncionarios(dao);
        JOptionPane.showMessageDialog(view, "Funcionário registrado com sucesso!");
    }
    
    
    public void removerFuncionario(JTextField jtId, JTextField jtUsuario, JTextField jtSenha) throws SQLException {
        FuncionarioDAO dao = fazerConexao();
        
        String id_str = jtId.getText();
        if(id_str.isEmpty()) {
            JOptionPane.showMessageDialog(view,"Selecione um funcionário da lista", "Erro na ação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = Integer.parseInt(id_str);
                
        String usuario = jtUsuario.getText();
        String senha = jtSenha.getText();
        
        Funcionario escolhido = new Funcionario(id, usuario, senha);
        Funcionario achado = dao.BuscarPorId(escolhido);
        
        if (achado == null) {
                JOptionPane.showMessageDialog(view,"Erro na hora de procurar o funcionario no DB", "Erro na ação", JOptionPane.ERROR_MESSAGE);
            }
            // Teste Delete
            dao.delete(achado);
        atualizarTabelaFuncionarios(dao);
        JOptionPane.showMessageDialog(view, "Funcionário removido com sucesso!");
    }
    
    public void procurarFuncionarios(JTextField jtUsuario) throws SQLException {
        FuncionarioDAO dao = fazerConexao();
        
        String usuario = jtUsuario.getText();
        ArrayList<Funcionario> listaFuncionarios = dao.buscarPorUsuarios(usuario);
        preencherTabela(listaFuncionarios);
    }
    
    public void atualizarFuncionario(JTextField jtId, JTextField jtUsuario, JTextField jtSenha) throws SQLException {
        FuncionarioDAO dao = fazerConexao();
    
        int id = Integer.parseInt(jtId.getText());
        String usuario = jtUsuario.getText();
        String senha = jtSenha.getText();
        Funcionario atualizado = new Funcionario(id, usuario, senha);
    
        dao.update(atualizado);
        atualizarTabelaFuncionarios(dao);
        JOptionPane.showMessageDialog(view, "Funcionário atualiazado com sucesso!");
    }

}
