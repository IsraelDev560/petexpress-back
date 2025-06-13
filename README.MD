# PetExpress Backend

A Spring Boot 3 project built with Java 21 for the final college assignment at Unimar. It provides the REST API used by the PetExpress management system.

## 🇺🇸 English

### [Deploy](https://petexpress-back.onrender.com)
This API is currently running at: <https://petexpress-back.onrender.com>.
The PostgreSQL database is hosted on [Neon](https://neon.tech/).

### Requirements
- Java 21 and Maven 3.9+
- PostgreSQL database
- Docker (optional)

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/IsraelDev560/petexpress-back.git
   cd petexpress-back
   ```
2.  Create two environment files in the project root:
- `.env` with the values used in the standard profile:
  ```env
  DATABASE_URL=jdbc:postgresql://localhost:5432/petexpress
  DATABASE_USER=postgres
  DATABASE_PASSWORD=postgres
  JWT_SECRET=your-secret
  ```
- `.env.dev` with the variables used when the `dev` profile is active:
  ```env
  DATABASE_URL_DEV=jdbc:postgresql://localhost:5432/petexpress
  DATABASE_USER_DEV=postgres
  DATABASE_PASSWORD_DEV=postgres
  JWT_SECRET=your-secret
  ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API will be available on `http://localhost:8080`.
4. You can also build a container and start it using Docker Compose (requires the variables above):
   ```bash
   docker compose up --build
   ```
5. If you want to run a local PostgreSQL instance together with the API, use `docker-compose-local.yml`:
   ```bash
   docker compose -f docker-compose-local.yml up --build
   ```
6. SQL migration files can be found under `src/main/resources/db/migration`. Flyway is not yet enabled, so they are only references for now.

### 🧑‍💻 How to create an ADMIN user in the database

After running the containers with `docker-compose up`, follow the steps below to access the PostgreSQL container and manually insert an admin user into the `users` table.

#### 1. Access the PostgreSQL container

```bash
docker exec -it db_petexpress psql -U postgres -d petexpress
```

> This will open the interactive PostgreSQL terminal inside the container.

#### 2. Insert the ADMIN user

Run the following SQL command inside the PostgreSQL terminal:

```sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO users (id, username, password, role)
VALUES (
  gen_random_uuid(),
  'Admin',
  crypt('Admin@123', gen_salt('bf')),
  'ADMIN'
);
```

> This password is the Bcrypt hash of `Admin@123`.

#### 3. Check if it was created

```sql
SELECT id, username, role FROM users;
```

#### ✅ Test login

- **Username:** `Admin`
- **Password:** `Admin@123`

You can now authenticate using `POST /auth/login` to obtain a JWT token and access protected routes.

### Folder structure
- `controller/` – REST controllers for authentication, animals, tasks, task types and users.
- `entities/` – JPA entities such as `User`, `Animal`, `Task` and `TaskType`.
- `dto/` – Request and response DTOs.
- `service/` – Business services and token generation.
- `repository/` – Spring Data repositories.
- `security/` – JWT authentication filter and security configuration.
- `exceptions/` – Custom exceptions and global handler.

### API Endpoints
Most routes require a valid Bearer token obtained from `POST /auth/login`.

- `POST /auth/login` – authenticate and receive a JWT.
- `POST /auth/register` – create a new user (ADMIN role required).
- `GET /animals`, `GET /animals/{id}`, `POST /animals`, `PATCH /animals/{id}`, `DELETE /animals/{id}`
- `GET /task`, `POST /task`, `GET /task/{id}`, `PUT /task/{id}`, `DELETE /task/{id}`
- `GET /task-types`, `POST /task-types`, `GET /task-types/{id}`, `PUT /task-types/{id}`, `DELETE /task-types/{id}`
- `GET /users`, `GET /users/{id}`, `GET /users/myinfo`, `PATCH /users/{id}`, `DELETE /users/{id}`

Swagger documentation is available at `/swagger-ui/index.html`.

---

## 🇧🇷 Português

### [Deploy](https://petexpress-back.onrender.com)
Esta API está hospedada em: <https://petexpress-back.onrender.com>.
O banco de dados PostgreSQL fica no [Neon](https://neon.tech/).

### Requisitos
- Java 21 e Maven 3.9+
- Servidor PostgreSQL
- Docker (opcional)

### Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/IsraelDev560/petexpress-back.git
   cd petexpress-back
   ```
2. Crie dois arquivos de variáveis de ambiente na raiz do projeto:
   - `.env` com os valores padrão:
     ```env
     DATABASE_URL=jdbc:postgresql://localhost:5432/petexpress
     DATABASE_USER=postgres
     DATABASE_PASSWORD=postgres
     JWT_SECRET=sua-chave
     ```
   - `.env.dev` com as variáveis usadas quando o perfil `dev` estiver ativo:
     ```env
     DATABASE_URL_DEV=jdbc:postgresql://localhost:5432/petexpress
     DATABASE_USER_DEV=postgres
     DATABASE_PASSWORD_DEV=postgres
     JWT_SECRET=sua-chave
     ```
3. Inicie a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   A API ficará acessível em `http://localhost:8080`.
4. Caso prefira, use Docker Compose para gerar a imagem e executar (é preciso definir as variáveis acima):
   ```bash
   docker compose up --build
   ```
5. Para rodar um PostgreSQL local junto com a API, utilize `docker-compose-local.yml`:
   ```bash
   docker compose -f docker-compose-local.yml up --build
   ```

6. Os scripts de migração SQL estão em `src/main/resources/db/migration`. O Flyway ainda não está habilitado, portanto eles servem apenas de referência.

### 🧑‍💻 Como criar um usuário ADMIN no banco de dados

Após rodar os containers com `docker-compose up`, siga os passos abaixo para acessar o container do PostgreSQL e inserir manualmente um usuário com perfil ADMIN na tabela `users`.

#### 1. Acesse o container do PostgreSQL

```bash
docker exec -it db_petexpress psql -U postgres -d petexpress
```

> Isso abrirá o terminal interativo do PostgreSQL dentro do container.

#### 2. Insira o usuário ADMIN

Execute o comando SQL abaixo dentro do terminal do PostgreSQL:

```sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO users (id, username, password, role)
VALUES (
  gen_random_uuid(),
  'Admin',
  crypt('Admin@123', gen_salt('bf')),
  'ADMIN'
);
```

> A senha salva será `Admin@123`, protegida com hash Bcrypt.

#### 3. Verifique se foi criado corretamente

```sql
SELECT id, username, role FROM users;
```

#### ✅ Teste de login

- **Usuário:** `Admin`
- **Senha:** `Admin@123`

Agora você já pode se autenticar usando o endpoint `POST /auth/login` para obter um token JWT e acessar as rotas protegidas.

### Estrutura de pastas
- `controller/` – controladores REST para autenticação, animais, tarefas, tipos de tarefa e usuários.
- `entities/` – entidades JPA como `User`, `Animal`, `Task` e `TaskType`.
- `dto/` – DTOs de requisição e resposta.
- `service/` – regras de negócio e geração de token.
- `repository/` – repositórios Spring Data.
- `security/` – filtro JWT e configuração de segurança.
- `exceptions/` – exceções customizadas e tratador global.

### Rotas da API
A maioria das rotas requer um token Bearer obtido em `POST /auth/login`.

- `POST /auth/login` – gera um JWT.
- `POST /auth/register` – cria um novo usuário (requer papel ADMIN).
- `GET /animals`, `GET /animals/{id}`, `POST /animals`, `PATCH /animals/{id}`, `DELETE /animals/{id}`
- `GET /task`, `POST /task`, `GET /task/{id}`, `PUT /task/{id}`, `DELETE /task/{id}`
- `GET /task-types`, `POST /task-types`, `GET /task-types/{id}`, `PUT /task-types/{id}`, `DELETE /task-types/{id}`
- `GET /users`, `GET /users/{id}`, `GET /users/myinfo`, `PATCH /users/{id}`, `DELETE /users/{id}`

A documentação Swagger fica disponível em `/swagger-ui/index.html`.