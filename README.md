# Sistema de Gerenciamento de Tarefas - Tech Challenge

API REST para gerenciamento de tarefas desenvolvida como parte do desafio técnico para Desenvolvedor Java.

---

## Funcionalidades Implementadas

 **CRUD Completo de Tarefas**

* `POST /tarefas` - Criar nova tarefa
* `GET /tarefas` - Listar tarefas com filtros
* `GET /tarefas/{id}` - Buscar tarefa por ID
* `PUT /tarefas/{id}/iniciar` - Iniciar tarefa
* `PUT /tarefas/{id}/concluir` - Concluir tarefa
* `DELETE /tarefas/{id}` - Excluir tarefa

 **Filtros Avançados**

* Status: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`
* Prioridade: `BAIXA`, `MEDIA`, `ALTA`
* Data de criação: Intervalo personalizado
* Ordenação: Por `dataCriacao` (padrão) ou `prioridade`

**Regras de Negócio**

* Tarefas criadas iniciam automaticamente como `PENDENTE`
* `dataCriacao` gerada automaticamente no servidor
* Prioridade é campo obrigatório na criação
* Apenas tarefas `PENDENTES` podem ser iniciadas
* Tarefas concluídas registram `dataConclusao` automaticamente
* Validações de estado (não iniciar já iniciada, não concluir já concluída)

---

## Arquitetura Técnica

```
src/main/java/com/techtest/gerenciador_tarefas/
├── config/                   # Configurações (Swagger)
├── controller/               # Controladores REST (@RestController)
├── service/                  # Lógica de negócio (@Service)
├── repository/               # Persistência (JpaRepository)
├── model/                    # Entidades JPA (@Entity)
├── DTO/                      # Objetos de Transferência de Dados
├── mapper/                   # Conversores DTO ↔ Entity
├── specification/            # Filtros dinâmicos (Specification)
└── globalExceptionHandler/   # Tratamento de erros padronizado
```

---

## Tecnologias Utilizadas

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* H2 Database (em memória)
* SpringDoc OpenAPI 3 (Swagger UI)
* Maven (Gerenciamento de dependências)
* JUnit 5 (Testes unitários)

---

##  Como Executar o Projeto

### Pré-requisitos

* Java 17 ou superior
* Maven 3.6+
* Git (opcional)

### Passo 1: Clone o repositório

```bash
git clone https://github.com/JONATH-OLIVEIRA/tech-desafio-gerenciamento-tarefas.git
cd tech-desafio-gerenciamento-tarefas
```

### Passo 2: Execute a aplicação

**Opção 1: Usando Maven**

```bash
mvn spring-boot:run
```

**Opção 2: Executando o JAR**

```bash
mvn clean package
java -jar target/gerenciador_tarefas-0.0.1-SNAPSHOT.jar
```

### Passo 3: Acesse os endpoints

A aplicação estará disponível em: `http://localhost:8080`
Documentação da API (Swagger UI): `http://localhost:8080/swagger-ui.html`

---

## Endpoints Disponíveis

**Criar Tarefa**

```http
POST /tarefas
Content-Type: application/json

{
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar conceitos de Spring Data JPA",
  "prioridade": "ALTA"
}
```

**Listar Tarefas (com filtros)**

```http
GET /tarefas?status=PENDENTE&prioridade=ALTA&dataInicio=01/01/2024&dataFim=31/12/2024&ordenarPor=prioridade
```

Parâmetros de consulta:

* `status` (opcional): `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`
* `prioridade` (opcional): `BAIXA`, `MEDIA`, `ALTA`
* `dataInicio` e `dataFim` (opcional): Formato `dd/MM/yyyy`
* `ordenarPor` (opcional): `dataCriacao` (padrão) ou `prioridade`

**Iniciar Tarefa**

```http
PUT /tarefas/{id}/iniciar
```

**Concluir Tarefa**

```http
PUT /tarefas/{id}/concluir
```

**Buscar Tarefa por ID**

```http
GET /tarefas/{id}
```

**Excluir Tarefa**

```http
DELETE /tarefas/{id}
```

---

## Banco de Dados

### H2 DATABASE

```properties
spring.datasource.url=jdbc:h2:mem:gerenciador-tarefas
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### H2 CONSOLE

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### SQL de exemplo

```sql
CREATE TABLE tarefas (
  id UUID PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  descricao VARCHAR(500),
  status VARCHAR(20) NOT NULL,
  prioridade VARCHAR(20) NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  data_conclusao TIMESTAMP
);
```

---

## Testes Unitarios
### Cobertura de Testes:
- **18 testes implementados** (6 de regras de negócio + 12 de validações)
- **100% dos cenários críticos testados**
- **Todos os testes passando**

### Categorias de Testes:
1. **Regras de Negócio** (6 testes):
   - Criação de tarefas
   - Início e conclusão de tarefas
   - Validações de status

2. **Validações de Entrada** (12 testes):
   - Campos obrigatórios (título, prioridade)
   - Valores inválidos (vazios, nulos)
   - IDs inexistentes
   - Todas prioridades válidas

```bash
mvn test
```

---

## Decisões Técnicas

1. **Spring Data JPA Specifications**

   * Flexibilidade para filtros dinâmicos
   * Código organizado e extensível
   * Segue padrão DDD

2. **Tratamento de Erros Centralizado**

   * `@RestControllerAdvice`
   * Respostas padronizadas

3. **DTO Pattern**

   * Evita expor detalhes de implementação
   * Flexibilidade na evolução da API

4. **H2 em Memória**

   * Atende à especificação "persistência em memória"
   * Fácil configuração e execução

---

## Estrutura do Projeto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/techtest/gerenciador_tarefas/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   │   └── specification/
│   │   │   ├── model/
│   │   │   ├── DTO/
│   │   │   ├── mapper/
│   │   │   └── globalExceptionHandler/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql (opcional)
│   └── test/
│       └── java/com/techtest/gerenciador_tarefas/service/
├── pom.xml
└── README.md
```

---

## Considerações Finais

* Código limpo e organizado
* Documentação completa com Swagger/OpenAPI
* Tratamento robusto de erros
* Filtros dinâmicos flexíveis
* Separação clara de responsabilidades

---

## Possíveis Melhorias Futuras

* Autenticação e autorização (Spring Security)
* Cache para melhor performance
* Paginação nos endpoints de listagem
* Logging mais detalhado
* Deploy em container (Docker)

---

## Contato

* Desenvolvedor: Jonath Oliveira
* LinkedIn: [https://www.linkedin.com/in/jonatholiveira/](https://www.linkedin.com/in/jonatholiveira/)
* GitHub: [JONATH-OLIVEIRA](https://github.com/JONATH-OLIVEIRA)
