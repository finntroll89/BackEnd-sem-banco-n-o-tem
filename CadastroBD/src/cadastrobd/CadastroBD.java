/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.util.ConectorBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class CadastroBD {

    
    public static void main(String[] args) {
        
       
        Scanner scanner = new Scanner(System.in);
        Connection conn = ConectorBD.getConnection();
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(conn);
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(conn);
        
        int escolha;

        do {
            System.out.println("#############################################");
            System.out.println("1 - Incluir pessoa");
            System.out.println("2 - Alterar pessoa");
            System.out.println("3 - Excluir pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Listar todos");
            System.out.println("6 - Lista de pessoas fisicas");
            System.out.println("7 - Lista de pessoas juridicas");
            System.out.println("0 - Sair");
            System.out.println("############################################");
            System.out.println("Escolha uma opcao: ");
            escolha = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (escolha) {
                    case 1:
                        System.out.println("(F) - Pessoa Fisica | (J) - Pessoa juridica");
                        System.out.println("Escolha uma opcao: ");
                        char tipoInclusao = scanner.next().charAt(0);
                        scanner.nextLine();

                        if (tipoInclusao == 'F' || tipoInclusao == 'f') {
                            cadastrarPessoaFisica(pessoaFisicaDAO, scanner);
                        } else if (tipoInclusao == 'J' || tipoInclusao == 'j') {
                            cadastrarPessoaJuridica(pessoaJuridicaDAO, scanner);
                        } else {
                            System.out.println("## > A opcao escolhida invalida.");
                        }
                        break;

                    case 2:
                        alterarPessoa(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 3:
                        excluirPessoa(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 4:
                        buscarPessoaPeloId(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 5:
                        exibirTodasPessoas(pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 6:
                        exibirPessoasFisicas(pessoaFisicaDAO);
                        break;
                    case 7:
                        exibirPessoasJuridicas(pessoaJuridicaDAO);
                        break;
                    case 0:
                        System.out.println("## > Saindo....");
                        break;
                    default:
                        System.out.println("## > Opcao invalida. Verifique a opcao escolhida e tente novamente");
                }
            } catch (SQLException e) {
                System.out.println("## > Erro de banco de dados: " + e.getMessage());
                
            }
        } while (escolha != 0);
    }

    // Métods para cadastrar Pessoa Fisica
    private static void cadastrarPessoaFisica(PessoaFisicaDAO pessoaFisicaDAO, Scanner scanner) throws SQLException {
        System.out.println("Digite o nome para a pessoa fisica:");
        String nome = scanner.nextLine();
        System.out.println("Digite o endereco:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o e-mail:");
        String email = scanner.nextLine();
        System.out.println("Digite o CPF:");
        String cpf = scanner.nextLine();

        PessoaFisica novaPessoaFisica = new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
        pessoaFisicaDAO.inserirPessoaFisica(novaPessoaFisica);
        System.out.println("## > Pessoa Fisica cadastrada com sucesso!");
    }

    // Métods para cadastrar Pessoa Juridica
    private static void cadastrarPessoaJuridica(PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("Digite o nome para a pessoa juridica:");
        String nome = scanner.nextLine();
        System.out.println("Digite o endereco:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o e-mail:");
        String email = scanner.nextLine();
        System.out.println("Digite o CNPJ:");
        String cnpj = scanner.nextLine();

        PessoaJuridica novaPessoaJuridica = new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
        pessoaJuridicaDAO.incluir(novaPessoaJuridica);
        System.out.println("## > Pessoa Juridica cadastrada com sucesso.");
    }

    // Métods para alterar Pessoas
    private static void alterarPessoa(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("F - Alterar pessoa fisica | J - Alterar pessoa juridica");
        System.out.println("Escolha uma opcao: ");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();
        int id;

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Digite o ID da pessoa fisica que deseja alterar:");
            id = scanner.nextInt();
            scanner.nextLine();

            PessoaFisica pessoaExistente = pessoaFisicaDAO.getPessoa(id);

            if (pessoaExistente != null) {
                System.out.println("Digite o novo nome da pessoa fisica:");
                String novoNome = scanner.nextLine();
                System.out.println("Digite o novo endereco:");
                String novoLogradouro = scanner.nextLine();
                System.out.println("Digite a nova cidade:");
                String novaCidade = scanner.nextLine();
                System.out.println("Digite o novo estado:");
                String novoEstado = scanner.nextLine();
                System.out.println("Digite o novo telefone:");
                String novoTelefone = scanner.nextLine();
                System.out.println("Digite o novo e-mail:");
                String novoEmail = scanner.nextLine();
                System.out.println("Digite o novo CPF:");
                String novoCpf = scanner.nextLine();

                PessoaFisica novaPessoa = new PessoaFisica(id, novoNome, novoLogradouro, novaCidade, novoEstado, novoTelefone, novoEmail, novoCpf);
                pessoaFisicaDAO.alterar(novaPessoa);
                System.out.println("## > Pessoa fisica atualizada com sucesso.");
            } else {
                System.out.println("## > Pessoa fisica não encontrada.");
            }
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Digite o ID da pessoa juridica que deseja alterar:");
            
            if (scanner.hasNextInt()) {
            id = scanner.nextInt();
            System.out.println("Voce adicionou o ID: " + id);
        } else {
            String input = scanner.next();
            System.out.println("Nao e permitido Strings: " + input);
            return;
        }
            
            scanner.nextLine();

            PessoaJuridica pessoaExistente = pessoaJuridicaDAO.getPessoa(id);

            if (pessoaExistente != null) {
                System.out.println("Digite o novo nome da pessoa juridica:");
                String novoNome = scanner.nextLine();
                System.out.println("Digite o novo endereco:");
                String novoLogradouro = scanner.nextLine();
                System.out.println("Digite a nova cidade:");
                String novaCidade = scanner.nextLine();
                System.out.println("Digite o novo estado:");
                String novoEstado = scanner.nextLine();
                System.out.println("Digite o novo telefone:");
                String novoTelefone = scanner.nextLine();
                System.out.println("Digite o novo email:");
                String novoEmail = scanner.nextLine();
                System.out.println("Digite o novo CNPJ:");
                String novoCnpj = scanner.nextLine();

                PessoaJuridica novaPessoa = new PessoaJuridica(id, novoNome, novoLogradouro, novaCidade, novoEstado, novoTelefone, novoEmail, novoCnpj);
                pessoaJuridicaDAO.alterar(novaPessoa);
                System.out.println("## > ## >Pessoa juridica atualizada com sucesso.");
            } else {
                System.out.println("## > Pessoa juridica não encontrada.");
            }
        } else {
            System.out.println("## > Opcao invalida.");
        }
    }

    // Métods para excluir Pessoas
    private static void excluirPessoa(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("(F) - Excluir pessoa fisica | (J) - Excluir pessoa juridica");
        System.out.println("Escolha uma opcao: ");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Digite o ID da pessoa fisica para ser removida:");
            int id = scanner.nextInt();
            pessoaFisicaDAO.excluir(id);
            System.out.println("## > Pessoa fisica removida com sucesso.");
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Digite o ID da pessoa juridica para ser removida:");
            int id = scanner.nextInt();
            pessoaJuridicaDAO.excluir(id);
            System.out.println("## > Pessoa juridica excluida com sucesso.");
        } else {
            System.out.println("## > Opcao invalida");
        }
    }

    // Métods para buscar Pessoas
    private static void buscarPessoaPeloId(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("(F) - Buscar pessoa fisica | (J) - Buscar pessoa juridica");
        System.out.println("Escolha uma opcao: ");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Digite o ID da pessoa fisica:");
            int id = scanner.nextInt();
            scanner.nextLine();
            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);

            if (pessoaFisica != null) {
                System.out.println("Informacoes da pessoa fisica:");
                System.out.println("ID: " + pessoaFisica.getId());
                System.out.println("Nome: " + pessoaFisica.getNome());
                System.out.println("Endereco: " + pessoaFisica.getEndereco());
                System.out.println("Cidade: " + pessoaFisica.getCidade());
                System.out.println("Estado: " + pessoaFisica.getEstado());
                System.out.println("Telefone: " + pessoaFisica.getTelefone());
                System.out.println("E-mail: " + pessoaFisica.getEmail());
                System.out.println("CPF: " + pessoaFisica.getCpf());
                System.out.println("======>Busca feita com Sucesso.========");
            } else {
                System.out.println("## > Nao foi possivel encontrar a pessoa fisica");
            }
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Digite o ID da pessoa juridica:");
            int id = scanner.nextInt();
            scanner.nextLine();

            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);

            if (pessoaJuridica != null) {
                System.out.println("Informacaoes da pessoa juridica:");
                System.out.println("ID: " + pessoaJuridica.getId());
                System.out.println("Nome: " + pessoaJuridica.getNome());
                System.out.println("Endereco: " + pessoaJuridica.getEndereco());
                System.out.println("Cidade: " + pessoaJuridica.getCidade());
                System.out.println("Estado: " + pessoaJuridica.getEstado());
                System.out.println("Telefone: " + pessoaJuridica.getTelefone());
                System.out.println("E-mail: " + pessoaJuridica.getEmail());
                System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
                System.out.println("======>Busca feita com Sucesso.========");
            } else {
                System.out.println("## > Nao foi possivel encontrar a pessoa juridica.");
            }
        } else {
            System.out.println("## > Opcao invalida.");
        }
    }

    // Métods para exibir todas as Pessoas
    private static void exibirTodasPessoas(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.println("Relacao de pessoas fisicas cadastradas:");
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        if (pessoasFisicas.isEmpty()) {
            System.out.println("## > Nenhuma cadastro de pessoa fisica.");
        } else {
            for (PessoaFisica pf : pessoasFisicas) {
                System.out.println("ID: " + pf.getId());
                System.out.println("Nome: " + pf.getNome());
                System.out.println("Endereco: " + pf.getEndereco());
                System.out.println("Cidade: " + pf.getCidade());
                System.out.println("Estado: " + pf.getEstado());
                System.out.println("Telefone: " + pf.getTelefone());
                System.out.println("E-mail: " + pf.getEmail());
                System.out.println("CPF: " + pf.getCpf());
                System.out.println();
            }
        }

        System.out.println("Relacao de pessoas juridicas cadastradas:");
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoasJuridicas();
        if (pessoasJuridicas.isEmpty()) {
            System.out.println("## > Nenhuma pessoa juridica cadastrada.");
        } else {
            for (PessoaJuridica pj : pessoasJuridicas) {
                System.out.println("ID: " + pj.getId());
                System.out.println("Nome: " + pj.getNome());
                System.out.println("Endereco: " + pj.getEndereco());
                System.out.println("Cidade: " + pj.getCidade());
                System.out.println("Estado: " + pj.getEstado());
                System.out.println("Telefone: " + pj.getTelefone());
                System.out.println("E-mail: " + pj.getEmail());
                System.out.println("CNPJ: " + pj.getCnpj());
                System.out.println("======>Exibicao Completa feita com Sucesso.========");
            }
        }
    }

    // Métods para exibir Pessoas Fisicas
    private static void exibirPessoasFisicas(PessoaFisicaDAO pessoaFisicaDAO) throws SQLException {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        if (pessoasFisicas.isEmpty()) {
            System.out.println("## > Nenhuma pessoa fisica cadastrada.");
        } else {
            for (PessoaFisica pf : pessoasFisicas) {
                System.out.println("ID: " + pf.getId());
                System.out.println("Nome: " + pf.getNome());
                System.out.println("Endereco: " + pf.getEndereco());
                System.out.println("Cidade: " + pf.getCidade());
                System.out.println("Estado: " + pf.getEstado());
                System.out.println("Telefone: " + pf.getTelefone());
                System.out.println("E-mail: " + pf.getEmail());
                System.out.println("CPF: " + pf.getCpf());
                System.out.println("======>Exibicao Completa feita com Sucesso.========");
            }
        }
    }

    // Métods para exibir Pessoas Fisicas
    private static void exibirPessoasJuridicas(PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoasJuridicas();
        if (pessoasJuridicas.isEmpty()) {
            System.out.println("## > Nenhuma pessoa juridica cadastrada.");
        } else {
            for (PessoaJuridica pj : pessoasJuridicas) {
                System.out.println("ID: " + pj.getId());
                System.out.println("Nome: " + pj.getNome());
                System.out.println("Endereco: " + pj.getEndereco());
                System.out.println("Cidade: " + pj.getCidade());
                System.out.println("Estado: " + pj.getEstado());
                System.out.println("Telefone: " + pj.getTelefone());
                System.out.println("E-mail: " + pj.getEmail());
                System.out.println("CNPJ: " + pj.getCnpj());
                System.out.println("======>Exibicao Completa feita com Sucesso.========");
            }
        }
    }
}
