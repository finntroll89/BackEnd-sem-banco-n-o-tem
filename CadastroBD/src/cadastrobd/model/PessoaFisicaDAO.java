/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    
    //private Connection conexao;
    //private Connection conn;

    public PessoaFisicaDAO(Connection conexao) {
        //this.conn = conexao;
    }

    public PessoaFisicaDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void inserirPessoaFisica(PessoaFisica pf) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (nome, endereco, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES (?, ?)";
        
        Connection conn = ConectorBD.getConnection();
        
        try {
            conn.setAutoCommit(false);
            int pessoaId = 0;

            // Inserir na tabela Pessoa
            try (PreparedStatement stPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                stPessoa.setString(1, pf.getNome());
                stPessoa.setString(2, pf.getEndereco());
                stPessoa.setString(3, pf.getCidade());
                stPessoa.setString(4, pf.getEstado());
                stPessoa.setString(5, pf.getTelefone());
                stPessoa.setString(6, pf.getEmail());
                stPessoa.executeUpdate();

                // Obter o ID gerado
                try (ResultSet rs = stPessoa.getGeneratedKeys()) {
                    if (rs.next()) {
                        pessoaId = rs.getInt(1);
                    }
                }
            }

            // Inserir na tabela PessoaFisica
            try (PreparedStatement stPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                stPessoaFisica.setInt(1, pessoaId);
                stPessoaFisica.setString(2, pf.getCpf());
                stPessoaFisica.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void alterar(PessoaFisica pf) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
        String sqlPessoaFisica = "UPDATE pessoa_fisica SET cpf = ? WHERE id_pessoa = ?";
        
        Connection conn = ConectorBD.getConnection();
        
        try {
            conn.setAutoCommit(false);

            // Atualizar na tabela Pessoa
            try (PreparedStatement stPessoa = conn.prepareStatement(sqlPessoa)) {
                stPessoa.setString(1, pf.getNome());
                stPessoa.setString(2, pf.getEndereco());
                stPessoa.setString(3, pf.getCidade());
                stPessoa.setString(4, pf.getEstado());
                stPessoa.setString(5, pf.getTelefone());
                stPessoa.setString(6, pf.getEmail());
                stPessoa.setInt(7, pf.getId());
                stPessoa.executeUpdate();
            }

            // Atualizar na tabela PessoaFisica
            try (PreparedStatement stPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                stPessoaFisica.setString(1, pf.getCpf());
                stPessoaFisica.setInt(2, pf.getId());
                stPessoaFisica.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM pessoa_fisica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";
        
        Connection conn = ConectorBD.getConnection();

        try {
            conn.setAutoCommit(false);

            // Excluir da tabela PessoaFisica
            try (PreparedStatement stPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                stPessoaFisica.setInt(1, id);
                stPessoaFisica.executeUpdate();
            }

            // Excluir da tabela Pessoa
            try (PreparedStatement stPessoa = conn.prepareStatement(sqlPessoa)) {
                stPessoa.setInt(1, id);
                stPessoa.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public PessoaFisica getPessoa(Integer id) throws SQLException {
        String sql = "SELECT Pessoa.id_pessoa, Pessoa.nome, Pessoa.endereco, Pessoa.cidade, Pessoa.estado, Pessoa.telefone, Pessoa.email, PF.cpf "
                   + "FROM Pessoa JOIN pessoa_fisica PF ON Pessoa.id_pessoa = PF.id_pessoa WHERE Pessoa.id_pessoa = ?";
        
        Connection conn = ConectorBD.getConnection();
        
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                            rs.getInt("id_pessoa"),
                            rs.getString("nome"),
                            rs.getString("endereco"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("cpf")
                    );
                }
            }
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        List<PessoaFisica> list = new ArrayList<>();
        String sql = "SELECT Pessoa.id_pessoa, Pessoa.nome, Pessoa.endereco, Pessoa.cidade, Pessoa.estado, Pessoa.telefone, Pessoa.email, PF.cpf "
                   + "FROM Pessoa JOIN pessoa_fisica PF ON Pessoa.id_pessoa = PF.id_pessoa";
        
        Connection conn = ConectorBD.getConnection();
        
        try (PreparedStatement st = conn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(new PessoaFisica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ));
            }
        }
        return list;
    }
}
