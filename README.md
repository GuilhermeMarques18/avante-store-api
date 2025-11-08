🛍️ Descrição do projeto

A Avante Store API é uma API RESTful construída com Java + Spring Boot para gerenciamento de categorias e produtos de uma loja. Implementa CRUD, busca avançada, paginação e soft-delete (remoção lógica).
Observação: a aplicação roda apenas localmente (porta 8081).

🧰 Tecnologias

Java 17

Spring Boot 3.5.7

Spring Data JPA

PostgreSQL

Spring Web

Jakarta Validation

Lombok

Maven

⚙️ Instruções (rodando localmente)
Pré-requisitos

Java 17+

Maven 3.8+

PostgreSQL

Git

Passos
# clonar
git clone https://github.com/seu-usuario/avante-store-api.git

# abrir pasta do projeto
cd avante-store-api

# configurar src/main/resources/application.properties (exemplo abaixo)

# rodar
mvn spring-boot:run


Aplicação disponível em: http://localhost:8081

🌍 Variáveis / application.properties (exemplo)

Coloque em src/main/resources/application.properties:

server.port=8081
spring.application.name=avante-store-api

spring.datasource.url=jdbc:postgresql://localhost:5432/<nome-do-banco>
spring.datasource.username=<usuario>
spring.datasource.password=<senha>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

📘 Endpoints (local)

Base: http://localhost:8081

Categorias (/categorias)

Listar (paginado)
GET http://localhost:8081/categorias?page=0&size=5

Buscar por ID
GET http://localhost:8081/categorias/1

Busca avançada por nome
GET http://localhost:8081/categorias/busca?nome=eletronicos&page=0&size=5

Criar (POST)
POST http://localhost:8081/categorias
Body:

{
  "nome": "Eletrônicos",
  "descricao": "Produtos tecnológicos"
}


Atualizar (PUT)
PUT http://localhost:8081/categorias/1
Body:

{
  "nome": "Eletrônicos Atualizados",
  "descricao": "Descrição nova"
}


Deletar (soft delete)
DELETE http://localhost:8081/categorias/1

Produtos (/produtos)

Listar (paginado)
GET http://localhost:8081/produtos?page=0&size=5

Buscar por ID
GET http://localhost:8081/produtos/1

Busca avançada (nome, preço mínimo, categoria, paginação)
GET http://localhost:8081/produtos/busca?nome=celular&precoMin=100&categoriaId=1&page=0&size=5

Criar (POST)
POST http://localhost:8081/produtos
Body:

{
  "nome": "Celular",
  "descricao": "Smartphone 2",
  "preco": 500.00,
  "categoria": {
    "id": 1
  }
}


Atualizar (PUT)
PUT http://localhost:8081/produtos/1
Body:

{
  "nome": "Celular Atualizado",
  "descricao": "Smartphone básico atualizado",
  "preco": 600.00,
  "categoria": {"id": 1}
}


Deletar (soft delete)
DELETE http://localhost:8081/produtos/1

📄 Exemplos com curl

Listar categorias (página 0, size 5)

curl "http://localhost:8081/categorias?page=0&size=5"


Criar categoria

curl -X POST "http://localhost:8081/categorias" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Eletrônicos","descricao":"Produtos tecnológicos"}'


Busca avançada produto

curl "http://localhost:8081/produtos/busca?nome=celular&precoMin=100&categoriaId=1&page=0&size=5"

🧾 Documentação (Swagger)

A aplicação ainda não tem o Swagger ativado por padrão. Para adicionar a interface automática, inclua no pom.xml:

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.5.0</version>
</dependency>


Depois de adicionar e rebuildar, a UI estará em: http://localhost:8081/swagger-ui.html

Se quiser, eu gero o trecho do pom.xml já pronto e a classe de configuração (se necessário).

☁️ Deploy

No momento não há deploy — apenas execução local. Sugestões de hosts para futuro deploy: Render, Railway, Heroku (buildpacks), AWS, Google Cloud.

🧑‍💻 Autor
Guilherme Marques Ramos de Souza

Guilherme Marques
📧 seuemail@exemplo.com
