package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.DTO.WhatsAppRequest;
import com.AgroVeterinaria.Registro.Visitas.DataSourceVisitas;

import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublicController {
    
    @Autowired
    private DataSourceVisitas dataSourceVisitas;
    
    @Autowired
    private WhatsAppService whatsAppService;
    
    @GetMapping({"/", "/index"})
    public String home(Model model, HttpServletRequest request) {
    	request.getSession(false); // Crea la sesión si no existe
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        model.addAttribute("fecha", hoy.format(formatter));
        model.addAttribute("dia", hoy.getDayOfMonth());
        model.addAttribute("mes", hoy.getMonth().getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "MX")));
        model.addAttribute("anio", hoy.getYear());
        model.addAttribute("usuario", new Usuarios());
        // Traer resumen de la sección Inicio
        model.addAttribute("stats", dataSourceVisitas.findVisitasResumenByOrigen("TuPaginaWebHoy"));
        
        return "inicio";
    }
    
    @GetMapping("/aviso-privacidad")
    public String avisoPrivacidad() {
        return "aviso-privacidad";
    }

    @GetMapping("/cookies")
    public String cookies() {
        return "cookies";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> send(@RequestBody WhatsAppRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Forzar envío a tu número (para pruebas)
            String phoneNumber = "522949417462";  // Tu número con código de país
            String message = "Nuevo lead desde el landing: " + request.getMessage() + 
                             ". Teléfono del cliente: " + request.getPhoneNumber();
            
            boolean sent = whatsAppService.sendMessage(phoneNumber, message);
            response.put("success", sent);
            if (!sent) response.put("error", "No se pudo enviar");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}