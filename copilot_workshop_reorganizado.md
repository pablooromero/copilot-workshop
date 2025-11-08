# 🧭 Workshop: GitHub Copilot + VS Code (Documento reorganizado y validado)
**Duración:** 1h – 1h 15min  
**Ámbito:** Tribu Java Sharks · Squad Innovación & Capacitación

---

# 🧭 BLOQUE 1 — Introducción y Apertura (5 min)

## Objetivo del Workshop
- Entender cómo funciona GitHub Copilot realmente.
- Aprender a configurarlo y personalizarlo.
- Dominar el contexto (@, #, /) y modos (Inline, Chat, Agente, Edit).
- Preparar un entorno IA-Ready.
- Introducción a buenas prácticas y prompts efectivos.

## Rompehielos
- ¿Quién ya usa Copilot?
- ¿Cuál fue el mejor y el peor prompt que tiraste? 😅

---

# 🧱 BLOQUE 2 — Instalación y Configuración (VS Code) [10–15 min]

## Requisitos previos
- Visual Studio Code actualizado.
- Cuenta GitHub con Copilot activo.
- Internet + login desde VS Code.

## Extensiones esenciales
1. **GitHub Copilot**  
2. **GitHub Copilot Chat**  
3. **(Opcional)** REST Client / Thunder Client, **PlantUML**, **SonarLint / SonarQube IDE**.

## Instalación
- VS Code → *View → Extensions* (Ctrl+Shift+X)
- Buscar e instalar “GitHub Copilot” y “GitHub Copilot Chat”.
- Reiniciar VS Code si lo solicita.

## Iniciar sesión y activar
- Accounts → **Sign in with GitHub**.
- Ver estado: **Copilot: activo** en la barra inferior.

## Configuraciones recomendadas
- Settings (Ctrl+,) → buscar "Copilot":
  - **Enable GitHub Copilot** → ON
  - **Enable GitHub Copilot Chat** → ON
  - **Inline Suggestions** → Enabled
  - **Show Suggested Completions** → ON
- Copilot Chat (⚙️ en el panel):
  - **Use Workspace Instructions** → ON (lee .github/copilot-instructions.md)
  - **Include context from open files** → ON
  - **Remember chat history** → ON
  - **Response language**: Configurable según preferencia
  - **Model**: Depende de tu suscripción (GPT-4 en Copilot Enterprise)

## Atajos útiles
- `Tab` → Aceptar sugerencia inline  
- `Esc` → Descartar sugerencia  
- `Ctrl + Enter` → Ver lista completa de sugerencias
- `Ctrl + Alt + I` → Abrir el chat de Copilot
- `Ctrl + I` → **Inline Chat** (chat contextual en el editor)
- `Alt + [` y `Alt + ]` → Navegar entre sugerencias (cuando hay múltiples)
- *View → Command Palette → "Copilot"* → Acceder a todos los comandos disponibles

## Verificación rápida (2 min)
- Escribir un comentario: `// función para validar email con regex` → aceptar propuesta con **Tab**.
- En Copilot Chat: “explicá este método” o “explicá #selection”.

> *Nota:* Si usás IntelliJ IDEA, esta sección es equivalente (plugins Copilot + Copilot Chat, atajos propios).

---

# 🧠 BLOQUE 3 — Fundamentos: ¿Cómo funciona Copilot? [10–15 min]

Copilot prioriza **contexto** para dar mejores respuestas.

## Fuentes de contexto

### Contexto **implícito** (automático)
- **Archivo actual** y **código seleccionado** (máxima prioridad).
- **Archivos abiertos en pestañas**.
- **Árbol del proyecto** (escaneo semántico).
- **Archivos de configuración** (pom.xml, package.json, .editorconfig, README.md).

### Contexto **explícito** (manual)
**Chat Participants (@):**
- `@workspace` → inspección de todo el proyecto.
- `@terminal` → últimos comandos y salidas del terminal.
- `@vscode` → ayuda con configuración del IDE.
- `@github` → consulta/repos/issues/PRs (según permisos).

**Variables (#):**
- `#file:Nombre.ext` → referencia a un archivo específico.
- `#selection` → el código actualmente seleccionado.
- `#codebase` → búsqueda semántica en todo el proyecto.
- `#terminalLastCommand`, `#terminalSelection` → contexto del terminal.

## Buenas prácticas para maximizar contexto
1) Abrí los archivos relevantes antes de pedir algo.  
2) Seleccioná el código relevante (se detecta automáticamente).  
3) Usá `#file:` o `#selection` para ser explícito.  
4) Usá `@workspace` cuando necesites ver todo el repo.  
5) Mantené **README**, **copilot-instructions.md** y guías al día.

## Ejemplos de prompts (mal vs bien)
- ❌ “creá un endpoint”  
- ✅ “creá un endpoint **POST /usuarios** que use **#file:UserService.java** siguiendo el patrón de **#file:ProductController.java** (@workspace)”

---

# 🔤 BLOQUE 3.5 — Sintaxis de Copilot: **@**, **#**, **/**, **?**

## **@ (Agentes y Menciones de Contexto)**
Traen contexto o delegan tareas a agentes especializados.

**Ejemplos**
```text
@workspace explicá la arquitectura del proyecto
@terminal qué hizo el último mvn test (#terminalLastCommand)
@vscode cómo configuro el debugger para Spring Boot
@github listá mis PR abiertos en este repo
```

## **# (Variables de Contexto)**
Referencias a elementos específicos para precisar tu consulta.

**Ejemplos**
```text
Explicá #file:UserService.java 
Documentá #selection con Javadoc
¿Qué hace #codebase con validaciones duplicadas?
Analizá el error de #terminalLastCommand
```

## **/ (Slash Commands)**
Atajos para acciones rápidas en Copilot Chat.
```text
/explain - Explica el código seleccionado
/fix - Sugiere correcciones para problemas
/tests - Genera tests unitarios
/doc - Genera documentación
/optimize - Optimiza el código
/new - Crea nuevo código basado en descripción
/clear - Limpia el historial del chat
```

## **? (Ayuda Contextual)**
Obten ayuda sobre comandos y características:
```text
? - Muestra ayuda general
/? - Muestra ayuda sobre comandos slash
@? - Muestra ayuda sobre participantes del chat
#? - Muestra ayuda sobre referencias de contexto
```

## Combos potentes
```text
@workspace genera /tests para #file:UserService.java
/ fix para #selection siguiendo SOLID
@terminal explica por qué falló #terminalLastCommand
/ doc para #selection con formato Javadoc del @workspace
```

---

# 💬 BLOQUE 4 — Copilot en el Editor: Inline, Chat, Agent, Edit

## 1) **Autocompletado Inline** (Completions)
- Sugerencias en tiempo real (ghost text).
- Contexto: archivo actual + abiertos.

**Ejemplo**
```java
// Método para calcular precio con descuento
public double calculateDiscountedPrice(double price, double discount) {
    return price * (1 - discount / 100);
}
```

## 2) **Inline Chat** (`Ctrl + I`)
- Chat contextual dentro del editor.
- Acciones: **Edit**, **Insert**, **Explain**, **Accept**, **Discard**.

**Ejemplos**
```text
refactorizá este método usando streams
agregá manejo de excepciones
extraé esta lógica a un método privado
```

## 3) **Panel de Copilot Chat**
- **Agente (@)**: `@workspace`, `@terminal`, `@vscode`, `@github`.
- **Chat libre**: preguntas generales / explicaciones.
- **Edit Mode**: refactor global multiarchivo.
- **Configurar (⚙️)**: activar instrucciones del workspace, open files, idioma, modelo.

---

# ⚙️ BLOQUE 5 — Configuración avanzada del Chat (⚙️)

**Panel de Chat → Icono ⚙️**  
- **Use workspace instructions** → lee `.github/copilot-instructions.md`.  
- **Include context from open files** → usa archivos abiertos como referencia.  
- **Remember chat history** → mantiene contexto conversacional.  
- **Response language** → Español/Inglés (no afecta comprensión de código).  
- **Model** → GPT-4 (si está disponible) / GPT-3.5 (más veloz).

---

# 📁 BLOQUE 6 — Archivos que Copilot **realmente** usa (validado)

## Prioridad y soportes

### Invocables explícitamente (vía prompt)
- ✅ `.github/prompts/*.prompt.md` → Ejecutables con `/<nombre-del-prompt>` o desde Command Palette.
  - Alcance: VS Code, compatible con Copilot Enterprise.
  - Extras: Soporta frontmatter con:
    - `tools`: Define herramientas disponibles
    - `mode`: Configura el comportamiento del agente
    - `description`: Describe el propósito del prompt
    - `model`: Especifica el modelo a usar (si está disponible)

### Implícitos (aplicados automáticamente)
- ✅ `.github/copilot-instructions.md` (**PRIORIDAD MÁXIMA**) → Se aplica a todos los requests del workspace.
- ✅ `AGENTS.md` (en raíz) → Instrucciones para agentes; se aplica automáticamente (configurable) y soporta múltiples archivos anidados (experimental).

### Condicionados / Adjuntables (no se invocan con `/`)
- ✅ `.github/instructions/*.instructions.md` → Se aplican según `applyTo`. También podés adjuntarlos explícitamente desde Chat > Add Context > Instructions.
- ✅ `.github/workflows/*.yml` → No ejecutables desde chat; Copilot los usa como referencia para sugerir comandos. Podés adjuntarlos como `#file:`.
- ✅ `README.md`, `pom.xml` / `package.json`, `.editorconfig` → Contexto general; Copilot los usa implícitamente. Podés adjuntarlos con `#file:` cuando sea relevante.

### ⚠️ No oficiales (no influyen directo en Copilot)
- `.github/agents.md` (usar **AGENTS.md** en raíz)
- `.github/coding-guidelines.md` (podés tenerlo, pero no es de lectura prioritaria)
- Issue/PR templates (útiles para GitHub UI, no para Copilot)

### 🧭 Guía rápida
- Ejecutables con `/`: solo `.prompt.md`.
- Efecto automático: `copilot-instructions.md`, `AGENTS.md`.
- Adjuntables como contexto: `*.instructions.md`, workflows, README/config (`#file:` o Add Context…).

### 🗂️ Tabla resumen (invocación vs contexto)

| Archivo | ¿Se invoca con `/`? | ¿Se aplica automático? | ¿Se puede adjuntar? |
|---|---|---|---|
| `.github/prompts/*.prompt.md` | Sí (`/mi-prompt`) | No | Opcional (abrir y ▶) |
| `.github/copilot-instructions.md` | No | Sí (todas las requests) | No necesario |
| `.github/instructions/*.instructions.md` | No | Según `applyTo` | Sí (Add Context > Instructions) |
| `AGENTS.md` (raíz) | No | Sí (configurable) | Opcional (`#file:AGENTS.md`) |
| `.github/workflows/*.yml` | No | No (sólo referencia) | Sí (`#file:ci.yml`) |
| `README.md`, `pom.xml`, `package.json`, `.editorconfig` | No | Implícito como contexto | Sí (`#file:`) |
| No oficiales (guidelines, etc.) | No | No | Sí (`#file:`) |

## Estructura recomendada
```text
.github/
  copilot-instructions.md
  instructions/
    controllers.instructions.md
    services.instructions.md
    tests.instructions.md
  prompts/
    code-review.prompt.md
    refactor.prompt.md
    testing.prompt.md
  workflows/
    ci.yml
    deploy.yml
AGENTS.md
README.md
.editorconfig
src/
docs/
pom.xml
```

---

# 🧻 BLOQUE 7 — Ejercicios Prácticos

## Ejercicio 1 — Análisis y Refactor
```text
@workspace analizá la clase TicketService y:
1. Identificá violaciones a SOLID
2. Proponé una refactorización
3. Explicá los beneficios del cambio
4. Mostrá un diagrama de la nueva estructura
5. Implementá los cambios por pasos
```

## Ejercicio 2 — Documentación Técnica
```text
@workspace generá:
1. Diagrama C4 (Contexto y Contenedores)
2. Diagrama de componentes con PlantUML
3. Documentación OpenAPI para endpoints
4. ADR explicando decisiones de diseño
```

## Ejercicio 3 — Testing
```text
/tests para #file:TicketService.java que:
1. Cubran casos de éxito y error
2. Usen @ParameterizedTest
3. Mocken dependencias correctamente
4. Sigan patrón AAA (Arrange-Act-Assert)
```

## Ejercicio 4 — Nueva Feature
```text
@workspace implementá filtrado de tickets por:
- Estado (ABIERTO, EN_PROGRESO, CERRADO)
- Prioridad (ALTA, MEDIA, BAJA)
- Fecha de creación (rango)
- Asignado a (usuario)

Incluir:
1. Endpoint REST con filtros como query params
2. Tests de integración
3. Documentación del endpoint
4. Manejo de casos borde
```

---


# 🧩 BLOQUE 8 — Personalizar Copilot para tu equipo

### 1) **Instrucciones del Workspace** — `.github/copilot-instructions.md`
```markdown
# Instrucciones para GitHub Copilot

## Estándares del Proyecto
- Java 17+, Spring Boot 3.x, Maven
- Arquitectura hexagonal / Clean Architecture
- Principios SOLID y DDD

## Reglas de Código
- Controladores con @RestController y @Validated
- DTOs con validaciones Jakarta
- Mappers con MapStruct
- Tests unitarios con JUnit 5 + Mockito

## Documentación
- Swagger/OpenAPI en todos los endpoints
- README actualizado por feature
- Diagramas PlantUML para nuevos componentes

## Calidad
- Cobertura mínima: 80%
- SonarQube: 0 issues críticos
- No TODOs sin ticket asociado
```

### 2) **Roles de Agente** — `AGENTS.md`
```markdown
# Roles de Copilot

## @java-architect
Arquitecto senior especializado en:
- Microservicios Spring Boot
- DDD y Clean Architecture
- Patrones de diseño empresariales
- Performance y escalabilidad

## @code-reviewer
Revisor de código enfocado en:
- Estándares de equipo
- Mejores prácticas Java
- Seguridad y vulnerabilidades
- Mantenibilidad y testing
```

### 3) **Instrucciones por Dominio** — `.github/instructions/*.instructions.md`
```markdown
# domain-logic.instructions.md
applyTo: ["src/main/java/*/domain/**"]

## Reglas de Dominio
- Entidades inmutables
- Value Objects para conceptos de negocio
- Validaciones invariantes en constructores
- Comportamiento rico en entidades

# api-endpoints.instructions.md
applyTo: ["src/main/java/*/controller/**"]

## Estándares API
- REST con Richardson Maturity Model L2+
- Versionado en URL (v1, v2)
- Respuestas con Problem Details RFC 7807
- Rate limiting y documentación
```

### 4) **Plantillas de Prompts** — `.github/prompts/*.prompt.md`
```markdown
# code-review.prompt.md
---
mode: reviewer
tools: ['problems', 'tests', 'search']
---

## Objetivos Review
1. Validar estándares de equipo
2. Verificar cobertura de tests
3. Detectar code smells
4. Sugerir mejoras de performance

# feature.prompt.md
---
mode: architect
tools: ['workspace', 'problems']
---

## Desarrollo Features
1. Análisis requisitos
2. Diseño solución
3. Implementación TDD
4. Documentación técnica
```

### 5) **Contexto del Proyecto**
- README con arquitectura y decisiones
- Diagramas C4 y componentes
- ADRs (Architecture Decision Records)
- Ejemplos de implementación

**Ejercicio**: ¿Qué reglas de calidad agregarías a las instrucciones de Copilot?

---


# 💻 BLOQUE 9 — DEMOS (15–20 min)

## DEMO 1 — Desarrollo con Copilot Chat

### Feature completa: Gestión de Tickets
1. Análisis con modo Arquitecto:
```text
@workspace diseñá una API REST para gestión de tickets siguiendo:
- Clean Architecture
- DDD táctico
- SOLID
```

2. Implementación guiada:
```text
/new crear estructura base del proyecto con:
- Dominio: Ticket, Estado, Prioridad
- Casos de uso: Crear, Actualizar, Buscar
- Adaptadores: REST, JPA, Cache
```

3. Testing y documentación:
```text
/tests generar suite completa
/doc documentar con OpenAPI
```

## DEMO 2 — Mejoras Iterativas

### Refactoring con Inline Chat
1. Seleccionar código + `Ctrl+I`:
```text
Refactorizar para:
- Usar Optional en Repository
- Agregar cache con Spring
- Mejorar manejo de errores
```

### Optimización con Agent Mode
```text
@workspace analizá y optimizá:
1. Queries N+1 en JPA
2. Índices faltantes
3. Batch operations
```

## DEMO 3 — Revisión de Código

### Code Review Automatizado
1. Activar modo Reviewer:
```text
@code-reviewer revisá los últimos cambios
enfocándote en:
- Clean Code
- Seguridad
- Performance
- Testing
```

2. Análisis estático:
```text
/analyze buscar:
- Code smells
- Vulnerabilidades
- Deuda técnica
```

3. Mejoras sugeridas:
```text
/fix aplicar correcciones de:
- Logging
- Excepciones
- Configuración
```

---

# ✅ BLOQUE 10 — Resumen y Cierre

Aprendimos:
- Qué es Copilot y cómo piensa por contexto.
- Cómo configurarlo (VS Code + Chat ⚙️).
- Sintaxis @, #, / y combinaciones poderosas.
- Archivos que Copilot realmente usa.
- Estructura IA-Ready y personalización.
- Demos: Inline, Chat, Agent, Edit.

---

# 🏁 BLOQUE 11 — Preparación para el Challenge

Checklist:
- ✅ Repo asignado
- ✅ IDE lista
- ✅ Copilot activo
- ✅ Instrucciones configuradas
- ✅ Ganas de romperla en dupla IA + Shark 🦈🤖

---

## 📚 Fuentes (validado)
- GitHub Docs — *Custom instructions for Copilot*  
- VS Code Docs — *Copilot customization & chat*  
- Especificación **AGENTS.md** (OpenAI)  
- GitHub Docs — *Copilot Chat: participants y slash commands*
