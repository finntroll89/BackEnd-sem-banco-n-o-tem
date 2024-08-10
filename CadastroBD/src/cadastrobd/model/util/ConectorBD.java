/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectorBD {
    
   private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:sqlserver:// localhost\MSMANM-039\SQLEXPRESS:1433;databaseName=loja;encrypt=true;trustServerCertificate=true;";
                String user = "loja";
                String password = "admin1010";
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Banco de Dados Conectado com Sucesso");
            } catch (SQLException e) {
                System.out.println("Erro no Banco de Dados");
                throw new RuntimeException("Erro ao obter conexão com o banco de dados: " + e.getMessage(), e);
            }
        }
        return conn;
    }

    public static PreparedStatement getPrepared(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static ResultSet getSelect(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(sql);
    }

    public static void close(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar o statement: " + e.getMessage(), e);
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar o resultSet: " + e.getMessage(), e);
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
