# 📌 Seja Bem-vindo

## 🛒 API de E-commerce - Java Spring Boot

## 🏗 Arquitetura: RESTful API

## 📖 Sobre o Projeto

### Este projeto consiste em uma API para um e-commerce que suporta múltiplos vendedores e compradores, inspirada nos principais marketplaces como Mercado Livre, Shopee e Amazon.

## 🔐 Permissões de Usuários

### Usuários não autenticados:

#### Podem visualizar anúncios de produtos.
#### Podem calcular o frete informando o CEP de origem.

### Usuários autenticados:

#### Podem adicionar produtos aos favoritos e ao carrinho de compras.
#### Podem e Devem se cadastrar na tabela de Compradores para realizar compras.
#### Podem e Devem se cadastrar na tabela de Vendedores para criar anúncios de produtos.

## 🚨 Restrições:

### Usuários que não estão cadastrados como Compradores não podem efetuar compras.

### Usuários que não estão cadastrados como Vendedores não podem criar anúncios.

## 🔑 Autenticação

### A API utiliza JWT (JSON Web Token) para autenticação, garantindo um sistema stateless e seguro.

## 📷 Upload de Imagens

### Para garantir boas práticas, as imagens dos produtos são armazenadas na nuvem usando a API do Cloudinary. No banco de dados, apenas a URL da imagem é salva, reduzindo a carga da aplicação.

## 💳 Pagamentos

### As transações financeiras são processadas pela API Gateway da Stripe, sendo utilizadas para:

#### Pagamentos de compras realizadas na plataforma.
#### Repasses automáticos dos valores das vendas para os vendedores.

## 🚨 Segurança nos pagamentos: Nenhum dado sensível (cartões de crédito, contas bancárias, etc.) é armazenado na API. Toda a gestão financeira segue os padrões de segurança PCI Compliance.

## 📩 Automação de E-mails

### Durante o cadastro de vendedores, a API da Stripe exige que algumas informações sejam fornecidas diretamente no painel deles por se tratar da criação de uma conta connect. Para facilitar esse processo, foi implementada uma automação de e-mails usando a API do SendGrid. Assim, um e-mail com o link de cadastro é enviado automaticamente ao vendedor.

## 🚚 Rastreamento de Pedidos

### A API integra a Melhor Envio para:

#### Cálculo de frete (valor e tempo estimado de entrega).
#### Rastreamento de compras diretamente na plataforma.

## ⚠️ Exceções Personalizadas

### Seguindo as melhores práticas para APIs RESTful, todas as respostas da API incluem tratamento de erros personalizado para maior clareza e padronização.

## 🗄 Banco de Dados

### O projeto utiliza um banco de dados relacional (MySQL) para garantir uma estrutura organizada e bem definida, permitindo relacionamentos entre tabelas como usuários, produtos e transações.

### ✅ Testes
### Este projeto possui testes unitários escritos com JUnit 5 e Mockito.

## 📑 Documentação da API

### Para visualizar e testar os endpoints da API Documetada Com Swagger UI:

#### 🔗 **Acesse a documentação aqui:** [Swagger UI](http://localhost:8080/swagger-ui/index.html)

#### Certifique-se de que a API está em execução para acessar a documentação.
