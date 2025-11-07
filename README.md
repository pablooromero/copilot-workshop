# Ticket Manager (simple)

Proyecto Spring Boot (Maven) minimal para gestionar tickets (tipo Jira pero liviano).

Estructura:
- `src/main/java/com/accenture/ticketmanager` - código fuente
- `src/main/resources/application.properties` - configuración H2 en memoria

Dependencias principales:
- Spring Boot Web
- Spring Data JPA
- H2 Database
- Lombok (opcional, ya incluido)

Cómo ejecutar:

Requisitos: JDK 17+, Maven

Desde la raíz del proyecto (`ticket-manager`):

```bash
mvn spring-boot:run
```

O generar JAR:

```bash
mvn package
java -jar target/ticket-manager-0.0.1-SNAPSHOT.jar
```

API REST (ejemplos):
- `GET /api/tickets` - listar tickets
- `GET /api/tickets/{id}` - obtener ticket
- `POST /api/tickets` - crear ticket (envía JSON)
- `PUT /api/tickets/{id}` - actualizar
- `DELETE /api/tickets/{id}` - eliminar

H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ticketdb`)

Si quieres, agrego autenticación básica, DTOs y validaciones next.
