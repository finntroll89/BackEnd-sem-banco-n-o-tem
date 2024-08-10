/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PessoaJuridicaDAO {
    //private Connection conn;

    public PessoaJuridicaDAO(Connection conn) {
        //this.conn = conn;
    }

    public PessoaJuridicaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    private PessoaJuridica extrairPessoaJuridica(ResultSet rs) throws SQLException {
        return new PessoaJuridica(
                rs.getInt("id_pessoa"),
                rs.getString("nome"),
                rs.getString("endereco"),
                rs.getString("cidade"),
                rs.getString("estado"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("cnpj")
        );
    }

    public PessoaJuridica getPessoa(int id) throws SQLException {
        final String sql = "SELECT Pessoa.id_pessoa, Pessoa.nome, Pessoa.endereco, Pessoa.cidade, Pessoa.estado, Pessoa.telefone, Pessoa.email, PJ.cnpj\n"
                + "FROM Pessoa AS Pessoa JOIN pessoa_juridica AS PJ ON Pessoa.id_pessoa = PJ.id_pessoa WHERE Pessoa.id_pessoa = ?;";
        
        Connection conn = ConectorBD.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairPessoaJuridica(rs);
                }
            }
        }
        return null;
    }

    public List<PessoaJuridica> getPessoasJuridicas() throws SQLException {
        List<PessoaJuridica> list = new ArrayList<>();
        final String sql = "SELECT Pessoa.id_pessoa, Pessoa.nome, Pessoa.endereco, Pessoa.cidade, Pessoa.estado, Pessoa.telefone, Pessoa.email, PJ.cnpj\n"
                + "FROM Pessoa AS Pessoa JOIN pessoa_juridica AS PJ ON Pessoa.id_pessoa = PJ.id_pessoa;";
        
        Connection conn = ConectorBD.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(extrairPessoaJuridica(rs));
            }
        }
        return list;
    }

    public void incluir(PessoaJuridica pessoa) throws SQLException {
        final String sqlPessoa = "INSERT INTO Pessoa (nome, endereco, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        final String sqlPessoaJuridica = "INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES (?, ?)";
        
        Connection conn = ConectorBD.getConnection();

        try {
            conn.setAutoCommit(false);

            int pessoaId = 0;
            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getEndereco());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.executeUpdate();

                try (ResultSet generatedKeys = stmtPessoa.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pessoaId = generatedKeys.getInt(1);
                    }
                }
            }

            if (pessoaId == 0) {
                throw new SQLException("Falha ao inserir pessoa, nenhum ID foi gerado.");
            }

            try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                stmtPessoaJuridica.setInt(1, pessoaId);
                stmtPessoaJuridica.setString(2, pessoa.getCnpj());
                stmtPessoaJuridica.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void alterar(PessoaJuridica pessoa) throws SQLException {
        final String sqlPessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
        final String sqlPessoaJuridica = "UPDATE pessoa_juridica SET cnpj = ? WHERE id_pessoa = ?";
        
        
        Connection conn = ConectorBD.getConnection();
        

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                stmtPessoa.setString(1, pessoa.getNome());
                stmtPessoa.setString(2, pessoa.getEndereco());
                stmtPessoa.setString(3, pessoa.getCidade());
                stmtPessoa.setString(4, pessoa.getEstado());
                stmtPessoa.setString(5, pessoa.getTelefone());
                stmtPessoa.setString(6, pessoa.getEmail());
                stmtPessoa.setInt(7, pessoa.getId());
                stmtPessoa.executeUpdate();
            }
            try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                stmtPessoaJuridica.setString(1, pessoa.getCnpj());
                stmtPessoaJuridica.setInt(2, pessoa.getId());
                stmtPessoaJuridica.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM pessoa_juridica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";
        
        Connection conn = ConectorBD.getConnection();

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
                stmtPessoaJuridica.setInt(1, id);
                stmtPessoaJuridica.executeUpdate();
            }
            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                stmtPessoa.setInt(1, id);
                stmtPessoa.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
