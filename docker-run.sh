#!/bin/bash
# ============================================
# Script para ejecutar Aria en Docker
# ============================================

set -e

echo "[INFO] Starting Aria container..."
echo ""

# Verificar si la variable GEMINI_API_KEY está configurada
if [ -z "$GEMINI_API_KEY" ]; then
    echo "[WARNING] GEMINI_API_KEY not set!"
    echo "Please set it: export GEMINI_API_KEY=your_api_key"
    echo ""
    echo "Using placeholder value 'your_api_key_here'"
    GEMINI_API_KEY="your_api_key_here"
fi

# Detener contenedor existente si existe
docker stop aria-api 2>/dev/null || true
docker rm aria-api 2>/dev/null || true

# Ejecutar nuevo contenedor
docker run -d \
  -p 8080:8080 \
  -e GEMINI_API_KEY="$GEMINI_API_KEY" \
  --name aria-api \
  --restart unless-stopped \
  aria:latest

echo ""
echo "[SUCCESS] Container started successfully!"
echo ""
echo "Container status:"
docker ps -f name=aria-api
echo ""
echo "View logs: docker logs -f aria-api"
echo "Stop container: docker stop aria-api"
echo ""
echo "API available at: http://localhost:8080"
echo "Swagger UI: http://localhost:8080/swagger-ui.html"
echo ""
