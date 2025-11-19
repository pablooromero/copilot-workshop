# Guía JavaDoc - Aria

Documentación rápida para métodos públicos en **Controllers** y **Services**.

---

## Principios

- Documentar **solo métodos públicos** de Controllers y Services
- Descripciones cortas (2-3 líneas)
- Español
- Tags básicos: `@param`, `@return`, `@throws`

---

## Template Controller

```java
/**
 * [Descripción breve de qué hace el endpoint].
 * 
 * @param [nombre] [descripción]
 * @return [qué retorna]
 */
@GetMapping("/ruta")
public ResponseEntity<Tipo> metodo(Parametros params) {
    // implementación
}
```

**Ejemplo real:**

```java
/**
 * Genera una excusa con Google Gemini AI.
 * Permite configurar rol, contexto y nivel de creatividad.
 * 
 * @param request configuración de la excusa (rol, contexto, creatividad)
 * @return excusa generada por IA con sus 4 fragmentos
 */
@PostMapping("/ai")
public ResponseEntity<ExcuseResponseDTO> generateExcuseWithAI(
        @RequestBody @Valid ExcuseAIRequestDTO request) {
    // implementación
}
```

---

## Template Service

```java
/**
 * [Descripción breve de la operación].
 * [Detalle adicional si es necesario].
 * 
 * @param [nombre] [descripción]
 * @return [qué retorna]
 * @throws [Excepción] [cuándo se lanza]
 */
public Tipo metodo(Parametros params) {
    // implementación
}
```

**Ejemplo real:**

```java
/**
 * Genera una excusa técnica usando Google Gemini AI.
 * Si la API falla, usa generación tradicional como fallback.
 * 
 * @param request configuración con rol, contexto y creatividad
 * @return excusa completa con 4 fragmentos (contexto, causa, consecuencia, recomendación)
 * @throws ValidationException si el rol es inválido
 */
public ExcuseResponseDTO generateExcuseWithAI(ExcuseAIRequestDTO request) {
    // implementación
}
```

---

## Checklist

- [ ] Describir qué hace el método (no cómo)
- [ ] Documentar todos los `@param`
- [ ] Documentar `@return`
- [ ] Documentar `@throws` si aplica

---

## Generar JavaDoc

```bash
# Generar HTML
mvn javadoc:javadoc

# Abrir
start target/site/apidocs/index.html
```

---

**Mantén la documentación simple y útil** 🦈
