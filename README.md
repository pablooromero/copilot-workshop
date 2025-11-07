# Aria - Sistema de Gestión de Tickets

Sistema minimalista de gestión de tickets inspirado en Jira, construido con Spring Boot.

## Características

- CRUD completo de tickets
- Validación de datos con Jakarta Validation
- Base de datos H2 en memoria
- API REST con DTOs
- Soporte para prioridades y estados
- Documentación Swagger/OpenAPI (próximamente)

## Tecnologías

- Java 17
- Spring Boot 3.2.8
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Estructura del Proyecto

```
aria/
├── main/
│   ├── java/com/accenture/aria/
│   │   ├── controller/
│   │   │   └── TicketController.java
│   │   ├── dto/
│   │   │   ├── TicketRequestDTO.java
│   │   │   └── TicketResponseDTO.java
│   │   ├── model/
│   │   │   ├── Ticket.java
│   │   │   ├── Status.java
│   │   │   └── Priority.java
│   │   ├── repository/
│   │   │   └── TicketRepository.java
│   │   ├── service/
│   │   │   ├── TicketService.java
│   │   │   └── TicketMapper.java
│   │   └── AriaApplication.java
│   └── resources/
│       └── application.properties
├── pom.xml
└── .gitignore
```

## Requisitos

- JDK 17 o superior
- Maven 3.6+

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/accentureshark/aria.git
cd aria
```

2. Compilar el proyecto:
```bash
mvn clean package
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

O directamente el JAR:
```bash
java -jar target/aria-0.0.1-SNAPSHOT.jar
```

## API REST

### Endpoints

- `GET /api/tickets` - Listar todos los tickets
- `GET /api/tickets/{id}` - Obtener un ticket por ID
- `POST /api/tickets` - Crear nuevo ticket
- `PUT /api/tickets/{id}` - Actualizar ticket existente
- `DELETE /api/tickets/{id}` - Eliminar ticket

### Ejemplos de Uso

#### Crear un Ticket
```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Bug en login",
    "description": "Error al intentar iniciar sesión",
    "priority": "HIGH",
    "reporter": "juan@example.com"
  }'
```

#### Listar Tickets
```bash
curl http://localhost:8080/api/tickets
```

#### Actualizar Ticket
```bash
curl -X PUT http://localhost:8080/api/tickets/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "IN_PROGRESS",
    "assignee": "ana@example.com"
  }'
```

## Base de Datos

H2 Console disponible en: http://localhost:8080/h2-console

Configuración de conexión:
- JDBC URL: `jdbc:h2:mem:ticketdb`
- Usuario: `sa`
- Contraseña: (vacía)

## Estados y Prioridades

Estados disponibles:
- `OPEN`
- `IN_PROGRESS`
- `RESOLVED`
- `CLOSED`

Prioridades:
- `LOW`
- `MEDIUM`
- `HIGH`
- `URGENT`

## Desarrollo

### Compilar y Ejecutar Tests
```bash
mvn clean verify
```

### Crear build de producción
```bash
mvn clean package -Pprod
```

## Siguientes Pasos

- [ ] Agregar autenticación con Spring Security
- [ ] Implementar documentación con Swagger/OpenAPI
- [ ] Añadir más tests (unitarios y de integración)
- [ ] Soporte para comentarios en tickets
- [ ] Implementar búsqueda y filtros
- [ ] Frontend minimalista (React/Angular)
