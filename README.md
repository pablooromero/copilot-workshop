# 🦈 Aria - API REST de Excusas Tech

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.8-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://img.shields.io/badge/Build-Maven-red)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![AI](https://img.shields.io/badge/AI-Google%20Gemini-yellow)](https://ai.google.dev/)

> **API REST creativa y técnicamente sólida** que genera excusas tech mezclando **fragmentos**, **memes argentinos**, **leyes del caos developer** y **axiomas universales del mundo IT**. Desarrollado con GitHub Copilot para el Java Sharks Challenge.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#️-tecnologías)
- [Arquitectura](#-arquitectura)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Endpoints](#-endpoints)
- [Configuración](#️-configuración)
- [Docker](#-docker)
- [Testing](#-testing)
- [Documentación](#-documentación)
- [Nivel Alcanzado](#-nivel-alcanzado-challenge)
- [Contribuir](#-contribuir)
- [Licencia](#-licencia)

---

## ✨ Características

### 🎯 Core Features
- ✅ **Generación de excusas tech aleatorias** con 4 fragmentos (contexto, causa, consecuencia, recomendación)
- ✅ **Reproducibilidad con seed** - Misma excusa con el mismo seed
- ✅ **Excusa del día** - La misma excusa durante 24 horas
- ✅ **Filtrado por roles** (DEV, QA, DEVOPS, PM, SRE, ALL)
- ✅ **Memes tech argentinos** - Tano Pasman, Maradona, Messi y más
- ✅ **Leyes y axiomas IT** - Murphy, Hofstadter, Dilbert, DevOps Principles, Axiomas del Dev

### 🤖 AI Integration (Nivel Megalodon)
- ✅ **Google Gemini AI** (gemini-1.5-flash) para generación creativa
- ✅ **3 niveles de creatividad**: LOW (0.3), MEDIUM (0.8), HIGH (1.2)
- ✅ **Retry logic** con exponential backoff (3 intentos)
- ✅ **Fallback automático** a generación tradicional si la API falla
- ✅ **Contexto del dominio** en el prompt (fragmentos, memes, leyes)

### 🏗️ Arquitectura y Calidad
- ✅ **Arquitectura Hexagonal** (Ports & Adapters)
- ✅ **Clean Code** con principios SOLID
- ✅ **64 tests unitarios** (100% de éxito)
- ✅ **Manejo robusto de excepciones** con GlobalExceptionHandler
- ✅ **Logging con SLF4J** en todos los layers
- ✅ **JavaDoc completo** en Controllers y Services
- ✅ **Docker-ready** con multi-stage build

### 📚 CRUD Completo
- ✅ Fragmentos (contexto, causa, consecuencia, recomendación)
- ✅ Memes tech argentinos
- ✅ Leyes y axiomas IT
- ✅ Validación con Jakarta Validation
- ✅ DTOs separados (Request/Response)

---

## 🛠️ Tecnologías

### Backend
- **Java 17** (Eclipse Temurin)
- **Spring Boot 3.2.8**
  - Spring Web (REST API)
  - Spring Data JPA (Persistencia)
  - Spring Validation (Validación de datos)
  - Spring Actuator (Health checks)
- **Maven 3.9.9** (Build tool)

### Base de Datos
- **H2 Database** (In-memory)
- Carga inicial automática desde JSONs (CommandLineRunner)
- 224 entidades precargadas:
  - 40 fragmentos
  - 87 memes argentinos
  - 97 leyes/axiomas IT

### AI & APIs
- **Google Gemini AI** (gemini-1.5-flash)
- **OkHttp3 4.12.0** (Cliente HTTP)
- **Gson** (JSON parsing)
- **WebFlux** (Llamadas asíncronas)

### Documentación & Tools
- **Springdoc OpenAPI 2.2.0** (Swagger UI)
- **Lombok 1.18.34** (Reducción de boilerplate)
- **PlantUML** (Diagramas UML)
- **JavaDoc** (Documentación de código)

### Testing
- **JUnit 5** (Framework de testing)
- **Mockito** (Mocking)
- **64 tests unitarios** con cobertura completa

### DevOps
- **Docker** + **Docker Compose**
- **GitHub Actions Ready**
- Multi-stage build optimizado (~200MB)

---

## 🏛️ Arquitectura

### Patrón Hexagonal (Ports & Adapters)

```
┌─────────────────────────────────────────────────────────┐
│                   ENTRADA (HTTP)                        │
│  Controllers (Adaptadores REST) - Puerto HTTP          │
│  ├── ExcuseController                                   │
│  ├── FragmentController                                 │
│  ├── MemeController                                     │
│  └── LawController                                      │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│              DTOs (Contratos de API)                    │
│  Request/Response separados + Validación               │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│         DOMINIO (Lógica de Negocio)                    │
│  Services (Casos de Uso)                               │
│  ├── ExcuseService (Generación + AI)                   │
│  ├── FragmentService                                    │
│  ├── MemeService                                        │
│  ├── LawService                                         │
│  └── GeminiService (Integración AI)                    │
└─────────────────┬───────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────┐
│         SALIDA (Persistencia + AI)                     │
│  Repositories (Adaptadores JPA) - Puerto BD           │
│  ├── FragmentRepository                                │
│  ├── MemeRepository                                     │
│  └── LawRepository                                      │
│                                                         │
│  AI Client (Adaptador HTTP) - Puerto Gemini           │
│  └── GeminiService + OkHttpClient                      │
└─────────────────────────────────────────────────────────┘
```

### Principios SOLID Aplicados

- **S**ingle Responsibility: Cada clase tiene una única razón para cambiar
- **O**pen/Closed: Extensible mediante herencia y composition
- **L**iskov Substitution: Interfaces JpaRepository permiten sustituir implementaciones
- **I**nterface Segregation: DTOs específicos por operación (Request vs Response)
- **D**ependency Inversion: Inyección por constructor, dependencias en abstracciones

---

## 📦 Requisitos

### Para Ejecución Local
- **Java 17+** ([Descargar JDK](https://adoptium.net/))
- **Maven 3.9+** ([Descargar Maven](https://maven.apache.org/download.cgi))
- **Google Gemini API Key** ([Obtener aquí](https://aistudio.google.com/app/apikey))

### Para Ejecución con Docker
- **Docker 20.10+** ([Descargar Docker](https://www.docker.com/get-started))
- **Docker Compose 2.0+** (incluido con Docker Desktop)
- **Google Gemini API Key** ([Obtener aquí](https://aistudio.google.com/app/apikey))

---

## 🚀 Instalación

### Opción 1: Ejecución Local

```bash
# 1. Clonar el repositorio
git clone https://github.com/pablooromero/copilot-workshop.git
cd copilot-workshop/aria

# 2. Configurar API Key de Gemini
export GEMINI_API_KEY="tu_api_key_aqui"
# Windows: set GEMINI_API_KEY=tu_api_key_aqui

# 3. Compilar el proyecto
mvn clean package -DskipTests

# 4. Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 2: Docker (Recomendado)

```bash
# 1. Clonar el repositorio
git clone https://github.com/pablooromero/copilot-workshop.git
cd copilot-workshop/aria

# 2. Configurar API Key
export GEMINI_API_KEY="tu_api_key_aqui"

# 3. Levantar con Docker Compose
docker-compose up -d

# 4. Ver logs
docker-compose logs -f
```

---

## 💻 Uso

### Verificar que la aplicación está corriendo

```bash
# Health check
curl http://localhost:8080/actuator/health

# Respuesta esperada:
# {"status":"UP"}
```

### Ejemplos de uso rápido

```bash
# Excusa aleatoria simple
curl http://localhost:8080/api/excuses/random

# Excusa del día (misma durante 24hs)
curl http://localhost:8080/api/excuses/daily

# Excusa por rol específico
curl http://localhost:8080/api/excuses/role/DEV

# Excusa con meme argentino
curl http://localhost:8080/api/excuses/meme

# Excusa con ley/axioma IT
curl http://localhost:8080/api/excuses/law

# Modo ULTRA SHARK (meme + ley)
curl http://localhost:8080/api/excuses/ultra?role=DEVOPS

# Excusa generada por AI (creatividad media)
curl "http://localhost:8080/api/excuses/ai/random?role=QA"

# Excusa AI ULTRA CREATIVA (HIGH + meme + ley)
curl "http://localhost:8080/api/excuses/ai/creative?role=DEV&context=servidor+caido"
```

### Respuesta de ejemplo

```json
{
  "contexto": "Durante el sprint review del viernes",
  "causa": "el pipeline de CI/CD falló por un timeout",
  "consecuencia": "tuvimos que hacer rollback a producción",
  "recomendacion": "implementar health checks más robustos",
  "meme": {
    "id": 42,
    "character": "Tano Pasman",
    "description": "¡¿CÓMO QUE FALLÓ EL DEPLOY?!"
  },
  "law": {
    "id": 15,
    "name": "Primera Ley de Murphy",
    "description": "Si algo puede salir mal, saldrá mal durante la demo",
    "category": "MURPHY"
  }
}
```

---

## 🌐 Endpoints

### 📊 Swagger UI
Accede a la documentación interactiva completa:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### 🎲 Generación de Excusas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/excuses/random` | Excusa aleatoria (soporta `?seed=123&role=DEV`) |
| GET | `/api/excuses/daily` | Excusa del día (misma durante 24hs) |
| GET | `/api/excuses/role/{role}` | Excusa filtrada por rol específico |
| GET | `/api/excuses/meme` | Excusa + meme tech argentino |
| GET | `/api/excuses/law` | Excusa + ley/axioma IT |
| GET | `/api/excuses/ultra` | Excusa ULTRA SHARK (meme + ley) |

### 🤖 Generación con AI (Google Gemini)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/excuses/ai` | Control total (rol, contexto, creatividad, meme, ley) |
| GET | `/api/excuses/ai/random` | Excusa rápida con creatividad MEDIUM |
| GET | `/api/excuses/ai/creative` | ULTRA AI SHARK (HIGH + meme + ley) |

**Ejemplo POST /api/excuses/ai**:
```json
{
  "role": "DEVOPS",
  "context": "servidor de producción caído",
  "creativity": "HIGH",
  "includeMeme": true,
  "includeLaw": true
}
```

### 📝 CRUD de Fragmentos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/fragments` | Listar todos los fragmentos |
| GET | `/api/fragments/{id}` | Obtener fragmento por ID |
| GET | `/api/fragments/type/{type}` | Filtrar por tipo (CONTEXTO, CAUSA, etc.) |
| GET | `/api/fragments/role/{role}` | Filtrar por rol (DEV, QA, etc.) |
| POST | `/api/fragments` | Crear nuevo fragmento |
| PUT | `/api/fragments/{id}` | Actualizar fragmento existente |
| DELETE | `/api/fragments/{id}` | Eliminar fragmento |

### 🎭 CRUD de Memes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/memes` | Listar todos los memes |
| GET | `/api/memes/{id}` | Obtener meme por ID |
| POST | `/api/memes` | Crear nuevo meme |
| PUT | `/api/memes/{id}` | Actualizar meme existente |
| DELETE | `/api/memes/{id}` | Eliminar meme |

### 📚 CRUD de Leyes/Axiomas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/laws` | Listar todas las leyes |
| GET | `/api/laws/{id}` | Obtener ley por ID |
| GET | `/api/laws/category/{category}` | Filtrar por categoría (MURPHY, HOFSTADTER, etc.) |
| POST | `/api/laws` | Crear nueva ley |
| PUT | `/api/laws/{id}` | Actualizar ley existente |
| DELETE | `/api/laws/{id}` | Eliminar ley |

### 💚 Health & Monitoring

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/actuator/health` | Estado de salud de la aplicación |
| GET | `/h2-console` | Consola de H2 Database |

**H2 Console**:
- URL: `jdbc:h2:mem:excusesdb`
- Usuario: `sa`
- Password: *(vacío)*

---

## ⚙️ Configuración

### Variables de Entorno

#### Requeridas
| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `GEMINI_API_KEY` | **REQUERIDO** - API key de Google Gemini AI | `your_api_key_here` |

#### Opcionales (AI Configuration)
| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `GEMINI_TEMPERATURE` | Creatividad AI (0.0-2.0) | `0.8` |
| `GEMINI_MAX_TOKENS` | Tokens máximos respuesta | `1024` |
| `GEMINI_API_TIMEOUT` | Timeout en segundos | `30` |
| `GEMINI_RETRY_MAX_ATTEMPTS` | Reintentos en fallo | `3` |

#### Opcionales (Spring Boot)
| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil de Spring Boot | `default` |
| `JAVA_OPTS` | Opciones JVM | `-Xms256m -Xmx512m` |

### Archivo de Configuración

Puedes crear un archivo `.env` en la raíz del proyecto:

```bash
# .env
GEMINI_API_KEY=AIzaSyD...tu_api_key_real...
GEMINI_TEMPERATURE=0.8
GEMINI_MAX_TOKENS=1024
```

---

## 🐳 Docker

### Build de Imagen

```bash
# Build manual
docker build -t aria:latest .

# O usar script
./docker-build.sh        # Linux/Mac
docker-build.bat         # Windows
```

### Ejecutar Contenedor

```bash
# Con Docker Compose (RECOMENDADO)
docker-compose up -d

# Con Docker Run
docker run -d \
  -p 8080:8080 \
  -e GEMINI_API_KEY="tu_api_key" \
  --name aria-api \
  aria:latest
```

### Comandos Útiles

```bash
# Ver logs
docker-compose logs -f

# Detener
docker-compose down

# Rebuild
docker-compose up -d --build

# Estado
docker-compose ps

# Entrar al contenedor
docker exec -it aria-api sh
```

**📖 Documentación completa**: Ver [README_DOCKER.md](README_DOCKER.md)

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=ExcuseServiceTest

# Con coverage (requiere plugin)
mvn test jacoco:report
```

### Cobertura de Tests

- **64 tests unitarios** (100% de éxito)
- **Controllers**: ExcuseController, FragmentController, MemeController, LawController
- **Services**: ExcuseService, FragmentService, MemeService, LawService
- **Mappers**: FragmentMapper, MemeMapper, LawMapper
- **Test de reproducibilidad con seed**: ✅ Implementado

### Ejemplo de Test

```java
@Test
void generateExcuse_withSeed_shouldBeReproducible() {
    Long seed = 12345L;
    
    ExcuseResponseDTO excuse1 = excuseService.generateExcuse(seed, Role.DEV);
    ExcuseResponseDTO excuse2 = excuseService.generateExcuse(seed, Role.DEV);
    
    assertEquals(excuse1.getContexto(), excuse2.getContexto());
    assertEquals(excuse1.getCausa(), excuse2.getCausa());
}
```

---

## 📚 Documentación

### Documentación Técnica

- **[README_AI.md](README_AI.md)** - Integración con Google Gemini AI
- **[README_DOCKER.md](README_DOCKER.md)** - Guía completa de Docker
- **[README_Excusas_Sharks.md](README_Excusas_Sharks.md)** - Consigna original del challenge

### Diagramas UML (PlantUML)

Ubicados en `/docs/uml/`:
- ✅ **class-diagram.puml** - Diagrama de clases del dominio
- ✅ **component-diagram.puml** - Componentes y dependencias
- ✅ **deployment-diagram.puml** - Arquitectura de deployment
- ✅ **sequence-excuse-generation.puml** - Flujo de generación de excusas

### JavaDoc

Documentación completa en código:
- Todos los métodos públicos de Controllers documentados
- Todos los métodos públicos de Services documentados
- Incluye `@param`, `@return`, `@throws`

Generar HTML:
```bash
mvn javadoc:javadoc
# Ver en: target/site/apidocs/index.html
```

### Datos de Ejemplo

JSON de carga inicial en `/docs/json/`:
- `murphy.json` - Leyes de Murphy
- `hofstadter.json` - Leyes de Hofstadter
- `dilbert.json` - Principio de Dilbert
- `devops_principles.json` - Principios DevOps
- `dev_axioms.json` - Axiomas del desarrollo
- `memes_argentinos.json` - Memes tech argentinos
- `argento-memes.json` - Más memes argentinos
- `dev-memes.json` - Memes de developers

---

## 🏆 Nivel Alcanzado (Challenge)

### ✅ Level Mojarrita (Completado)
- ✅ API funcional + Swagger
- ✅ Generador de excusas coherente y reproducible
- ✅ Persistencia simple (H2)
- ✅ Tests mínimos (64 tests)
- ✅ Código limpio + linter

### ✅ Level Delfín (Completado)
- ✅ Calidad del diseño (Arquitectura Hexagonal)
- ✅ Patrones de diseño (Builder, Strategy, Factory)
- ✅ Clean Code (SOLID, DRY, KISS)
- ✅ Conventional commits

### ✅ Level Shark (Completado)
- ✅ 64 tests unitarios (100% éxito)
- ✅ SLF4J logging implementado
- ✅ PlantUML: 4 diagramas (Clases, Componentes, Deploy, Secuencia)
- ✅ JavaDoc completo en Controllers y Services
- ✅ Manejo robusto de excepciones

### ✅ Level White Shark (Completado)
- ✅ Docker + Docker Compose
- ✅ Multi-stage build optimizado
- ✅ Arquitectura Hexagonal implementada
- ✅ Health checks configurados

### ✅ Level Megalodon (Completado)
- ✅ **Integración con Google Gemini AI** (gemini-1.5-flash)
- ✅ 3 niveles de creatividad
- ✅ Retry logic con exponential backoff
- ✅ Fallback a generación tradicional
- ✅ Contexto del dominio en prompts

### 🌟 Extras Implementados
- ✅ Manejo de excepciones personalizado (GlobalExceptionHandler)
- ✅ Logging estructurado con SLF4J
- ✅ DTOs separados (Request/Response)
- ✅ Mappers estáticos
- ✅ Spring Boot Actuator
- ✅ Validación con Jakarta Validation
- ✅ Scripts de utilidad (build/run)
- ✅ Documentación exhaustiva

---

## 🤝 Contribuir

### Cómo Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios con **Conventional Commits** (`git commit -m 'feat: add amazing feature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Conventional Commits

Formato: `<tipo>(<scope>): <descripción>`

Tipos:
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Formato, punto y coma faltante, etc
- `refactor`: Refactorización de código
- `test`: Agregar tests
- `chore`: Actualización de build tasks, etc

Ejemplos:
```bash
feat(ai): add Google Gemini integration
fix(service): resolve null pointer in excuse generation
docs(readme): update installation instructions
test(controller): add tests for ExcuseController
```

---

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 👥 Autor

**Pablo Romero**
- GitHub: [@pablooromero](https://github.com/pablooromero)
- Proyecto: [copilot-workshop](https://github.com/pablooromero/copilot-workshop)

---

## 🙏 Agradecimientos

- **Accenture Java Sharks Team** - Por el challenge original
- **GitHub Copilot** - Desarrollo asistido por IA
- **Google Gemini AI** - Integración de IA generativa
- **Spring Boot Community** - Framework robusto y bien documentado
- **PlantUML** - Herramienta de diagramación

---

## 📞 Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa la [documentación completa](README_AI.md)
2. Consulta los [issues existentes](https://github.com/pablooromero/copilot-workshop/issues)
3. Abre un [nuevo issue](https://github.com/pablooromero/copilot-workshop/issues/new)

---

<div align="center">


Si algo sale mal... ¡ahora tenés una API completa para explicarlo! 🦈💻😅

</div>
