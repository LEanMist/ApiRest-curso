# API REST - Cursos

## 📚 Sobre o Projeto

API REST desenvolvida com Spring Boot para gerenciamento de cursos.

O sistema permite:

- Cadastro de cursos
- Listagem de cursos ativos
- Busca de curso por ID
- Atualização de cursos
- Exclusão lógica
- Listagem dos períodos disponíveis

O projeto foi desenvolvido utilizando boas práticas de desenvolvimento backend com Java e Spring Boot.

---

# 🚀 Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|---------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Maven | 4+ |
| MariaDB | 11+ |
| MariaDB Java Client | 3.5.8 |
| Flyway | 11.14.1 |
| SpringDoc OpenAPI | 3.0.2 |
| Lombok | Última |
| Spring Data JPA | 4.0.6 |
| Spring Validation | 4.0.6 |
| HeidiSQL | Opcional |

---

# 📦 Dependências Utilizadas

O projeto utiliza as seguintes dependências:

- Spring Web MVC
- Spring Data JPA
- Flyway Migration
- Validation
- Lombok
- MariaDB Driver
- Spring Boot DevTools
- SpringDoc OpenAPI

---

# 🗄️ Banco de Dados

O projeto utiliza MariaDB.

Antes de executar a aplicação:

1. Instale o MariaDB
2. Verifique usuário, senha e porta
3. Crie o banco de dados configurado no `application.properties`

Exemplo:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/apiCursos
```

Nesse caso, crie o banco:

```sql
CREATE DATABASE apiCursos;
```

---

# 💻 HeidiSQL (Opcional)

O HeidiSQL pode ser utilizado para:

- Criar bancos de dados
- Visualizar tabelas
- Inserir registros
- Validar conexão com o MariaDB

Configuração padrão:

| Configuração | Valor |
|--------------|-------|
| Host | localhost |
| Porta | 3306 |
| Usuário | root |
| Senha | sua senha |

As informações devem ser compatíveis com o arquivo:

```text
src/main/resources/application.properties
```

---

# ⚙️ Configuração do application.properties

Arquivo:

```text
src/main/resources/application.properties
```

Exemplo utilizado no projeto:

```properties
spring.application.name=apiRest

spring.datasource.url=jdbc:mariadb://localhost:3306/apiCursos
spring.datasource.username=root
spring.datasource.password=senai2026

spring.jpa.hibernate.ddl-auto=none

spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

---

# 🔧 Configurações que podem ser alteradas

Você pode alterar:

- Nome do banco
- Porta
- Usuário
- Senha
- Host

Exemplo:

## Alterar porta

```properties
spring.datasource.url=jdbc:mariadb://localhost:3307/apiCursos
```

## Alterar nome do banco

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/meuBanco
```

Após alterar o nome do banco, crie o banco correspondente no MariaDB.

---

# ▶️ Como Executar o Projeto

## 1. Clonar ou baixar Zip. o repositório

## 2. Abrir o projeto

Abra o projeto no IntelliJ IDEA.

---

## 3. Configurar o banco de dados

- Instale o MariaDB
- Crie o banco configurado no `application.properties`
- Configure usuário e senha corretamente

---

## 4. Atualizar dependências Maven

No IntelliJ:

```text
Maven > Reload Project
```

---

## 5. Executar a aplicação

Execute a classe principal:

```text
ApiRestApplication
```

O Flyway executará automaticamente as migrations e criará as tabelas.

---

# 📄 Swagger/OpenAPI

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

A documentação contém:

- Endpoints
- Exemplos JSON
- DTOs
- Status HTTP
- Testes das rotas

---

# 📌 Endpoints

| Método | Endpoint | Descrição |
|--------|----------|------------|
| GET | /cursos | Lista cursos ativos |
| GET | /cursos/{id} | Busca curso por ID |
| POST | /cursos | Cadastra curso |
| PUT | /cursos | Atualiza curso |
| DELETE | /cursos/{id} | Exclusão lógica |
| GET | /cursos/periodos | Lista períodos |

---

# 📌 Exemplo de Cadastro

```json
{
  "nome": "ADS",
  "periodo": "NOTURNO"
}
```

---

# 📌 Períodos Aceitos

- MATUTINO
- VESPERTINO
- NOTURNO
- INTEGRAL

---

# 🧱 Estrutura do Projeto

```text
src
 └── main
     ├── java
     │    └── br.com.senai.apiRest
     │         ├── controller
     │         │    └── CursoController.java
     │         │
     │         ├── curso
     │         │    ├── Curso.java
     │         │    ├── CursoRepository.java
     │         │    ├── DadosAtualizarCurso.java
     │         │    ├── DadosCadastroCurso.java
     │         │    ├── DadosDetalhamentoCurso.java
     │         │    └── DadosListagemCurso.java
     │         │
     │         └── ApiRestApplication.java
     │
     └── resources
          ├── application.properties
          │
          └── db
               └── migration
                    └── V1__create-table-cursos.sql
```
