package com.accenture.aria.config;

import com.accenture.aria.model.*;
import com.accenture.aria.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final FragmentRepository fragmentRepository;
    private final MemeRepository memeRepository;
    private final LawRepository lawRepository;
    private final ObjectMapper objectMapper;
    
    public DataLoader(FragmentRepository fragmentRepository,
                     MemeRepository memeRepository,
                     LawRepository lawRepository) {
        this.fragmentRepository = fragmentRepository;
        this.memeRepository = memeRepository;
        this.lawRepository = lawRepository;
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🦈 Cargando datos iniciales para Excusas Tech API...");
        loadLaws();
        loadMemes();
        loadFragments();
        System.out.println("✅ Datos cargados exitosamente!");
    }
    
    private void loadLaws() throws Exception {
        System.out.println("📚 Cargando leyes y axiomas...");
        loadLawsFromFile("data/murphy.json", LawCategory.MURPHY);
        loadLawsFromFile("data/hofstadter.json", LawCategory.HOFSTADTER);
        loadLawsFromFile("data/dilbert.json", LawCategory.DILBERT);
        loadLawsFromFile("data/devops_principles.json", LawCategory.DEVOPS);
        loadLawsFromFile("data/dev_axioms.json", LawCategory.AXIOM);
        System.out.println("   ✓ Leyes cargadas: " + lawRepository.count());
    }
    
    private void loadLawsFromFile(String filePath, LawCategory category) throws Exception {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        JsonNode jsonArray = objectMapper.readTree(inputStream);
        
        List<Law> laws = new ArrayList<>();
        for (JsonNode node : jsonArray) {
            String text = node.get("text").asText();
            String source = node.has("source") ? node.get("source").asText() : category.name();
            
            Law law = Law.builder()
                    .name(source)
                    .description(text)
                    .category(category)
                    .build();
            laws.add(law);
        }
        
        lawRepository.saveAll(laws);
    }
    
    private void loadMemes() throws Exception {
        System.out.println("😄 Cargando memes...");
        loadMemesFromFile("data/memes_argentinos.json", "Meme Argentino");
        loadMemesFromFile("data/argento-memes.json", "Meme Argento");
        loadMemesFromFile("data/dev-memes.json", "Dev Meme");
        System.out.println("   ✓ Memes cargados: " + memeRepository.count());
    }
    
    private void loadMemesFromFile(String filePath, String character) throws Exception {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        JsonNode jsonArray = objectMapper.readTree(inputStream);
        
        List<Meme> memes = new ArrayList<>();
        for (JsonNode node : jsonArray) {
            String text = node.get("text").asText();
            
            Meme meme = Meme.builder()
                    .character(character)
                    .description(text)
                    .build();
            memes.add(meme);
        }
        
        memeRepository.saveAll(memes);
    }
    
    private void loadFragments() {
        System.out.println("🧩 Cargando fragmentos de excusas...");
        List<Fragment> fragments = new ArrayList<>();
        
        // CONTEXTO (10 fragmentos)
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Estábamos deployando un hotfix crítico en viernes a la tarde", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Durante la daily standup del equipo", Role.DEV));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Mientras ejecutábamos los tests de regresión", Role.QA));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "En medio de la demo con el cliente", Role.PM));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Justo cuando estábamos por cerrar el sprint", Role.ALL));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Durante el análisis de métricas de producción", Role.SRE));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Al revisar el código del pull request", Role.DEV));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Mientras configurábamos el pipeline de CI/CD", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "En la retrospectiva del último sprint", Role.PM));
        fragments.add(createFragment(FragmentType.CONTEXTO, 
            "Durante el troubleshooting del incidente en producción", Role.SRE));
        
        // CAUSA (10 fragmentos)
        fragments.add(createFragment(FragmentType.CAUSA, 
            "el token de autenticación del CI/CD expiró", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "alguien mergeó sin revisar los tests", Role.DEV));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "encontramos un bug crítico que bloqueaba todo", Role.QA));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "el cliente cambió los requerimientos a último momento", Role.PM));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "la base de datos se quedó sin espacio", Role.SRE));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "hubo un conflicto de merge que nadie detectó", Role.DEV));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "el ambiente de staging estaba caído", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "los tests automatizados no cubrían ese caso", Role.QA));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "se actualizó una dependencia sin verificar la compatibilidad", Role.DEV));
        fragments.add(createFragment(FragmentType.CAUSA, 
            "el monitoreo no alertó a tiempo del problema", Role.SRE));
        
        // CONSECUENCIA (10 fragmentos)
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "tuvimos que hacer rollback de emergencia", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "perdimos toda la tarde debuggeando", Role.DEV));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "no pudimos cumplir con el deadline", Role.PM));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "los usuarios reportaron múltiples errores", Role.QA));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "el sistema estuvo inaccesible por 2 horas", Role.SRE));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "tuvimos que posponer el release", Role.PM));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "el equipo se quedó hasta tarde resolviendo el problema", Role.ALL));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "se generó deuda técnica que nadie quiere agarrar", Role.DEV));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "el pipeline estuvo bloqueado todo el día", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.CONSECUENCIA, 
            "tuvimos que escalar el incidente a management", Role.SRE));
        
        // RECOMENDACION (10 fragmentos)
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "automatizar la rotación de secretos y tokens", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "implementar code review obligatorio", Role.DEV));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "mejorar la cobertura de tests automatizados", Role.QA));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "establecer un proceso de gestión de cambios más riguroso", Role.PM));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "configurar alertas de monitoreo proactivas", Role.SRE));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "documentar los procedimientos de emergencia", Role.ALL));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "realizar retrospectivas post-incidente", Role.PM));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "implementar feature flags para despliegues más seguros", Role.DEVOPS));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "establecer ambientes de staging que repliquen producción", Role.SRE));
        fragments.add(createFragment(FragmentType.RECOMENDACION, 
            "adoptar prácticas de trunk-based development", Role.DEV));
        
        fragmentRepository.saveAll(fragments);
        System.out.println("   ✓ Fragmentos cargados: " + fragmentRepository.count());
    }
    
    private Fragment createFragment(FragmentType type, String text, Role role) {
        return Fragment.builder()
                .type(type)
                .text(text)
                .role(role)
                .build();
    }
}
