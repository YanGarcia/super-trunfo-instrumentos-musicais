package br.com.trabalho.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica de conexões com o banco de dados MySQL.
 * Ajuste URL, USUARIO e SENHA conforme seu ambiente.
 */
public class ConnectionFactory {

    // ============================================================
    // AJUSTE AQUI o usuário e senha do seu MySQL
    // ============================================================
    private static final String URL = "jdbc:mysql://localhost:3306/super_trunfo?useSSL=false&serverTimezone=America/Sao_Paulo&useUnicode=true&characterEncoding=UTF-8";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    /**
     * Retorna uma conexão ativa com o banco super_trunfo.
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado. Adicione o mysql-connector-j ao classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
