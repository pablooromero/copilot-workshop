# 🐳 Aria - Guía de Dockerización

Esta guía explica cómo ejecutar **Aria** (API REST de Excusas Tech) usando Docker y Docker Compose.

---

## 📋 Prerrequisitos

- **Docker** 20.10+ instalado ([Descargar Docker](https://www.docker.com/get-started))
- **Docker Compose** 2.0+ (incluido con Docker Desktop)
- **API Key de Google Gemini AI** ([Obtener aquí](https://aistudio.google.com/app/apikey))

---

## 🚀 Inicio Rápido

### Opción 1: Docker Compose (RECOMENDADO)

```bash
# 1. Configurar API key de Gemini
export GEMINI_API_KEY="tu_api_key_aqui"

# 2. Levantar la aplicación
docker-compose up -d

# 3. Ver logs
docker-compose logs -f

# 4. Verificar que funciona
curl http://localhost:8080/api/excuses/random
```

### Opción 2: Docker Run

```bash
# 1. Build de la imagen
docker build -t aria:latest .

# 2. Ejecutar contenedor
docker run -d \
  -p 8080:8080 \
  -e GEMINI_API_KEY="tu_api_key_aqui" \
  --name aria-api \
  aria:latest

# 3. Ver logs
docker logs -f aria-api

# 4. Verificar que funciona
curl http://localhost:8080/api/excuses/random
```

---

## ⚙️ Variables de Entorno

### Variables Requeridas

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `GEMINI_API_KEY` | **REQUERIDO** - API key de Google Gemini AI | `your_api_key_here` |

### Variables Opcionales

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `GEMINI_TEMPERATURE` | Nivel de creatividad (0.0-2.0) | `0.8` |
| `GEMINI_MAX_TOKENS` | Máximo tokens de respuesta | `1024` |
| `GEMINI_API_TIMEOUT` | Timeout en segundos | `30` |
| `GEMINI_RETRY_MAX_ATTEMPTS` | Reintentos en caso de fallo | `3` |
| `SPRING_PROFILES_ACTIVE` | Perfil de Spring Boot | `default` |
| `JAVA_OPTS` | Opciones JVM | `-Xms256m -Xmx512m` |

---

## 📦 Uso con .env File

Puedes crear un archivo `.env` en la raíz del proyecto:

```bash
# .env
GEMINI_API_KEY=AIzaSyD...tu_api_key_real...
GEMINI_TEMPERATURE=0.8
GEMINI_MAX_TOKENS=1024
```

Luego ejecutar:

```bash
docker-compose --env-file .env up -d
```

---

## 🔧 Comandos Útiles

### Gestión de Contenedores

```bash
# Ver contenedores en ejecución
docker-compose ps

# Detener aplicación
docker-compose down

# Reiniciar aplicación
docker-compose restart

# Ver logs en tiempo real
docker-compose logs -f aria-api

# Entrar al contenedor (debugging)
docker exec -it aria-api sh
```

### Build y Limpieza

```bash
# Rebuild imagen sin cache
docker-compose build --no-cache

# Rebuild y reiniciar
docker-compose up -d --build

# Eliminar todo (contenedores + imágenes + volúmenes)
docker-compose down -v --rmi all
```

### Healthcheck

```bash
# Verificar salud del contenedor
docker inspect aria-api | grep -i health

# Healthcheck manual
curl http://localhost:8080/api/excuses/random
```

---

## 📊 Endpoints Disponibles

Una vez levantado el contenedor, puedes acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:excusesdb`
  - Username: `sa`
  - Password: *(vacío)*

### Ejemplos de Uso

```bash
# Excusa aleatoria
curl http://localhost:8080/api/excuses/random

# Excusa del día
curl http://localhost:8080/api/excuses/daily

# Excusa con IA (modo ULTRA)
curl http://localhost:8080/api/excuses/ai/creative?role=DEV

# Excusa ULTRA SHARK (meme + ley)
curl http://localhost:8080/api/excuses/ultra?role=DEVOPS
```

---

## 🏗️ Arquitectura Docker

### Multi-Stage Build

```
Stage 1 (builder):
  maven:3.9.9-eclipse-temurin-17
  ├── Descarga dependencias
  ├── Compila código fuente
  └── Genera JAR ejecutable (~40MB)

Stage 2 (runtime):
  eclipse-temurin:17-jre-alpine
  ├── Solo JRE (no JDK completo)
  ├── Copia JAR desde builder
  └── Imagen final optimizada (~200MB)
```

### Ventajas

✅ **Imagen pequeña**: ~200MB (vs ~700MB con JDK)  
✅ **Seguridad**: Sin herramientas de compilación en producción  
✅ **Rápido**: Cache de dependencias Maven  
✅ **Usuario no-root**: Mejores prácticas de seguridad  

---

## 🐛 Troubleshooting

### El contenedor no inicia

```bash
# Ver logs detallados
docker-compose logs aria-api

# Verificar variables de entorno
docker exec aria-api env | grep GEMINI
```

### Puerto 8080 ocupado

```bash
# Cambiar puerto en docker-compose.yml
ports:
  - "9090:8080"  # Host:Container
```

### API Key no configurada

**Síntoma**: Aplicación funciona pero generación AI falla

**Solución**:
```bash
# Verificar que la API key esté configurada
docker exec aria-api env | grep GEMINI_API_KEY

# Si no está, recrear contenedor con la key
docker-compose down
export GEMINI_API_KEY="tu_api_key_aqui"
docker-compose up -d
```

### Errores de compilación

```bash
# Rebuild sin cache
docker-compose build --no-cache

# Verificar que el Dockerfile esté correcto
docker build -t aria:debug . --progress=plain
```

---

## 🚢 Despliegue en Producción

### Recomendaciones

1. **Usar secretos**: No hardcodear API key en docker-compose.yml
2. **Configurar límites**: Añadir `mem_limit` y `cpus` en docker-compose
3. **Monitoreo**: Integrar con herramientas como Prometheus/Grafana
4. **Load Balancer**: Usar nginx como reverse proxy
5. **Logging**: Configurar driver de logs centralizado

### Ejemplo con límites de recursos

```yaml
services:
  aria-api:
    # ... configuración anterior ...
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 1G
        reservations:
          cpus: '0.5'
          memory: 512M
```

---

## 📚 Referencias

- [Dockerfile Reference](https://docs.docker.com/engine/reference/builder/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Google Gemini AI Documentation](https://ai.google.dev/docs)

---

## 🦈 Soporte

Para problemas o preguntas:
- Ver `README.md` principal
- Revisar `README_AI.md` para configuración de Gemini
- Consultar logs: `docker-compose logs -f`

**¡Happy Dockerizing, Sharks!** 🐳🦈
