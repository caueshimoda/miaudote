<div align="center">
  <img src="https://miaudote.org/logo-main.png" alt="MiAudote Logo" width="260" />

  # 🐾 MiAudote — Backend

  **API desenvolvida em Spring Boot para gerenciar o sistema de adoção de animais.**

  [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
  [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
  [![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
</div>

---

## 🐶 Sobre o projeto

O ***MiAudote*** é uma plataforma desenvolvida para **facilitar a conexão entre ONGs/protetores de animais e pessoas interessadas em adotar**.  
O **backend** é responsável por **gerenciar cadastros, autenticação, solicitações de adoção e armazenamento de dados**, disponibilizando uma **API RESTful** que é consumida pelo frontend.

O projeto foi desenvolvido como **trabalho de faculdade**, com o objetivo de aplicar conceitos de **Engenharia de Software**.

---

## 🧩 Tecnologias utilizadas

### **Backend**
- [Java 17+](https://www.java.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [MySQL](https://www.mysql.com/)
- [Maven](https://maven.apache.org/)

---

## 🧠 Funcionalidades principais

- Cadastro e autenticação (JWT) de ONGs e adotantes
- Cadastro, listagem, edição e exclusão de animais
- Upload de até 5 fotos por animal (armazenadas como longblob no banco)
- Solicitações de adoção com controle de status
- Favoritos (animais marcados pelos adotantes)

---

## ☁️ Hospedagem

| Camada | Plataforma | Link |
|:-------|:------------|:------|
| **Backend (API)** | [Render](https://render.com/) | 🔗 Endpoint utilizado pelo Frontend |
| **Banco de Dados** | [Aiven (MySQL)](https://aiven.io/) | 🔒 Privado |
| **Frontend** | [Vercel](https://vercel.com/) | 🌐 [miaudote.org](https://miaudote.org) |

---

## 👥 Equipe do projeto

Este repositório corresponde ao **Backend** do projeto *MiAudote*.

Desenvolvido por:

**Cauê Shimoda, Bruna Ogura e Samuel Leite**, responsáveis pelo **Backend**.  

Projeto desenvolvido em equipe por:  
**Leonardo Flores** — responsável pelo **Frontend e Banco de Dados**.  
**Ayana Hanashiro** — responsável pelo **design e documentação**.  
**Caio Luiz** — responsável pela **documentação**.  

📚 Curso: Análise e Desenvolvimento de Sistemas (FATEC-SP)  
🏫 Projeto acadêmico desenvolvido em equipe para fins educacionais.

---

## 🚀 Como executar localmente

### **Pré-requisitos**
- Java 17+
- Maven
- MySQL em execução
- IDE de sua preferência (IntelliJ, Eclipse, VS Code, etc.)

### **Passos**

```bash
# Clonar o repositório
git clone https://github.com/caueshimoda/miaudote.git

# Entrar na pasta do projeto
cd miaudote

# Configurar o banco de dados no arquivo:
# src/main/resources/application.properties

spring.datasource.url=URL_BANCO_DE_DADOS
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

# Compilar e executar o projeto
mvn spring-boot:run
