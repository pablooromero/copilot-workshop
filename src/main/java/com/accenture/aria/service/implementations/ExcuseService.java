package com.accenture.aria.service.implementations;

import com.accenture.aria.dto.ExcuseAIRequestDTO;
import com.accenture.aria.dto.ExcuseResponseDTO;
import com.accenture.aria.exception.GeminiException;
import com.accenture.aria.model.Fragment;
import com.accenture.aria.model.FragmentType;
import com.accenture.aria.model.Law;
import com.accenture.aria.model.Meme;
import com.accenture.aria.model.Role;
import com.accenture.aria.repository.FragmentRepository;
import com.accenture.aria.repository.LawRepository;
import com.accenture.aria.repository.MemeRepository;
import com.accenture.aria.service.IExcuseService;
import com.accenture.aria.service.ai.GeminiService;
import com.accenture.aria.service.mapper.LawMapper;
import com.accenture.aria.service.mapper.MemeMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class ExcuseService implements IExcuseService {
    
    private final FragmentRepository fragmentRepository;
    private final MemeRepository memeRepository;
    private final LawRepository lawRepository;
    private final GeminiService geminiService;
    
    public ExcuseService(FragmentRepository fragmentRepository, 
                        MemeRepository memeRepository,
                        LawRepository lawRepository,
                        GeminiService geminiService) {
        this.fragmentRepository = fragmentRepository;
        this.memeRepository = memeRepository;
        this.lawRepository = lawRepository;
        this.geminiService = geminiService;
    }
    
    /**
     * Genera una excusa técnica combinando fragmentos aleatorios.
     * 
     * @param seed semilla para generar siempre la misma excusa (opcional)
     * @param role rol para filtrar fragmentos (opcional)
     * @return excusa con 4 fragmentos (contexto, causa, consecuencia, recomendación)
     */
    @Override
    public ExcuseResponseDTO generateExcuse(Long seed, Role role) {
        log.debug("Generating excuse with seed: {} and role: {}", seed, role);
        Random random = seed != null ? new Random(seed) : new Random();
        
        Fragment contexto = selectRandomFragment(FragmentType.CONTEXTO, role, random);
        Fragment causa = selectRandomFragment(FragmentType.CAUSA, role, random);
        Fragment consecuencia = selectRandomFragment(FragmentType.CONSECUENCIA, role, random);
        Fragment recomendacion = selectRandomFragment(FragmentType.RECOMENDACION, role, random);
        
        ExcuseResponseDTO excuse = ExcuseResponseDTO.builder()
                .contexto(contexto.getText())
                .causa(causa.getText())
                .consecuencia(consecuencia.getText())
                .recomendacion(recomendacion.getText())
                .build();
        
        log.info("Excuse generated successfully with seed: {}", seed);
        return excuse;
    }
    
    /**
     * Genera una excusa con un meme tech argentino incluido.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role rol para filtrar fragmentos (opcional)
     * @return excusa con meme aleatorio
     */
    @Override
    public ExcuseResponseDTO generateExcuseWithMeme(Long seed, Role role) {
        log.debug("Generating excuse with meme. Seed: {}, Role: {}", seed, role);
        Random random = seed != null ? new Random(seed) : new Random();
        ExcuseResponseDTO excuse = generateExcuse(seed, role);
        
        List<Meme> memes = memeRepository.findAll();
        if (!memes.isEmpty()) {
            Meme randomMeme = memes.get(random.nextInt(memes.size()));
            excuse.setMeme(MemeMapper.toResponse(randomMeme));
            log.debug("Added meme: {}", randomMeme.getCharacter());
        }
        
        log.info("Excuse with meme generated successfully");
        return excuse;
    }
    
    /**
     * Genera una excusa con una ley o axioma IT incluido.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role rol para filtrar fragmentos (opcional)
     * @return excusa con ley/axioma aleatorio
     */
    @Override
    public ExcuseResponseDTO generateExcuseWithLaw(Long seed, Role role) {
        log.debug("Generating excuse with law. Seed: {}, Role: {}", seed, role);
        Random random = seed != null ? new Random(seed) : new Random();
        ExcuseResponseDTO excuse = generateExcuse(seed, role);
        
        List<Law> laws = lawRepository.findAll();
        if (!laws.isEmpty()) {
            Law randomLaw = laws.get(random.nextInt(laws.size()));
            excuse.setLaw(LawMapper.toResponse(randomLaw));
            log.debug("Added law: {} ({})", randomLaw.getName(), randomLaw.getCategory());
        }
        
        log.info("Excuse with law generated successfully");
        return excuse;
    }
    
    /**
     * Genera una excusa ULTRA con meme y ley simultáneamente.
     * 
     * @param seed semilla para reproducibilidad (opcional)
     * @param role rol para filtrar fragmentos (opcional)
     * @return excusa completa con meme y ley
     */
    @Override
    public ExcuseResponseDTO generateExcuseUltra(Long seed, Role role) {
        log.debug("Generating ULTRA excuse with meme AND law. Seed: {}, Role: {}", seed, role);
        Random random = seed != null ? new Random(seed) : new Random();
        ExcuseResponseDTO excuse = generateExcuse(seed, role);
        
        List<Meme> memes = memeRepository.findAll();
        if (!memes.isEmpty()) {
            Meme randomMeme = memes.get(random.nextInt(memes.size()));
            excuse.setMeme(MemeMapper.toResponse(randomMeme));
        }
        
        List<Law> laws = lawRepository.findAll();
        if (!laws.isEmpty()) {
            Law randomLaw = laws.get(random.nextInt(laws.size()));
            excuse.setLaw(LawMapper.toResponse(randomLaw));
        }
        
        log.info("ULTRA excuse generated successfully (with meme and law)");
        return excuse;
    }
    
    /**
     * Genera una excusa técnica usando Google Gemini AI.
     * Si la API falla, usa generación tradicional como fallback.
     * 
     * @param request configuración con rol, contexto, creatividad, meme y ley
     * @return excusa generada por IA o fallback tradicional
     */
    @Override
    public ExcuseResponseDTO generateExcuseWithAI(ExcuseAIRequestDTO request) {
        log.info("Generating AI-powered excuse. Role: {}, Creativity: {}, IncludeMeme: {}, IncludeLaw: {}", 
                request.getRole(), request.getCreativity(), request.getIncludeMeme(), request.getIncludeLaw());
        
        try {
            // Recolectar fragmentos de referencia del dominio
            List<Fragment> referenceFragments = collectReferenceFragments(request.getRole());
            
            // Seleccionar meme y ley si se solicita
            Meme selectedMeme = request.getIncludeMeme() ? selectRandomMeme() : null;
            Law selectedLaw = request.getIncludeLaw() ? selectRandomLaw() : null;
            
            // Llamar a Gemini AI
            Map<String, String> aiExcuse = geminiService.generateExcuseText(
                    request.getRole(), 
                    request.getContext(), 
                    referenceFragments, 
                    selectedLaw, 
                    selectedMeme
            );
            
            // Construir respuesta
            ExcuseResponseDTO.ExcuseResponseDTOBuilder builder = ExcuseResponseDTO.builder()
                    .contexto(aiExcuse.get("contexto"))
                    .causa(aiExcuse.get("causa"))
                    .consecuencia(aiExcuse.get("consecuencia"))
                    .recomendacion(aiExcuse.get("recomendacion"));
            
            if (selectedMeme != null) {
                builder.meme(MemeMapper.toResponse(selectedMeme));
            }
            if (selectedLaw != null) {
                builder.law(LawMapper.toResponse(selectedLaw));
            }
            
            log.info("AI excuse generated successfully");
            return builder.build();
            
        } catch (GeminiException e) {
            log.warn("Gemini API failed: {}. Falling back to traditional generation", e.getMessage());
            
            // Fallback a generación tradicional
            if (request.getIncludeMeme() && request.getIncludeLaw()) {
                return generateExcuseUltra(null, request.getRole());
            } else if (request.getIncludeMeme()) {
                return generateExcuseWithMeme(null, request.getRole());
            } else if (request.getIncludeLaw()) {
                return generateExcuseWithLaw(null, request.getRole());
            } else {
                return generateExcuse(null, request.getRole());
            }
        }
    }
    
    /**
     * Recolecta fragmentos de referencia para el prompt de IA.
     * Selecciona 2 fragmentos aleatorios de cada tipo.
     */
    private List<Fragment> collectReferenceFragments(Role role) {
        List<Fragment> references = new ArrayList<>();
        Random random = new Random();
        
        for (FragmentType type : FragmentType.values()) {
            List<Fragment> fragments = fragmentRepository.findByTypeAndRoleIn(type, Arrays.asList(role, Role.ALL));
            if (!fragments.isEmpty()) {
                // Tomar hasta 2 fragmentos aleatorios de cada tipo
                references.add(fragments.get(random.nextInt(fragments.size())));
                if (fragments.size() > 1) {
                    Fragment second = fragments.get(random.nextInt(fragments.size()));
                    if (!second.equals(references.get(references.size() - 1))) {
                        references.add(second);
                    }
                }
            }
        }
        
        return references;
    }
    
    private Meme selectRandomMeme() {
        List<Meme> memes = memeRepository.findAll();
        if (memes.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return memes.get(random.nextInt(memes.size()));
    }
    
    private Law selectRandomLaw() {
        List<Law> laws = lawRepository.findAll();
        if (laws.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return laws.get(random.nextInt(laws.size()));
    }
    
    private Fragment selectRandomFragment(FragmentType type, Role role, Random random) {
        List<Fragment> fragments;
        
        if (role != null) {
            // Buscar fragmentos del rol específico o del rol ALL
            fragments = fragmentRepository.findByTypeAndRoleIn(type, Arrays.asList(role, Role.ALL));
        } else {
            // Sin filtro de rol, tomar todos
            fragments = fragmentRepository.findByType(type);
        }
        
        if (fragments.isEmpty()) {
            throw new RuntimeException("No fragments found for type: " + type + 
                    (role != null ? " and role: " + role : ""));
        }
        
        return fragments.get(random.nextInt(fragments.size()));
    }
}
