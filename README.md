# Projeto CadastroBD

# Aluno: Alex Barroso Paz  - Matricula: 2023 06 15 1781 


## Descrição

Este projeto é uma prática de desenvolvimento Java voltada para a criação de um sistema de cadastro com persistência em banco de dados SQL Server utilizando o JDBC. O objetivo é implementar e demonstrar o uso do padrão DAO (Data Access Object) para gerenciar operações de banco de dados, aplicando conceitos de mapeamento objeto-relacional (ORM) em Java.

## Objetivos

- Implementar persistência de dados utilizando JDBC.
- Aplicar o padrão DAO para manuseio de dados.
- Realizar o mapeamento objeto-relacional em Java.
- Criar e gerenciar sistemas cadastrais com persistência em banco de dados relacional.

## Requisitos

- **SQL Server**: Com banco de dados criado anteriormente (ex: `loja`).
- **JDK e NetBeans IDE**: Para desenvolvimento e execução do projeto.
- **Driver JDBC para SQL Server**: Necessário para a conexão com o banco de dados.
- **Computador com acesso à internet**: Para configurar e testar as funcionalidades.

## Estrutura do Projeto

### Classes e Pacotes

- **`Pessoa`**: Classe base com atributos como `id`, `nome`, `logradouro`, `cidade`, `estado`, `telefone`, `email`.
- **`PessoaFisica`**: Extende `Pessoa`, adicionando o campo `cpf` e aplicando polimorfismo no método `exibir`.
- **`PessoaJuridica`**: Extende `Pessoa`, adicionando o campo `cnpj` e aplicando polimorfismo no método `exibir`.
- **`ConectorBD`**: Gerencia a conexão com o banco de dados e facilita a execução de comandos SQL.
- **`SequenceManager`**: Fornece o próximo valor de uma sequência para uso no banco de dados.
- **`PessoaFisicaDAO` e `PessoaJuridicaDAO`**: Implementam as operações CRUD para `PessoaFisica` e `PessoaJuridica`, respectivamente.

### Funcionalidades

- Inclusão, alteração, exclusão e consulta de pessoas físicas e jurídicas no banco de dados.
- Testes realizados através de uma classe principal (`CadastroBDTeste`) que executa operações no banco de dados e exibe os resultados no console.

## Como Executar

1. Clone o repositório do Git.
2. Abra o projeto no NetBeans.
3. Configure a conexão com o banco de dados SQL Server.
4. Execute a classe principal `CadastroBDTeste` para testar as funcionalidades implementadas.

## Relatório Discente

A prática inclui a elaboração de um relatório em formato PDF, contendo:

- Título da prática
- Objetivo da prática
- Códigos desenvolvidos
- Resultados obtidos
- Análise e conclusão sobre o uso do JDBC, DAO e conceitos de programação orientada a objetos.

## Entrega

- Armazene o projeto no Git.
- Anexe a documentação do projeto (em PDF) ao repositório.
- Compartilhe o link do repositório com o tutor para correção.

## Referências

- [Introdução ao JDBC](https://www.devmedia.com.br/jdbc-tutorial/6638)
- [Padrões de Projeto](https://refactoring.guru/pt-br/design-patterns)
- [Os 4 pilares da Programação Orientada a Objetos](https://www.devmedia.com.br/os-4-pilares-da-programacao-orientada-a-objetos/9264)
