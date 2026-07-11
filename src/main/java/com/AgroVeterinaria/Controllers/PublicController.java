package com.AgroVeterinaria.Controllers;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.DTO.WhatsAppRequest;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	private VisitasExternasService visitasExternasService;
    
	@GetMapping({"/", "/index"})
	public String home(Model model, HttpServletRequest request) {
	    request.getSession(false);
	    LocalDate hoy = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
	    model.addAttribute("fecha", hoy.format(formatter));
	    model.addAttribute("dia", hoy.getDayOfMonth());
	    model.addAttribute("mes", hoy.getMonth().getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "MX")));
	    model.addAttribute("anio", hoy.getYear());
	    model.addAttribute("usuario", new Usuarios());

	    // ✅ Usar el servicio externo
	    ResumenVisitas stats = visitasExternasService.procesarVisitas(Constantes.ORIGEN_MAY);
	    model.addAttribute("stats", stats != null ? stats : new ResumenVisitas());

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
}