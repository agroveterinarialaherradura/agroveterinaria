package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AgroVeterinaria.DTO.WhatsAppRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendWhatsAppMessage(@RequestBody WhatsAppRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar número
            String phone = request.getPhoneNumber().replaceAll("[^0-9]", "");
            if (phone.length() < 10) {
                response.put("success", false);
                response.put("error", "Número inválido. Debe tener al menos 10 dígitos.");
                return ResponseEntity.badRequest().body(response);
            }

            // Enviar mensaje
            boolean sent = whatsAppService.sendMessage(phone, request.getMessage());
            
            if (sent) {
                response.put("success", true);
                response.put("message", "Mensaje enviado correctamente a WhatsApp");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "No se pudo enviar el mensaje. Verifica las credenciales.");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}