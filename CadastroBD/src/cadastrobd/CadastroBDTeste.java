/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd;
import java.sql.SQLException;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import java.sql.Connection;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.Level;


public class CadastroBDTeste {
   
    private static final Logger LOGGER = Logger.getLogger(CadastroBDTeste.class.getName());
     
     
    private Connection conexao;
    //private Connection conn;
    
    
    private final PessoaFisicaDAO pfDao;
    private final PessoaJuridicaDAO pjDao;
    
    public CadastroBDTeste() {
        pfDao = new PessoaFisicaDAO(conexao);
        pjDao = new PessoaJuridicaDAO(conexao);
    }
    
    private void run() {
        PessoaFisica pf = new PessoaFisica(1, "Luiz Silva", "Rua Anibal", "Belem",
            "PA", "0000-0000", "Luiz@bol.com", "91988474848");

        if (pf.getNome() == null || pf.getNome().trim().isEmpty()) {
            System.out.println("o campo nome não pode ser vazio!");
            return; 
        }
        
        try {
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica incluida com ID: " );
            pfDao.inserirPessoaFisica(pf);
            System.out.println("---------------------------------");
            pf.exibir();
            pf.setNome("Gabriel");
            pf.setCidade("Ceara");
            pf.setEstado("CE");
            pfDao.alterar(pf);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica alterada com Sucesso!.");
            pf.exibir();
            List<PessoaFisica> listaPf = pfDao.getPessoas();
            System.out.println("---------------------------------");
            System.out.println("Exibir todas as pessoas fisicas:");
            for (PessoaFisica pessoa : listaPf) {
                System.out.println("---------------------------------");
                pessoa.exibir();
            }
            System.out.println("---------------------------------");
            pfDao.excluir(pf.getId());
            System.out.println("---------------------------------");
            System.out.println("Pessoa Fisica excluida.");
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        PessoaJuridica pj = new PessoaJuridica(2, "Loja Vermelha","Rua Vermelha", "Sao Paulo",
            "SP", "2222-3333", "lojavermelha@bol.com", "44012616000158");

        if (pj.getNome() == null || pj.getNome().trim().isEmpty()) {
            System.out.println("o campo nome não pode ser vazio!");
            return; 
        }
        
        try {
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica incluida com ID: ");
            pjDao.incluir(pj);
            System.out.println("---------------------------------");
            pj.exibir();
            pj.setNome("Super Update");
            pj.setCidade("Sao Paulo");
            pj.setEstado("SP");
            pjDao.alterar(pj);
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica alterada com Sucesso!.");
            pj.exibir();
            List<PessoaJuridica> listaPj = pjDao.getPessoasJuridicas();
            System.out.println("---------------------------------");
            System.out.println("Exibir todas as pessoas juridicas:");
            for (PessoaJuridica pessoa : listaPj) {
                System.out.println("---------------------------------");
                pessoa.exibir();
            }
            System.out.println("---------------------------------");
            pjDao.excluir(pj.getId());
            System.out.println("---------------------------------");
            System.out.println("Pessoa Juridica excluida.");
        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        
    }
    
    public static void main(String[] args) {
        new CadastroBDTeste().run();
    }    
      
}
