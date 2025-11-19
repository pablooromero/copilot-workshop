package com.accenture.aria.service.ai;

import com.accenture.aria.exception.GeminiException;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.Meme;
import com.accenture.aria.model.Role;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servicio de integración con Google Gemini AI.
 * Genera excusas técnicas creativas usando la API REST de Gemini 1.5 Flash.
 */
@Slf4j
@Service
public class GeminiService {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient httpClient;
    private final String apiKey;
    private final Gson gson;

    @Value("${gemini.temperature:0.8}")
    private double temperature;

    @Value("${gemini.max-tokens:1024}")
    private int maxTokens;

    @Value("${gemini.retry.max-attempts:3}")
    private int maxRetries;

    public GeminiService(OkHttpClient geminiHttpClient, String geminiApiKey) {
        this.httpClient = geminiHttpClient;
        this.apiKey = geminiApiKey;
        this.gson = new Gson();
    }

    /**
     * Genera una excusa técnica usando Google Gemini AI.
     * Realiza hasta 3 reintentos con backoff exponencial si falla.
     *
     * @param role rol del usuario para determinar estilo técnico
     * @param context contexto adicional opcional
     * @param fragments fragmentos de referencia del dominio
     * @param law ley/axioma opcional a incorporar
     * @param meme meme opcional a referenciar
     * @return mapa con 4 claves (contexto, causa, consecuencia, recomendacion)
     * @throws GeminiException si la API falla después de todos los reintentos
     */
    public Map<String, String> generateExcuseText(Role role, String context, List<Fragment> fragments, Law law, Meme meme) {
        validateApiKey();

        String prompt = buildPrompt(role, context, fragments, law, meme);
        log.debug("Generated prompt for Gemini (length: {} chars)", prompt.length());

        int attempt = 0;
        Exception lastException = null;

        while (attempt < maxRetries) {
            attempt++;
            try {
                log.info("Calling Gemini API (attempt {}/{})", attempt, maxRetries);
                String response = callGeminiAPI(prompt);
                Map<String, String> parsedExcuse = parseGeminiResponse(response);
                log.info("Gemini API call successful");
                return parsedExcuse;
            } catch (IOException e) {
                lastException = e;
                log.warn("Gemini API call failed (attempt {}/{}): {}", attempt, maxRetries, e.getMessage());
                if (attempt < maxRetries) {
                    try {
                        Thread.sleep(1000L * attempt); // Exponential backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new GeminiException(GeminiException.GeminiErrorCode.API_ERROR, 
                                "Interrupted while retrying Gemini API call", ie);
                    }
                }
            }
        }

        throw new GeminiException(GeminiException.GeminiErrorCode.API_ERROR,
                "Failed to generate excuse after " + maxRetries + " attempts", lastException);
    }

    /**
     * Construye el prompt para Gemini con contexto del dominio.
     */
    private String buildPrompt(Role role, String context, List<Fragment> fragments, Law law, Meme meme) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Eres un generador de excusas técnicas divertidas y creativas para profesionales IT.\n\n");
        prompt.append("ROL: ").append(getRoleDescription(role)).append("\n\n");

        if (context != null && !context.isBlank()) {
            prompt.append("CONTEXTO: ").append(context).append("\n\n");
        }

        prompt.append("REFERENCIAS DEL DOMINIO (úsalas como inspiración, no copies textualmente):\n");
        if (fragments != null && !fragments.isEmpty()) {
            fragments.forEach(f -> prompt.append("- ").append(f.getText()).append("\n"));
        }

        if (law != null) {
            prompt.append("\nLEY/AXIOMA A INCORPORAR:\n");
            prompt.append("Nombre: ").append(law.getName()).append("\n");
            prompt.append("Descripción: ").append(law.getDescription()).append("\n");
        }

        if (meme != null) {
            prompt.append("\nMEME DE REFERENCIA:\n");
            prompt.append("Personaje: ").append(meme.getCharacter()).append("\n");
            prompt.append("Situación: ").append(meme.getDescription()).append("\n");
        }

        prompt.append("\nGENERA UNA EXCUSA TÉCNICA con esta estructura JSON:\n");
        prompt.append("{\n");
        prompt.append("  \"contexto\": \"Descripción del contexto/situación técnica\",\n");
        prompt.append("  \"causa\": \"Causa técnica raíz del problema\",\n");
        prompt.append("  \"consecuencia\": \"Consecuencia o impacto del problema\",\n");
        prompt.append("  \"recomendacion\": \"Recomendación técnica o acción sugerida\"\n");
        prompt.append("}\n\n");

        prompt.append("REQUISITOS:\n");
        prompt.append("- Usa jerga técnica auténtica del rol ").append(role).append("\n");
        prompt.append("- Sé creativo y ligeramente humorístico\n");
        prompt.append("- Incorpora la ley/axioma de forma natural si está presente\n");
        prompt.append("- Haz referencia al meme de forma sutil si está presente\n");
        prompt.append("- Responde SOLO con el JSON, sin texto adicional\n");

        return prompt.toString();
    }

    /**
     * Llama a la API REST de Gemini con el prompt.
     */
    private String callGeminiAPI(String prompt) throws IOException {
        String url = GEMINI_API_URL + "?key=" + apiKey;

        JsonObject requestBody = new JsonObject();
        
        // Contents array
        JsonArray contentsArray = new JsonArray();
        JsonObject contentItem = new JsonObject();
        JsonArray partsArray = new JsonArray();
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);
        partsArray.add(textPart);
        contentItem.add("parts", partsArray);
        contentsArray.add(contentItem);
        requestBody.add("contents", contentsArray);

        // Generation config
        JsonObject generationConfig = new JsonObject();
        generationConfig.addProperty("temperature", temperature);
        generationConfig.addProperty("maxOutputTokens", maxTokens);
        requestBody.add("generationConfig", generationConfig);

        RequestBody body = RequestBody.create(gson.toJson(requestBody), JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                log.error("Gemini API error (HTTP {}): {}", response.code(), errorBody);
                
                if (response.code() == 401 || response.code() == 403) {
                    throw new GeminiException(GeminiException.GeminiErrorCode.INVALID_API_KEY,
                            "Invalid or unauthorized API key");
                } else if (response.code() == 429) {
                    throw new GeminiException(GeminiException.GeminiErrorCode.QUOTA_EXCEEDED,
                            "API quota exceeded. Try again later.");
                } else {
                    throw new IOException("HTTP " + response.code() + ": " + errorBody);
                }
            }

            String responseBody = response.body() != null ? response.body().string() : null;
            if (responseBody == null || responseBody.isBlank()) {
                throw new GeminiException(GeminiException.GeminiErrorCode.INVALID_RESPONSE,
                        "Empty response from Gemini API");
            }

            log.debug("Gemini API response: {}", responseBody);
            return responseBody;
        }
    }

    /**
     * Parsea la respuesta JSON de Gemini para extraer la excusa.
     */
    private Map<String, String> parseGeminiResponse(String response) {
        try {
            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            
            // Navegar: candidates[0].content.parts[0].text
            String generatedText = jsonResponse
                    .getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

            // Limpiar markdown code blocks si existen
            generatedText = generatedText.replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();

            log.debug("Extracted generated text: {}", generatedText);

            // Parsear JSON de la excusa
            JsonObject excuse = gson.fromJson(generatedText, JsonObject.class);
            return Map.of(
                    "contexto", excuse.has("contexto") ? excuse.get("contexto").getAsString() : "",
                    "causa", excuse.has("causa") ? excuse.get("causa").getAsString() : "",
                    "consecuencia", excuse.has("consecuencia") ? excuse.get("consecuencia").getAsString() : "",
                    "recomendacion", excuse.has("recomendacion") ? excuse.get("recomendacion").getAsString() : ""
            );
        } catch (Exception e) {
            log.error("Failed to parse Gemini response: {}", response, e);
            throw new GeminiException(GeminiException.GeminiErrorCode.INVALID_RESPONSE,
                    "Failed to parse Gemini API response: " + e.getMessage(), e);
        }
    }

    private void validateApiKey() {
        if (apiKey == null || apiKey.isBlank() || apiKey.equals("your_api_key_here")) {
            throw new GeminiException(GeminiException.GeminiErrorCode.INVALID_API_KEY,
                    "GEMINI_API_KEY not configured. Set environment variable or application property.");
        }
    }

    private String getRoleDescription(Role role) {
        return switch (role) {
            case DEV -> "Desarrollador de Software";
            case QA -> "Quality Assurance / Tester";
            case DEVOPS -> "DevOps Engineer / SRE";
            case PM -> "Project Manager / Scrum Master";
            case SRE -> "Site Reliability Engineer";
            case ALL -> "Profesional IT General";
        };
    }
}
