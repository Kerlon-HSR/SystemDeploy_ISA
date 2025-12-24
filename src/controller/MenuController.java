/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import view.GerenciarFuncionariosView;
import view.GuiaView;
import view.MenuView;

/**
 *
 * @author kerlon
 */
public class MenuController {
    private final MenuView view;

    public MenuController(MenuView view) {
        this.view = view;
    }
    
    public void mostrarGuia() {
    for (javax.swing.JInternalFrame frame : view.getDesktopPane().getAllFrames()) {
        
        // Verifica se é do tipo GuiaView
        if (frame instanceof GuiaView) {
            // Se encontrar, traz ela para a frente e foca nela
            frame.toFront();
            try {
                frame.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
                e.printStackTrace();
            }
            // Retorna imediatamente, impedindo a criação de uma nova janela
            return; 
        }
    }

    // Se o loop terminar sem encontrar a GuiaView, cria uma nova
    GuiaView telaGuia = new GuiaView();
    view.getDesktopPane().add(telaGuia);
    telaGuia.setVisible(true);
    }
    
    public void gerenciarFuncionarios() throws SQLException {
        GerenciarFuncionariosView telaGerenciarFuncionarios = new GerenciarFuncionariosView();
        view.getDesktopPane().add(telaGerenciarFuncionarios);
        telaGerenciarFuncionarios.setVisible(true);
    }
    
}
