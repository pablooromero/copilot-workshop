@echo off
REM ============================================
REM Script para build de imagen Docker - Aria
REM ============================================

echo [INFO] Building Aria Docker image...
echo.

REM Build con tag latest
docker build -t aria:latest .

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] Image built successfully!
    echo.
    echo Image details:
    docker images aria:latest
    echo.
    echo To run the container:
    echo   docker run -d -p 8080:8080 -e GEMINI_API_KEY=your_key aria:latest
    echo.
    echo Or use docker-compose:
    echo   docker-compose up -d
) else (
    echo.
    echo [ERROR] Build failed! Check logs above.
    exit /b 1
)
