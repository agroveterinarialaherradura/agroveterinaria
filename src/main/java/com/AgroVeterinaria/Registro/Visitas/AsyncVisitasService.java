package com.AgroVeterinaria.Registro.Visitas;
import com.AgroVeterinaria.DTO.Visitas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncVisitasService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.visitas.api.url}")
    private String externalApiUrl;

    @Async("taskExecutor")  // ← Usa el pool que definiste en AsyncConfig
    public void enviarVisita(Visitas visita) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Visitas> requestEntity = new HttpEntity<>(visita, headers);
            restTemplate.postForEntity(externalApiUrl, requestEntity, String.class);
            System.out.println("✅ Visita enviada en segundo plano: " + visita.getOrigen());
        } catch (Exception e) {
            System.err.println("❌ Error al enviar visita (asíncrono): " + e.getMessage());
        }
    }
}