package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        return "inicio";
    }
      
    @GetMapping("/api/stats/inicio")
    @ResponseBody
    public ResumenVisitas getStatsInicio() {
        try {
            return visitasExternasService.procesarVisitas(Constantes.ORIGEN_MAY);
        } catch (Exception e) {
            System.err.println("Error al obtener stats: " + e.getMessage());
            return new ResumenVisitas();
        }
    }
    
    @GetMapping("/api/stats/blog")
    @ResponseBody
    public ResumenVisitas getStatsBlog() {
        try {
            return visitasExternasService.procesarVisitas("blog");
        } catch (Exception e) {
            System.err.println("Error al obtener stats del blog: " + e.getMessage());
            return new ResumenVisitas();
        }
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
    public String login(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuarios());
        }
        return "login";
    }
}