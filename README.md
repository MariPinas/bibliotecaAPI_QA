# Spring Demo - Qualidade de Software

**Disciplina:** Qualidade de Software  
**Professor:** Anisio Silva  
**Alunos:** Mariana Pereira e David Barbosa                                                          
**E-mail:** [anisio.silva@ifsp.du.br](mailto:anisio.silva@ifsp.du.br)

---

## 1. Descrição

Projeto de revisão de conceitos de **Spring Boot** utilizando **H2 Database em memória**.  
Contém um **CRUD de carros, clientes e vendas**, com relacionamentos entre entidades, endpoints REST e documentação via Swagger.

O código foi propositalmente escrito de forma confusa para prática de **refatoração e aplicação de Clean Code**.

---

## 2. Funcionalidades

* CRUD de **Carros**  
* CRUD de **Clientes**  
* Registro de **Vendas** (relacionando Carros e Clientes)  
* Banco **H2** em memória  
* Documentação **Swagger/OpenAPI**

---

## 3. Tecnologias

* Java 17+  
* Spring Boot 3+  
* Spring Data JPA  
* H2 Database (em memória)  
* Swagger/OpenAPI  
* Maven

---

## 4. Endpoints

* **Swagger UI:**  
  [http://localhost:8090/library/swagger-ui/index.html#/](http://localhost:8080/demo/v1/swagger-ui/index.html#/)

* **H2 Console:**  
  [http://localhost:8090/library/h2-console/login.do](http://localhost:8080/demo/v1/h2-console/login.do)  

  * **JDBC URL:** `jdbc:h2:mem:bibliotecaDB`  
  * **Usuário:** `sa`  
  * **Senha:** (deixe em branco)

---

## 5. Como Rodar o Projeto

1.  Abra o projeto no IDE de sua preferência (Eclipse, IntelliJ, VS Code).
2.  Execute a aplicação pelo terminal da IDE ou diretamente no terminal do projeto:

    ```bash
    mvn spring-boot:run
    ```

3.  Teste os endpoints via **Swagger UI** ou usando ferramentas como **Postman** ou **curl**.
4.  Para acessar o **H2 Console** (banco de dados em memória):
    *   **URL:** [http://localhost:8090/library/h2-console](http://localhost:8080/demo/v1/h2-console)
    *   **JDBC URL:** `jdbc:h2:mem:carrosDB`
    *   **Usuário:** `sa`
    *   **Senha:** *(deixe em branco)*

---

## 6. Observações

*   O banco **H2** é em memória, portanto os dados são apagados a cada reinício da aplicação.
*   O projeto contém **código intencionalmente bagunçado**, permitindo a prática de **refatoração e Clean Code**.
*   Os alunos podem aplicar boas práticas de:
    *   API REST
    *   Tratamento de exceções
    *   Uso de DTOs (Data Transfer Objects)



