package com.AgroVeterinaria.Registro.Productos;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.AgroVeterinaria.DTO.ContactosRequestDTO;

@Service
public class ContactosExternasService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.heroku.api.url}")
    private String herokuApiUrl;

    public Map<String, Object> enviarContacto(ContactosRequestDTO dto) {
        try {
            String url = herokuApiUrl + "/newContacto";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ContactosRequestDTO> requestEntity = new HttpEntity<>(dto, headers);
            return restTemplate.postForObject(url, requestEntity, Map.class);
        } catch (Exception e) {
            System.err.println("Error al enviar contacto a Heroku: " + e.getMessage());
            // ❌ Java 9+
            //Map<String, Boolean> response = Map.of("exito", false, "mensaje", "Error");
            // ✅ Java 8
            Map<String, Object> response = new HashMap<>();
            response.put("exito", false);
            response.put("mensaje", "Error al enviar el contacto");
            return response;
        }
    }
}