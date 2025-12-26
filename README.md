# 🚀 Sistema de Gerenciamento de Tarefas - Tech Challenge

API REST para gerenciamento de tarefas desenvolvida como parte do desafio técnico para Desenvolvedor Java.

## 📋 Funcionalidades Implementadas

### ✅ CRUD Completo de Tarefas
- **POST** `/tarefas` - Criar nova tarefa
- **GET** `/tarefas` - Listar tarefas com filtros
- **GET** `/tarefas/{id}` - Buscar tarefa por ID
- **PUT** `/tarefas/{id}/iniciar` - Iniciar tarefa
- **PUT** `/tarefas/{id}/concluir` - Concluir tarefa
- **DELETE** `/tarefas/{id}` - Excluir tarefa

### ✅ Filtros Avançados
- **Status**: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`
- **Prioridade**: `BAIXA`, `MEDIA`, `ALTA`
- **Data de criação**: Intervalo personalizado
- **Ordenação**: Por `dataCriacao` (padrão) ou `prioridade`

### ✅ Regras de Negócio
- Tarefas criadas iniciam automaticamente como `PENDENTE`
- `dataCriacao` gerada automaticamente no servidor
- Prioridade é campo obrigatório na criação
- Apenas tarefas `PENDENTES` podem ser iniciadas
- Tarefas concluídas registram `dataConclusao` automaticamente
- Validações de estado (não iniciar já iniciada, não concluir já concluída)

## 🏗️ Arquitetura Técnica
src/main/java/com/techtest/gerenciador_tarefas/
├── config/ # Configurações (Swagger)
├── controller/ # Controladores REST (@RestController)
├── service/ # Lógica de negócio (@Service)
├── repository/ # Persistência (JpaRepository)
├── model/ # Entidades JPA (@Entity)
├── DTO/ # Objetos de Transferência de Dados
├── mapper/ # Conversores DTO ↔ Entity
├── specification/ # Filtros dinâmicos (Specification)
└── globalExceptionHandler/ # Tratamento de erros padronizado


## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database** (em memória)
- **SpringDoc OpenAPI 3** (Swagger UI)
- **Maven** (Gerenciamento de dependências)
- **JUnit 5** (Testes unitários)

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Git (opcional)

### Passo 1: Clone o repositório
git clone https://github.com/JONATH-OLIVEIRA/tech-desafio-gerenciamento-tarefas.git
cd tech-desafio-gerenciamento-tarefas


### Passo 2: Execute a aplicacao
# Opção 1: Usando Maven
mvn spring-boot:run

# Opção 2: Executando o JAR
mvn clean package
java -jar target/gerenciador_tarefas-0.0.1-SNAPSHOT.jar

### Passo 3: Acesse os endpoints
A aplicação estará disponível em: http://localhost:8080

### Documentação da API
Swagger UI 
Acesse: http://localhost:8080/swagger-ui.html

### Endpoints Disponíveis

1. Criar Tarefa
http
POST /tarefas
Content-Type: application/json

{
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar conceitos de Spring Data JPA",
  "prioridade": "ALTA"
}

2. Listar Tarefas (com filtros)
http
GET /tarefas?status=PENDENTE&prioridade=ALTA&dataInicio=01/01/2024&dataFim=31/12/2024&ordenarPor=prioridade
Parâmetros de consulta:

status (opcional): PENDENTE, EM_ANDAMENTO, CONCLUIDA

prioridade (opcional): BAIXA, MEDIA, ALTA

dataInicio (opcional): Formato dd/MM/yyyy

dataFim (opcional): Formato dd/MM/yyyy

ordenarPor (opcional): dataCriacao (padrão) ou prioridade

3. Iniciar Tarefa
http
PUT /tarefas/{id}/iniciar

4. Concluir Tarefa
http
PUT /tarefas/{id}/concluir

6. Buscar Tarefa por ID
http
GET /tarefas/{id}

7. Excluir Tarefa
http

DELETE /tarefas/{id}

### Banco de Dados
# ===============================
# H2 DATABASE
# ===============================
spring.datasource.url=jdbc:h2:mem:gerenciador-tarefas
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# ===============================
# H2 CONSOLE
# ===============================
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

sql
CREATE TABLE tarefas (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    prioridade VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_conclusao TIMESTAMP
);
### TESTES
mvn test

### Decisões Técnicas

### 1. Spring Data JPA Specifications
Optei por usar Specifications para os filtros dinâmicos em vez de Query Methods, pois:

Oferece maior flexibilidade para combinações de filtros

Mantém o código mais organizado e extensível

Segue o padrão Specification do Domain-Driven Design

### 2. Tratamento de Erros Centralizado
Implementei um @RestControllerAdvice para:

Padronizar todas as respostas de erro

Separar preocupações (separação do tratamento de erros da lógica de negócio)

Facilitar manutenção e logging

### 3. DTO Pattern
Separei as entidades JPA dos DTOs para:

Controlar quais campos são expostos na API

Evitar expor detalhes de implementação

Flexibilidade para evolução da API sem impactar o modelo de domínio

### 4. H2 em Memória
Escolha baseada nos requisitos:

Atende à especificação "persistência em memória"

Fácil configuração e execução

Ideal para desenvolvimento e testes

### Estrutura do Projeto
.
├── src/
│   ├── main/
│   │   ├── java/com/techtest/gerenciador_tarefas/
│   │   │   ├── config/
│   │   │   │   └── SwaggerConfig.java
│   │   │   ├── controller/
│   │   │   │   └── TarefaController.java
│   │   │   ├── service/
│   │   │   │   └── TarefaService.java
│   │   │   ├── repository/
│   │   │   │   ├── TarefaRepository.java
│   │   │   │   └── specification/
│   │   │   │       └── TarefaSpecification.java
│   │   │   ├── model/
│   │   │   │   ├── Tarefa.java
│   │   │   │   ├── StatusTarefa.java
│   │   │   │   └── PrioridadeTarefa.java
│   │   │   ├── DTO/
│   │   │   │   └── TarefaDTO.java
│   │   │   ├── mapper/
│   │   │   │   └── TarefaMapper.java
│   │   │   └── globalExceptionHandler/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── OperacaoInvalidaException.java
│   │   │       ├── TarefaNaoEncontradaException.java
│   │   │       └── DTO/
│   │   │           └── ApiErrorDTO.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql (opcional)
│   └── test/
│       └── java/com/techtest/gerenciador_tarefas/
│           └── service/
│               └── TarefaServiceTest.java
├── pom.xml
└── README.md

### Considerações Finais
-Pontos Fortes da Implementação
-Código limpo e organizado seguindo as melhores práticas do Spring

-Documentação completa com Swagger/OpenAPI

-Tratamento robusto de erros com respostas padronizadas

-Filtros dinâmicos flexíveis usando JPA Specifications

-Separação clara de responsabilidades entre camadas

### Possíveis Melhorias Futuras
-Autenticação e autorização (Spring Security)

-Cache para melhor performance

-Paginação nos endpoints de listagem

-Logging mais detalhado com níveis diferentes

-Deploy em container (Docker)

### Contato
Desenvolvedor: Jonath Oliveira.

LinkedIn: https://www.linkedin.com/in/jonatholiveira/

GitHub: JONATH-OLIVEIRA
