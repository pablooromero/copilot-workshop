# 🤖 Aria - Excusas Tech con IA (Google Gemini)

**API REST para generar excusas técnicas creativas** combinando fragmentos del dominio IT, memes argentinos y leyes del mundo tech. Ahora potenciado por **Google Gemini 1.5 Flash AI** para excusas contextuales y creativas.

---

## 🚀 Características

### Generación Tradicional (Determinística)
- ✅ Combina fragmentos pre-definidos de H2 Database
- ✅ 224 entidades cargadas (97 leyes + 87 memes + 40 fragmentos)
- ✅ Filtrado por rol (DEV, QA, DEVOPS, PM, SRE)
- ✅ Reproducible con seeds

### 🆕 Generación con IA (Google Gemini)
- 🤖 **Excusas creativas y contextuales** usando Gemini 1.5 Flash
- 🎨 **3 niveles de creatividad**: LOW (0.3), MEDIUM (0.8), HIGH (1.2)
- 🔄 **Fallback automático** a generación tradicional si API falla
- 🛡️ **Manejo robusto de errores** con reintentos exponenciales
- 📊 **Prompt engineering** con contexto del dominio (fragmentos, leyes, memes)

---

## 📦 Arquitectura

**Hexagonal Architecture (Ports & Adapters)**:
```
HTTP REST → Controller → Service (Domain) → GeminiService (AI Port) → Gemini API
                                          ↘ Repository (DB Port) → H2 Database
```

**Stack Técnico**:
- **Java 17** + **Spring Boot 3.2.8**
- **H2 Database** (in-memory)
- **Google Gemini 1.5 Flash API** (REST)
- **OkHttp3** (HTTP client)
- **Swagger/OpenAPI** (documentación)
- **JUnit 5 + Mockito** (64 tests)

---

## 🔑 Configuración de API Key

### 1️⃣ Obtener API Key de Google AI Studio

1. Ir a: [https://aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey)
2. Iniciar sesión con cuenta Google
3. Crear un nuevo proyecto (si no tienes)
4. Generar API Key
5. Copiar el key (ejemplo: `AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX`)

### 2️⃣ Configurar Variable de Entorno

**Windows (PowerShell)**:
```powershell
$env:GEMINI_API_KEY="AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```

**Linux/Mac (Bash)**:
```bash
export GEMINI_API_KEY="AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```

**O crear archivo `.env`** (no commitear):
```bash
cp .env.example .env
# Editar .env y agregar tu API key
```

---

## 🏃 Ejecutar la Aplicación

```bash
# Compilar
mvn clean package

# Ejecutar con API key
export GEMINI_API_KEY="tu_api_key_aqui"
mvn spring-boot:run

# O ejecutar JAR
java -jar target/aria-0.0.1-SNAPSHOT.jar
```

**Sin API Key** (solo generación tradicional):
```bash
mvn spring-boot:run
# Los endpoints AI harán fallback automático
```

**Acceder a**:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:excusesdb`
  - Usuario: `sa`, Password: (vacía)

---

## 🌐 Endpoints REST

### 📖 Generación Tradicional

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/excuses/random` | Excusa aleatoria simple |
| `GET` | `/api/excuses/daily` | Excusa del día (misma cada 24hs) |
| `GET` | `/api/excuses/role/{role}` | Excusa filtrada por rol |
| `GET` | `/api/excuses/meme` | Excusa + meme argentino |
| `GET` | `/api/excuses/law` | Excusa + ley/axioma IT |
| `GET` | `/api/excuses/ultra` | Excusa + meme + ley (ULTRA SHARK) |

### 🤖 Generación con IA (Google Gemini)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/excuses/ai` | **Control total**: rol, context, creatividad, meme, ley |
| `GET` | `/api/excuses/ai/random` | Excusa AI rápida (creatividad media) |
| `GET` | `/api/excuses/ai/creative` | **ULTRA AI SHARK**: Alta creatividad + meme + ley |

---

## 📝 Ejemplos de Uso

### 1️⃣ Excusa AI Básica (GET)

```bash
curl -X GET "http://localhost:8080/api/excuses/ai/random?role=DEV"
```

**Respuesta**:
```json
{
  "contexto": "Durante el hotfix crítico del viernes por la tarde",
  "causa": "la API de terceros decidió cambiar su contrato sin previo aviso",
  "consecuencia": "tuvimos que revertir el deploy y nuestro product owner tuvo un micro-infarto",
  "recomendacion": "Implementar contract testing con Pact para detectar breaking changes antes de producción"
}
```

### 2️⃣ Excusa AI Ultra Creativa con Context (GET)

```bash
curl -X GET "http://localhost:8080/api/excuses/ai/creative?role=DEVOPS&context=servidor%20de%20producción%20caído"
```

**Respuesta**:
```json
{
  "contexto": "En medio del Black Friday, con tráfico 10x normal",
  "causa": "un contenedor zombie consumió todos los recursos del nodo master",
  "consecuencia": "el auto-scaling no pudo levantar nuevas instancias y perdimos 2 horas de ventas",
  "recomendacion": "Configurar resource limits en Kubernetes y alertas proactivas de consumo anómalo",
  "meme": {
    "character": "Tano Pasman",
    "description": "Cuando ves el dashboard de Grafana totalmente en rojo"
  },
  "law": {
    "name": "Ley de Murphy #3",
    "description": "Si algo puede salir mal, saldrá mal en el peor momento posible",
    "category": "MURPHY"
  }
}
```

### 3️⃣ Excusa AI Personalizada (POST)

```bash
curl -X POST "http://localhost:8080/api/excuses/ai" \
  -H "Content-Type: application/json" \
  -d '{
    "role": "QA",
    "context": "test suite fallando en CI/CD",
    "creativity": "HIGH",
    "includeMeme": true,
    "includeLaw": false
  }'
```

**Respuesta**:
```json
{
  "contexto": "Ejecutando la suite de regresión en el pipeline de GitHub Actions",
  "causa": "un test flaky con TimeoutException decidió despertar de su letargo",
  "consecuencia": "el pipeline bloqueó el merge por 3 horas hasta que alguien hizo 'Retry' manualmente",
  "recomendacion": "Implementar TestContainers para ambientes reproducibles y eliminar sleeps hardcodeados",
  "meme": {
    "character": "Maradona",
    "description": "Cuando el test pasa en local pero falla en CI"
  }
}
```

---

## 🔧 Configuración Avanzada

### `application.properties`

```properties
# Google Gemini AI Configuration
gemini.api.key=${GEMINI_API_KEY:your_api_key_here}
gemini.temperature=0.8              # Creatividad (0.0 - 2.0)
gemini.max-tokens=1024              # Máximo tokens en respuesta
gemini.api.timeout=30               # Timeout en segundos
gemini.retry.max-attempts=3         # Reintentos en caso de fallo
```

**Ajustar creatividad**:
- `0.3` (LOW): Predecible, técnico, formal
- `0.8` (MEDIUM): Balanceado, creativo pero coherente
- `1.2` (HIGH): Muy creativo, humorístico, innovador

---

## 🔀 Diferencias: Tradicional vs AI

| Característica | Tradicional | AI (Gemini) |
|----------------|-------------|-------------|
| **Fuente de datos** | DB H2 (fragmentos fijos) | Generación dinámica por IA |
| **Creatividad** | Baja (combinaciones limitadas) | Alta (contexto + creatividad configurable) |
| **Consistencia** | Reproducible con seeds | Variabilidad controlada por temperature |
| **Latencia** | <50ms | ~1-3s (llamada API externa) |
| **Dependencias** | Solo DB local | Requiere API key + internet |
| **Fallback** | N/A | Sí, a generación tradicional |
| **Contexto** | Genérico por rol | Personalizable con contexto libre |
| **Costo** | Gratis | 15 RPM gratis (Gemini 1.5 Flash) |

---

## 📊 Rate Limits de Google Gemini

**Gemini 1.5 Flash (Free Tier)**:
- **15 RPM** (requests per minute)
- **1500 RPD** (requests per day)
- **1 millón de tokens gratis** por mes

**Manejo en Aria**:
- Reintentos automáticos (exponential backoff)
- Fallback a generación tradicional si falla
- HTTP 503 con mensaje descriptivo si cuota excedida

---

## 🛡️ Manejo de Errores

### Códigos de Error AI

| Código | HTTP | Descripción | Acción |
|--------|------|-------------|--------|
| `INVALID_API_KEY` | 503 | API key no configurada o inválida | Verificar `GEMINI_API_KEY` |
| `QUOTA_EXCEEDED` | 503 | Cuota de API excedida (>15 RPM) | Esperar 1 minuto o usar tier pago |
| `API_ERROR` | 503 | Error de comunicación con Gemini | Reintentos automáticos (3x) |
| `TIMEOUT` | 503 | Timeout en respuesta (>30s) | Aumentar `gemini.api.timeout` |
| `INVALID_RESPONSE` | 503 | JSON malformado desde Gemini | Reportar bug |

**Todos los errores AI hacen fallback** a generación tradicional automáticamente.

---

## 🧪 Testing

```bash
# Ejecutar todos los tests (64 tests)
mvn test

# Tests de cobertura (JaCoCo)
mvn clean test jacoco:report

# Ver reporte en: target/site/jacoco/index.html
```

**Tests Incluidos**:
- ✅ 64 tests unitarios + integración
- ✅ Services, Controllers, Mappers
- ✅ Manejo de excepciones custom
- ✅ Mock de GeminiService en tests

---

## 📚 Recursos Adicionales

- **Google AI Studio**: [https://aistudio.google.com](https://aistudio.google.com)
- **Gemini API Docs**: [https://ai.google.dev/docs](https://ai.google.dev/docs)
- **Swagger UI Local**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Copilot Instructions**: [`.github/copilot-instructions.md`](.github/copilot-instructions.md)

---

## 🐛 Troubleshooting

### ❌ Error: `GEMINI_API_KEY not configured`
**Solución**: Configurar variable de entorno antes de ejecutar:
```bash
export GEMINI_API_KEY="tu_key_aqui"
mvn spring-boot:run
```

### ❌ Error: `HTTP 429 - Quota Exceeded`
**Solución**: Esperar 1 minuto (rate limit de 15 RPM) o usar generación tradicional temporalmente.

### ❌ Tests fallan con constructor ExcuseService
**Solución**: Asegurar que el mock de `GeminiService` esté presente en el `@BeforeEach`.

---

## 🦈 Niveles del Proyecto

- **DELFÍN** ✅: Estructura básica + CRUD + tests básicos
- **TIBURÓN** ✅: 100% tests + logging + excepciones + diagramas PlantUML
- **ULTRA SHARK AI** 🤖 ✅: Integración con Google Gemini AI + fallback robusto

---

## 👨‍💻 Desarrollo

**Compilar y ejecutar en desarrollo**:
```bash
# Compilar sin tests
mvn clean compile -DskipTests

# Ejecutar en modo desarrollo (con reload automático)
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

**Agregar nuevos fragmentos/memes/leyes**:
1. Editar JSONs en `/docs/json/`
2. Reiniciar aplicación (recarga automática)

---

## 📝 Licencia

MIT License - Ver [LICENSE](LICENSE)

---

## 🙌 Contribuciones

Este proyecto fue mejorado de nivel **DELFÍN** a **ULTRA SHARK AI** con:
- ✅ 64 tests comprehensivos
- ✅ Logging profesional (SLF4J)
- ✅ Manejo robusto de excepciones
- ✅ Diagramas PlantUML
- 🤖 Integración con Google Gemini AI
- 🔄 Fallback automático
- 📖 Documentación completa

---

**¡Ahora genera excusas técnicas creativas y contextuales con el poder de la IA! 🦈🤖**
