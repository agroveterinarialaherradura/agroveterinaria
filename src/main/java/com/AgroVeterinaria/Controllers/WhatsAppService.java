package com.AgroVeterinaria.Controllers;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    // === CONFIGURACIÓN DE WHATSAPP CLOUD API (META) ===
    // Si NO tienes credenciales, cambia "USAR_API_REAL" a false
    private static final boolean USAR_API_REAL = true;  // <--- CAMBIA A true SI TIENES CREDENCIALES
    
    private static final String WHATSAPP_API_URL = "https://graph.facebook.com/v18.0/1173797679150302/messages";
    private static final String ACCESS_TOKEN = "EAAjYvN14TqwBR2YHfESYdZCKjvgbz8OioIsgEBFtiPl8tTwLasL64LjznZAp74bBmUNljVRvFDg2pgGjsXsIFZA7906O4Pe37LF6igpHDWx3YKorz0ZAkbfZCFh0FpRGyoClF1ZAA5ZBxL4vMTf8hjz7GZADsnnXNoMx8ZCvGTQhY25CylA6ddrrZCFcSdgaYHiz4HgVUC5bRmCfqGz4UIOBFYFIgcpxYSXoItZBq9ItDCZCFRpeg6V5";  // Reemplaza con tu token
    
    private static final String FROM_PHONE_NUMBER_ID = "1173797679150302";
    private static final String PHONE_NUMBER_ID = "1173797679150302";
    
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean sendMessage(String phoneNumber, String text) {
        try {
            // Limpiar el número y agregar código de país si no lo tiene
            String to = phoneNumber.replaceAll("[^0-9]", "");
            if (!to.startsWith("52")) {
                to = "52" + to; // Código de México por defecto
            }

            // Construir el payload (compatible con Java 8)
            Map<String, Object> payload = new HashMap<>();
            payload.put("messaging_product", "whatsapp");
            payload.put("to", to);
            payload.put("type", "text");

            Map<String, String> textObj = new HashMap<>();
            textObj.put("body", text);
            payload.put("text", textObj);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(ACCESS_TOKEN);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            String url = WHATSAPP_API_URL.replace("{phone-number-id}", PHONE_NUMBER_ID);

            // Enviar la solicitud
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("✅ WhatsApp response: " + response.getBody());
            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método real para WhatsApp Cloud API
    private boolean sendViaWhatsAppAPI(String toPhone, String text) {
        try {
            // El número debe estar en formato internacional sin '+'
            String to = toPhone.startsWith("52") ? toPhone : "52" + toPhone;

            Map<String, Object> payload = new HashMap<>();
            payload.put("messaging_product", "whatsapp");
            payload.put("to", to);
            payload.put("type", "text");

            Map<String, String> textObj = new HashMap<>();
            textObj.put("body", text);
            payload.put("text", textObj);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(ACCESS_TOKEN);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            String url = WHATSAPP_API_URL.replace("{phone-number-id}", FROM_PHONE_NUMBER_ID);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}