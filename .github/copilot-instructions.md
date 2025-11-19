# Instrucciones para GitHub Copilot - Proyecto Aria (Excusas Tech API)

## Arquitectura y Contexto

Aria es una **API REST de Excusas Tech** (Java Sharks Challenge) que genera excusas técnicas divertidas combinando fragmentos, memes y leyes del mundo IT. Construido con Spring Boot 3.2.8 y Java 17. La arquitectura sigue el patrón **Hexagonal (Ports & Adapters)**, separando el dominio de la infraestructura, con H2 como base de datos en memoria.

**Principios fundamentales**:
- **Arquitectura Hexagonal**: Dominio independiente de frameworks e infraestructura
- **Clean Code**: Código legible, autodocumentado y mantenible
- **KISS (Keep It Simple, Stupid)**: Soluciones simples antes que complejas
- **SOLID**: Aplicado consistentemente en toda la arquitectura
- **JPA**: Spring Data JPA para persistencia con H2 en memoria
- **Lombok**: Usado en TODAS las clases (entidades y DTOs) para reducir boilerplate

## Estructura de Paquetes (Hexagonal)

```
com.accenture.aria/
├── controller/     # Adaptadores de entrada (REST) - Puerto HTTP
├── dto/           # Contratos de API (Request/Response con Lombok: @Data, @Builder)
├── model/         # Dominio - Entidades de negocio (@Entity con Lombok: @Getter, @Setter, @Builder)
├── repository/    # Adaptadores de salida - Puerto de Persistencia (JpaRepository)
└── service/       # Núcleo del dominio - Casos de uso y lógica de negocio + Mappers
```

**Flujo Hexagonal**:
```
HTTP Request → Controller (Adapter) → Service (Domain) → Repository (Adapter) → Database
             ↓                        ↓                   ↓
            DTOs              Lógica de Negocio      Persistencia JPA
```

**SOLID aplicado**:
- **S**ingle Responsibility: Cada clase tiene una única razón para cambiar (Controllers solo manejan HTTP, Services solo lógica de negocio)
- **O**pen/Closed: Extensible mediante herencia de repositories y composition en services
- **L**iskov Substitution: Interfaces JpaRepository permiten sustituir implementaciones
- **I**nterface Segregation: DTOs específicos por operación (Request vs Response)
- **D**ependency Inversion: Inyección por constructor, dependemos de abstracciones (Repository interfaces)

## Patrones Críticos del Proyecto

### 1. Relaciones Entity-DTO con IDs
**NO uses entidades anidadas en RequestDTOs**. Usa IDs para referencias:

```java
// ✅ CORRECTO - FragmentRequestDTO
private FragmentType type;
private String text;
private Role role;

// ❌ INCORRECTO - No uses entidades anidadas en Request
private Fragment related;
```

**Los ResponseDTOs SÍ contienen objetos anidados** completos cuando corresponde:
```java
// ExcuseResponseDTO incluye objetos completos
private MemeResponseDTO meme;
private LawResponseDTO law;
```

### 2. Mappers Estáticos con Constructor Privado
Todos los mappers son clases utilitarias con constructor privado:

```java
public class FragmentMapper {
    private FragmentMapper() {} // Evita instanciación
    
    public static Fragment toEntity(FragmentRequestDTO dto) { ... }
    public static FragmentResponseDTO toResponse(Fragment f) { ... }
}
```

### 3. Service Layer - Dos Métodos por Operación
Los servicios exponen dos métodos para crear/actualizar:

```java
// Para uso directo con entidades
public Fragment create(Fragment fragment) { ... }

// Para uso desde controllers con DTOs - resuelve relaciones por ID
public Fragment createFromDTO(FragmentRequestDTO dto) {
    Fragment fragment = FragmentMapper.toEntity(dto);
    // Aplicar validaciones de negocio si es necesario
    return create(fragment);
}
```

**Controllers DEBEN usar métodos `*FromDTO`** para manejar correctamente las relaciones.

### 4. Entidades Simples (Sin Relaciones JPA)
En este proyecto, las entidades son simples y NO tienen relaciones `@ManyToOne` o `@OneToMany`:
- `Fragment`, `Meme`, `Law` son independientes
- Las **Excusas NO se persisten**, se generan on-the-fly combinando fragmentos

Los mappers manejan objetos null de forma segura.

### 5. Generación Aleatoria con Seed (Reproducibilidad)
La lógica de generación de excusas debe soportar seeds para reproducibilidad:

```java
public Excuse generateExcuse(Long seed) {
    Random random = seed != null ? new Random(seed) : new Random();
    // Selección aleatoria de fragmentos
    Fragment contexto = selectRandomFragment(FragmentType.CONTEXTO, random);
    // ...
}
```

El endpoint `/excuses/daily` usa `LocalDate.now().toEpochDay()` como seed automático.

### 6. Actualización Parcial con Null-Safe
Los métodos `update` solo modifican campos no-null del DTO:

```java
if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
```

## Convenciones de Código

### Clean Code y Buenas Prácticas
- **Nombres descriptivos**: `createFromDTO()` mejor que `create2()`
- **Métodos pequeños**: Una función = una responsabilidad
- **No Magic Numbers**: Usar constantes o enums (`Status.OPEN` no `"OPEN"`)
- **DRY**: Extraer lógica repetida a métodos privados o utilities

### Uso de Lombok
- **Entidades (@Entity)**: Usar `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
- **DTOs**: Usar `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- **Mappers**: Constructor privado manual (no instanciables, sin Lombok)

### JPA y Persistencia
- **Enums**: Usar `@Enumerated(EnumType.STRING)` para persistencia legible
- **IDs**: Siempre `Long` con `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- **Timestamps**: `LocalDateTime` para `createdAt` / `updatedAt`
- **Relaciones**: `FetchType.LAZY` por defecto, evitar N+1 queries
- **Validaciones**: Jakarta Validation solo en RequestDTOs (`@NotBlank`, `@Size`, `@Email`)

### REST y Controllers
- **Responses HTTP**: Usar `ResponseEntity<T>` con códigos apropiados (201 Created, 404 Not Found)
- **Inyección de dependencias**: Siempre por constructor (no `@Autowired` en campos)
- **KISS**: Endpoints simples, lógica compleja en services

## Carga de Datos Inicial

Los datos se cargan desde JSONs en `/docs/json` mediante `CommandLineRunner`:
- `murphy.json`, `hofstadter.json`, `dilbert.json`, `devops_principles.json`, `dev_axioms.json` → tabla `laws`
- `memes_argentinos.json`, `argento-memes.json`, `dev-memes.json` → tabla `memes`
- Fragments se crean programáticamente en el loader

## Comandos de Desarrollo

```bash
# Compilar
mvn clean package

# Ejecutar aplicación
mvn spring-boot:run

# Acceder a H2 Console
# URL: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:excusesdb
# Usuario: sa, Password: (vacía)
```

## Enums del Dominio

```java
FragmentType: CONTEXTO, CAUSA, CONSECUENCIA, RECOMENDACION
Role: DEV, QA, DEVOPS, PM, ALL
LawCategory: MURPHY, HOFSTADTER, DILBERT, DEVOPS, AXIOM
```

## Modelo de Dominio

```java
Fragment: id, type (FragmentType), text, role (Role)
Meme: id, character, description
Law: id, name, description, category (LawCategory)
Excuse: contexto, causa, consecuencia, recomendacion (generado on-the-fly, NO persiste)
```

## Endpoints REST

Todos bajo `/api/{recurso}`:

**Generación de Excusas**:
- `/api/excuses/random` - Excusa aleatoria simple
- `/api/excuses/daily` - Excusa del día (misma cada 24hs)
- `/api/excuses/role/{role}` - Excusa filtrada por rol
- `/api/excuses/meme` - Excusa + meme
- `/api/excuses/law` - Excusa + ley/axioma
- `/api/excuses/ultra` - Excusa + meme + ley (modo ULTRA SHARK)

**CRUD de Recursos**:
- `/api/fragments` - CRUD de fragmentos
- `/api/memes` - CRUD de memes
- `/api/laws` - CRUD de leyes/axiomas

**Pattern**: `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}`

## Tests

Actualmente NO hay tests automatizados. Al crear tests:
- Preferir tests unitarios con Mockito para servicios
- Tests de integración con `@SpringBootTest` y H2
- Mockear repositorios en service tests
