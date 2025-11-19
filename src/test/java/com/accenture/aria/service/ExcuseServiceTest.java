package com.accenture.aria.service;

import com.accenture.aria.dto.ExcuseResponseDTO;
import com.accenture.aria.model.*;
import com.accenture.aria.repository.*;
import com.accenture.aria.service.ai.GeminiService;
import com.accenture.aria.service.implementations.ExcuseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ExcuseServiceTest {
    
    @Mock
    private FragmentRepository fragmentRepository;
    
    @Mock
    private MemeRepository memeRepository;
    
    @Mock
    private LawRepository lawRepository;
    
    @Mock
    private GeminiService geminiService;
    
    private IExcuseService excuseService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        excuseService = new ExcuseService(fragmentRepository, memeRepository, lawRepository, geminiService);
        
        // Mock fragments para cada tipo
        List<Fragment> contextos = Arrays.asList(
            Fragment.builder().id(1L).type(FragmentType.CONTEXTO).text("Estábamos deployando").role(Role.ALL).build(),
            Fragment.builder().id(2L).type(FragmentType.CONTEXTO).text("Durante la daily").role(Role.ALL).build()
        );
        List<Fragment> causas = Arrays.asList(
            Fragment.builder().id(3L).type(FragmentType.CAUSA).text("el token expiró").role(Role.ALL).build(),
            Fragment.builder().id(4L).type(FragmentType.CAUSA).text("alguien mergeó sin revisar").role(Role.ALL).build()
        );
        List<Fragment> consecuencias = Arrays.asList(
            Fragment.builder().id(5L).type(FragmentType.CONSECUENCIA).text("tuvimos que hacer rollback").role(Role.ALL).build(),
            Fragment.builder().id(6L).type(FragmentType.CONSECUENCIA).text("perdimos toda la tarde").role(Role.ALL).build()
        );
        List<Fragment> recomendaciones = Arrays.asList(
            Fragment.builder().id(7L).type(FragmentType.RECOMENDACION).text("automatizar la rotación").role(Role.ALL).build(),
            Fragment.builder().id(8L).type(FragmentType.RECOMENDACION).text("implementar code review").role(Role.ALL).build()
        );
        
        when(fragmentRepository.findByTypeAndRoleIn(eq(FragmentType.CONTEXTO), anyList())).thenReturn(contextos);
        when(fragmentRepository.findByTypeAndRoleIn(eq(FragmentType.CAUSA), anyList())).thenReturn(causas);
        when(fragmentRepository.findByTypeAndRoleIn(eq(FragmentType.CONSECUENCIA), anyList())).thenReturn(consecuencias);
        when(fragmentRepository.findByTypeAndRoleIn(eq(FragmentType.RECOMENDACION), anyList())).thenReturn(recomendaciones);
        
        when(fragmentRepository.findByType(FragmentType.CONTEXTO)).thenReturn(contextos);
        when(fragmentRepository.findByType(FragmentType.CAUSA)).thenReturn(causas);
        when(fragmentRepository.findByType(FragmentType.CONSECUENCIA)).thenReturn(consecuencias);
        when(fragmentRepository.findByType(FragmentType.RECOMENDACION)).thenReturn(recomendaciones);
        
        // Mock memes y laws
        List<Meme> memes = Arrays.asList(
            Meme.builder().id(1L).character("Tano Pasman").description("¿CÓMO QUE FALLÓ?").build()
        );
        List<Law> laws = Arrays.asList(
            Law.builder().id(1L).name("Murphy").description("Si algo puede salir mal, saldrá mal").category(LawCategory.MURPHY).build()
        );
        
        when(memeRepository.findAll()).thenReturn(memes);
        when(lawRepository.findAll()).thenReturn(laws);
    }
    
    @Test
    void testGenerateExcuseWithSeed_ShouldBeReproducible() {
        // Given
        Long seed = 12345L;
        
        // When
        ExcuseResponseDTO excuse1 = excuseService.generateExcuse(seed, null);
        ExcuseResponseDTO excuse2 = excuseService.generateExcuse(seed, null);
        
        // Then
        assertNotNull(excuse1);
        assertNotNull(excuse2);
        assertEquals(excuse1.getContexto(), excuse2.getContexto(), 
            "Las excusas con el mismo seed deben ser idénticas");
        assertEquals(excuse1.getCausa(), excuse2.getCausa(), 
            "Las excusas con el mismo seed deben ser idénticas");
        assertEquals(excuse1.getConsecuencia(), excuse2.getConsecuencia(), 
            "Las excusas con el mismo seed deben ser idénticas");
        assertEquals(excuse1.getRecomendacion(), excuse2.getRecomendacion(), 
            "Las excusas con el mismo seed deben ser idénticas");
    }
    
    @Test
    void testGenerateExcuse_ShouldContainAllParts() {
        // When
        ExcuseResponseDTO excuse = excuseService.generateExcuse(null, null);
        
        // Then
        assertNotNull(excuse, "La excusa no debe ser null");
        assertNotNull(excuse.getContexto(), "El contexto no debe ser null");
        assertNotNull(excuse.getCausa(), "La causa no debe ser null");
        assertNotNull(excuse.getConsecuencia(), "La consecuencia no debe ser null");
        assertNotNull(excuse.getRecomendacion(), "La recomendación no debe ser null");
        assertFalse(excuse.getContexto().isEmpty(), "El contexto no debe estar vacío");
        assertFalse(excuse.getCausa().isEmpty(), "La causa no debe estar vacía");
        assertFalse(excuse.getConsecuencia().isEmpty(), "La consecuencia no debe estar vacía");
        assertFalse(excuse.getRecomendacion().isEmpty(), "La recomendación no debe estar vacía");
    }
    
    @Test
    void testGenerateExcuseWithRole_ShouldUseFilteredFragments() {
        // Given
        Role role = Role.DEV;
        Long seed = 999L;
        
        // When
        ExcuseResponseDTO excuse = excuseService.generateExcuse(seed, role);
        
        // Then
        assertNotNull(excuse);
        assertNotNull(excuse.getContexto());
        assertNotNull(excuse.getCausa());
        assertNotNull(excuse.getConsecuencia());
        assertNotNull(excuse.getRecomendacion());
    }
    
    @Test
    void testGenerateExcuseWithMeme_ShouldIncludeMeme() {
        // Given
        Long seed = 777L;
        
        // When
        ExcuseResponseDTO excuse = excuseService.generateExcuseWithMeme(seed, null);
        
        // Then
        assertNotNull(excuse);
        assertNotNull(excuse.getMeme(), "La excusa debe incluir un meme");
        assertNotNull(excuse.getMeme().getCharacter());
        assertNotNull(excuse.getMeme().getDescription());
    }
    
    @Test
    void testGenerateExcuseWithLaw_ShouldIncludeLaw() {
        // Given
        Long seed = 888L;
        
        // When
        ExcuseResponseDTO excuse = excuseService.generateExcuseWithLaw(seed, null);
        
        // Then
        assertNotNull(excuse);
        assertNotNull(excuse.getLaw(), "La excusa debe incluir una ley");
        assertNotNull(excuse.getLaw().getName());
        assertNotNull(excuse.getLaw().getDescription());
        assertNotNull(excuse.getLaw().getCategory());
    }
    
    @Test
    void testGenerateExcuseUltra_ShouldIncludeMemeAndLaw() {
        // Given
        Long seed = 555L;
        
        // When
        ExcuseResponseDTO excuse = excuseService.generateExcuseUltra(seed, null);
        
        // Then
        assertNotNull(excuse);
        assertNotNull(excuse.getContexto());
        assertNotNull(excuse.getCausa());
        assertNotNull(excuse.getConsecuencia());
        assertNotNull(excuse.getRecomendacion());
        assertNotNull(excuse.getMeme(), "La excusa ULTRA debe incluir un meme");
        assertNotNull(excuse.getLaw(), "La excusa ULTRA debe incluir una ley");
    }
    
    @Test
    void testDifferentSeeds_ShouldGenerateDifferentExcuses() {
        // Given
        Long seed1 = 100L;
        Long seed2 = 200L;
        
        // When
        ExcuseResponseDTO excuse1 = excuseService.generateExcuse(seed1, null);
        ExcuseResponseDTO excuse2 = excuseService.generateExcuse(seed2, null);
        
        // Then
        assertNotNull(excuse1);
        assertNotNull(excuse2);
        
        // Al menos uno de los componentes debería ser diferente
        boolean isDifferent = !excuse1.getContexto().equals(excuse2.getContexto()) ||
                              !excuse1.getCausa().equals(excuse2.getCausa()) ||
                              !excuse1.getConsecuencia().equals(excuse2.getConsecuencia()) ||
                              !excuse1.getRecomendacion().equals(excuse2.getRecomendacion());
        
        assertTrue(isDifferent, "Seeds diferentes deberían generar excusas diferentes");
    }
}
