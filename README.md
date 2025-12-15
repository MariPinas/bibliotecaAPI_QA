# Biblioteca API - Qualidade de Software

**Disciplina:** Qualidade de Software  
**Professor:** Anisio Silva  
**Alunos:** David Barbosa, Mariana Pereira, Renan Martins e Ruhan Garatini                                                          
**E-mail:** [anisio.silva@ifsp.du.br](mailto:anisio.silva@ifsp.du.br)

---

## 1. Descrição

Projeto para desenvolvimento e implementação de conceitos de qualidade de software como Clean Code e SOLID com **Spring Boot** utilizando **H2 Database em memória**.  
Contém um **CRUD de livros, usuários, estoque e empréstimos**, com relacionamentos entre entidades, relatórios, regras de negócio, endpoints REST e documentação via Swagger.

---

## 2. Tecnologias

* Java 17+  
* Spring Boot 3+  
* Spring Data JPA  
* H2 Database (em memória)  
* Swagger/OpenAPI  
* Maven

---

## 3. Endpoints

* **Swagger UI:**  
  [http://localhost:8090/library/swagger-ui/index.html#/](http://localhost:8090/library/swagger-ui/index.html#/)

* **H2 Console:**  
  [http://localhost:8090/library/h2-console/login.do](http://localhost:8090/library/h2-console/login.do)  

  * **JDBC URL:** `jdbc:h2:mem:bibliotecaDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`  
  * **Usuário:** `sa`  
  * **Senha:** (deixe em branco)

---

## 4. Como Rodar o Projeto

1.  Abra o projeto no IDE de sua preferência (Eclipse, IntelliJ, VS Code).
2.  Execute a aplicação pelo terminal da IDE ou diretamente no terminal do projeto:

    ```bash
    mvn spring-boot:run
    ```



