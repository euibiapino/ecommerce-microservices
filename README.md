# E-Commerce Microservices Platform

Sistema de e-commerce desenvolvido com arquitetura de microserviços utilizando Spring Boot, RabbitMQ para mensageria assíncrona e comunicação REST entre serviços.

## 🏗️ Arquitetura
```
┌─────────────────┐      REST       ┌──────────────────┐
│   Storefront    │ ←──────────────→ │    Warehouse     │
│   (Vitrine)     │                  │    (Estoque)     │
└────────┬────────┘                  └────────┬─────────┘
         │                                    │
         │          ┌──────────────┐          │
         └─────────→│   RabbitMQ   │←─────────┘
                    └──────────────┘
```

### Serviços

- **Warehouse Service** (porta 8080): Gerencia produtos e estoque
- **Storefront Service** (porta 8081): Vitrine de produtos disponíveis
- **RabbitMQ** (porta 5672/15672): Message broker para comunicação assíncrona

## 🚀 Tecnologias

- **Backend**: Java 21, Spring Boot 3.x, Gradle
- **Banco de Dados**: H2 (in-memory)
- **Mensageria**: RabbitMQ
- **Containerização**: Docker, Docker Compose
- **Documentação API**: Swagger/OpenAPI
- **Mapeamento**: MapStruct
- **Persistência**: Spring Data JPA

## 📋 Pré-requisitos

- Docker & Docker Compose
- Java 21 (para desenvolvimento local)
- Gradle 8.x

## 🔧 Como Executar

### Opção 1: Docker Compose (Recomendado)
```bash
# Criar a rede Docker
docker network create ecommerce-net

# Subir todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar os serviços
docker-compose down
```

### Opção 2: Desenvolvimento Local
```bash
# Terminal 1 - Warehouse
cd warehouse-service
gradle bootRun

# Terminal 2 - Storefront
cd storefront-service
gradle bootRun

# RabbitMQ via Docker
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=admin \
  -e RABBITMQ_DEFAULT_PASS=admin \
  rabbitmq:4.0.5-management
```

## 📡 Endpoints

### Warehouse Service (http://localhost:8080/warehouse)

- `POST /products` - Criar produto
- `GET /products/{id}` - Buscar produto por ID
- `POST /products/{id}/purchase` - Registrar compra
- `POST /stocks` - Adicionar estoque
- `PUT /stocks/{id}/release` - Liberar estoque
- `DELETE /stocks/{id}/release` - Inativar estoque

### Storefront Service (http://localhost:8081/storefront)

- `POST /products` - Criar produto na vitrine
- `GET /products` - Listar produtos disponíveis
- `GET /products/{id}` - Detalhes do produto
- `POST /products/{id}/purchase` - Comprar produto

### Documentação API (Swagger)

- Warehouse: http://localhost:8080/warehouse/swagger-ui.html
- Storefront: http://localhost:8081/storefront/swagger-ui.html

### RabbitMQ Management

- Console: http://localhost:15672
- User: `admin`
- Password: `admin`

## 🗄️ Bancos de Dados H2

### Warehouse
- URL: http://localhost:8080/warehouse/h2-console
- JDBC URL: `jdbc:h2:mem:warehouse`
- Username: `sa`
- Password: (vazio)

### Storefront
- URL: http://localhost:8081/storefront/h2-console
- JDBC URL: `jdbc:h2:mem:storefront`
- Username: `sa`
- Password: (vazio)

## 🔄 Fluxo de Comunicação

1. **Cadastro de Produto**:
   - Produto é criado no Warehouse
   - Warehouse notifica Storefront via REST
   - Produto aparece na vitrine como inativo

2. **Liberação de Estoque**:
   - Estoque é liberado no Warehouse
   - Warehouse publica mensagem no RabbitMQ
   - Storefront consome mensagem e ativa produto

3. **Compra**:
   - Cliente compra no Storefront
   - Storefront chama Warehouse via REST
   - Warehouse decrementa estoque
   - Se estoque zerou, publica mensagem de indisponibilidade
   - Storefront atualiza status do produto

## 🛠️ Desenvolvimento

### Estrutura do Projeto
```
ecommerce-microservices/
├── warehouse-service/       # Serviço de estoque
├── storefront-service/      # Serviço de vitrine
├── shared/                  # Código compartilhado (futuro)
├── docs/                    # Documentação adicional
├── docker-compose.yml       # Orquestração de containers
└── README.md               # Este arquivo
```

### Debug Remoto

- Warehouse: porta 5005
- Storefront: porta 5006

## 📝 Notas Técnicas

- Ambos os serviços usam H2 in-memory (dados resetam ao reiniciar)
- RabbitMQ usa exchange direto com routing key
- Comunicação síncrona via RestClient
- MapStruct para conversão de DTOs
- Gradle com hot reload via DevTools

## 📄 Licença

Este projeto está sob a licença MIT.
