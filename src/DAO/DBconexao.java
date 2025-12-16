/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Kerlon
 */
public class DBconexao {

    private static final Properties PROPS = new Properties();

    static {
        // Tente carregar o arquivo a partir da raiz do classpath (com a barra /)
        try (InputStream input = DBconexao.class.getResourceAsStream("/db.properties")) { 
            
            if (input == null) {
                System.err.println("ERRO CRÍTICO: Arquivo /db.properties não encontrado. Verifique a localização no Classpath.");
                // Se o arquivo não for encontrado, NADA MAIS DEVE ACONTECER no bloco estático.
                // O método getConnection() falhará no bloco catch, o que é o comportamento esperado.
                
            } else {
                // SOMENTE carrega se o arquivo for encontrado!
                PROPS.load(input);
            }
            
        } catch (Exception ex) {
            System.err.println("Erro ao carregar propriedades de conexão.");
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        // O restante do seu código está correto
        try {
            return DriverManager.getConnection(
                PROPS.getProperty("DB_URL"), 
                PROPS.getProperty("DB_USER"), 
                PROPS.getProperty("DB_PASSWORD")
            );
        } catch (SQLException e) {
            // ... (restante do seu bloco catch)
            e.printStackTrace();
            return null;
        }
    }
}