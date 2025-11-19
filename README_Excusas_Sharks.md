# 🦈 Java Sharks Challenge – Excusas Tech API + Memes + Leyes

Bienvenido al **Challenge oficial de la Tribu Java Sharks**.  
Tu misión: construir una **API REST** divertida, creativa y técnicamente sólida que genere excusas tech mezclando **fragmentos**, **memes**, **leyes del caos developer** y **axiomas universales del mundo IT**.

La idea es que desarrolles **en dupla con GitHub Copilot**, aprovechando su modo agente, comandos, edición y generación de código.

---

## 🎯 Objetivo

Crear una API REST que pueda:

- Generar **excusas tech aleatorias**
- Combinar fragmentos: *contexto*, *causa*, *consecuencia*, *recomendación*
- Sumar opcionalmente **memes tech argentinos**
- Justificar la excusa con **leyes/axiomas** (Murphy, Hofstadter, Dilbert, DevOps Principles, Axiomas del Dev)
- Exponer endpoints que permitan CRUD sobre los fragmentos y generación de excusas
- Ofrecer endpoints por rol: *dev*, *qa*, *devops*, *pm*, etc.

Tu API puede devolver:
- ✨ ley, meme, excusa del dia   
- ✨ excusa simple  
- ✨ excusa + meme  
- ✨ excusa + ley  
- ✨ excusa + meme + ley (modo ULTRA SHARK)

---

## 📦 Requisitos mínimos

### 🧩 API REST
- CRUD básico de fragmentos (contexto, causa, consecuencia, recomendación)
- CRUD opcional de:
  - Memes
  - Leyes / axiomas
  - Roles

### 📘 Documentación
- Swagger/OpenAPI accesible  
- README claro (este mismo archivo + tus notas)
- Un diagrama PlantUML (clases o secuencia)

### 🗃️ Persistencia
- H2 o base en memoria  
- Repositorio + servicio + modelo básico

### 🧪 Tests mínimos
- 1 test de combinador de excusas  
- Debe validar reproducibilidad con *seed*  

### 🧾 Entregables obligatorios
- Proyecto Spring Boot + Maven  
- Código compilable  
- Endpoints funcionales  
- PlantUML en `/docs/uml`  
- Historial del chat del Copilot Agente en `/docs/copilot`  
- Commits usando Conventional Commits  
- Tag en github repo   
- Swagger accesible en `/swagger-ui` o similar  

---

## 📂 JSON de ejemplo

En `/docs/json` vas a encontrar **ejemplos para poblar tu base en memoria**, incluyendo:

- `hofstadter.json`  
- `devops_principles.json`  
- `dev_axioms.json`  
- `memes_argentinos.json`  

> Podés usarlos para un **bulk inicial** desde un CommandLineRunner, via recurso estático o precargando el repositorio en memoria.

---

## 📡 Endpoints sugeridos (podés modificar todo libremente)

```
POST /fragments
GET  /fragments?tipo=contexto
PUT  /fragments/{id}
DELETE /fragments/{id}

GET /excuses/random
GET /excuses/role/{rol}
GET /excuses/daily
GET /excuses/meme
GET /excuses/law
GET /excuses/ultra

GET /health
```

---

## 🧠 Concepto de una Excusa Tech

Una excusa se compone de:

```json
{
  "contexto": "Estábamos deployando un hotfix",
  "causa": "el token de CI/CD venció",
  "consecuencia": "tuvimos que hacer rollback",
  "recomendacion": "automatizar la rotación de secretos"
}
```

Modo ULTRA SHARK:

```json
{
  "excusa": {
    "contexto": "...",
    "causa": "...",
    "consecuencia": "...",
    "recomendacion": "..."
  },
  "meme": "Tano Pasman - '¿CÓMO QUE FALLÓ EL PIPELINE?'",
  "ley": "Ley de Murphy - Si algo puede salir mal, saldrá mal durante la demo."
}
```

---

## 🧮 Criterios de evaluación

###  Level Mojarrita 
- API funcional + Swagger  
- Generador de excusas coherente y reproducible  
- Persistencia simple  
- Tests mínimos  
- Código limpio + linter  

###  Level Delfin 
- Calidad del diseño
- Patrones 
- Clean code 
- Conventional commits  

###  Level Shark 
- 100 Coverage de test de unidad

- SonarLint sin issues graves  
- PlantUML Clases, deploy, componentes, sequencia  

###  Level  White Shark
- Docker 
- Test de integracion Restassured 
- Arquitetura Hexagonal

###  Megalodon 
- Usar AI componer las excusas. 

---

## 🐳 Ejecutar con Docker

Si querés ejecutar la aplicación en Docker sin instalar Java ni Maven:

### Inicio Rápido con Docker Compose

```bash
# 1. Configurar API key de Gemini (requerido)
export GEMINI_API_KEY="tu_api_key_aqui"

# 2. Levantar la aplicación
docker-compose up -d

# 3. Verificar que funciona
curl http://localhost:8080/api/excuses/random
```

### Build manual con Docker

```bash
# Build imagen
docker build -t aria:latest .

# Ejecutar contenedor
docker run -d -p 8080:8080 \
  -e GEMINI_API_KEY="tu_api_key" \
  --name aria-api aria:latest
```

**Ver documentación completa:** [README_DOCKER.md](README_DOCKER.md)

---

## 🎉 ¡Éxitos Shark!
Recordá: la creatividad suma, el humor suma más,  
y si algo sale mal… ¡tenés una API completa para explicarlo! 🦈💻😅
