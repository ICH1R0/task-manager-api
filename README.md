# Task Manager API

API REST para gerenciamento de tarefas (criar, listar, consultar, atualizar e remover), desenvolvida com **Spring Boot** durante o curso da DIO. O foco do projeto é a organização em camadas e a cobertura de testes.

## Tecnologias

- Java 25
- Spring Boot 4.1.2 (Web e Validation)
- Gradle (com wrapper incluído)
- Lombok
- JUnit 5 e MockMvc para testes
- Spring REST Docs + Asciidoctor para a documentação da API

## Arquitetura

O código é dividido em três camadas, em `src/main/java/dio/taskmanager`:

| Camada | O que contém |
|---|---|
| `domain` | Entidade `Task`, `TaskId`, `TaskStatus`, a interface `TaskRepository` e as exceções de domínio |
| `application` | Casos de uso (criar, listar, buscar por id, atualizar e deletar), com seus objetos de entrada (`input`) e saída (`output`) |
| `infrastructure` | Controller HTTP, requests/responses, tratamento global de erros e o repositório em memória |

As regras de negócio ficam no `domain` e na `application`, sem depender de detalhes de HTTP ou de armazenamento. O repositório é uma implementação em memória, então **os dados são perdidos ao reiniciar a aplicação**.

## Como executar

Pré-requisito: JDK 25 instalado (o Gradle também pode baixá-lo automaticamente pelo toolchain).

```bash
# Linux / macOS
./gradlew bootRun

# Windows (PowerShell)
.\gradlew.bat bootRun
```

A API sobe em `http://localhost:8080`.

## Endpoints

Base: `/tasks`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `POST` | `/tasks` | Cria uma tarefa | `201 Created` |
| `GET` | `/tasks` | Lista todas as tarefas | `200 OK` |
| `GET` | `/tasks/{id}` | Busca uma tarefa pelo id (UUID) | `200 OK` |
| `PATCH` | `/tasks/{id}` | Atualiza campos da tarefa | `200 OK` |
| `DELETE` | `/tasks/{id}` | Remove a tarefa | `204 No Content` |

### Regras de validação

- `title`: obrigatório, entre 3 e 100 caracteres
- `description`: opcional, até 500 caracteres
- `status` (na atualização): `PENDING`, `IN_PROGRESS` ou `COMPLETED`. Toda tarefa nova começa como `PENDING`

### Exemplos

Criar uma tarefa:

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Estudar Spring Boot", "description": "Revisar arquitetura em camadas"}'
```

Resposta:

```json
{
  "id": "3f2b8c1e-5a47-4d0e-9c2a-1b6e7f8a9d10",
  "title": "Estudar Spring Boot",
  "description": "Revisar arquitetura em camadas",
  "status": "PENDING"
}
```

Atualizar o status:

```bash
curl -X PATCH http://localhost:8080/tasks/{id} \
  -H "Content-Type: application/json" \
  -d '{"status": "COMPLETED"}'
```

### Erros

- `400 Bad Request`: dados inválidos. O corpo traz um mapa com o campo e a mensagem de validação
- `404 Not Found`: tarefa não encontrada para o id informado

## Testes

```bash
./gradlew test
```

Os testes cobrem as três camadas: repositório, caso de uso e controller (com MockMvc).

## Documentação da API

A documentação é gerada com Spring REST Docs a partir dos testes do controller:

```bash
./gradlew asciidoctor
```

O HTML gerado fica em `build/docs/asciidoc`.

## Estrutura do projeto

```text
src
├── main
│   ├── java/dio/taskmanager
│   │   ├── domain
│   │   ├── application
│   │   └── infrastructure
│   └── resources
├── test
└── docs/asciidoc
```

## Próximos passos

- Persistir os dados em banco de dados (por exemplo, com Spring Data JPA)
- Completar a documentação dos endpoints de listagem, atualização e remoção
- Adicionar paginação e filtro por status na listagem
