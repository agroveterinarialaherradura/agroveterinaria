package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Usuarios.UsuarioExternasService;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UsuarioExternasService usuarioExternasService;

    @Autowired
    private VisitasExternasService visitasExternasService;

    // ===== MOSTRAR FORMULARIO DE REGISTRO =====
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        // Si el modelo ya tiene un usuario, no lo sobrescribimos
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuarios());
        }
        return "registro";
    }

    // ===== REGISTRO DE USUARIO =====
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuarios usuario,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        
        // Si hay errores de validación, regresamos al formulario con los errores
        if (result.hasErrors()) {
            // Mantenemos el objeto usuario en el modelo para mostrar los datos ingresados
            model.addAttribute("usuario", usuario);
            return "registro";
        }

        try {
            // Delegar el registro a Heroku vía API
            Map<String, String> response = usuarioExternasService.registrarUsuario(usuario);
            
            if (response.containsKey("error")) {
                // Si hay error del servicio externo, lo mostramos
                model.addAttribute("error", response.get("error"));
                model.addAttribute("usuario", usuario);
                return "registro";
            }
            
            // Registro exitoso
            redirectAttributes.addFlashAttribute("mensaje", 
                response.getOrDefault("mensaje", "Registro exitoso. Revisa tu correo para activar tu cuenta."));
            return "redirect:/login";
            
        } catch (Exception e) {
            System.err.println("Error en registro: " + e.getMessage());
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            return "registro";
        }
    }

    // ===== PANEL DE VISITAS =====
    @GetMapping("/visitas")
    public String visitas(Model model) {
        try {
            ResumenVisitas statsGlobal = visitasExternasService.procesarVisitas(Constantes.ORIGEN_MAY);
            List<Map<String, Object>> seccionesStats = visitasExternasService.findVisitsGroupByOrigen();
            List<Map<String, Object>> dataBD = visitasExternasService.findSeccionesDinamicas();

            List<Map<String, String>> seccionesSelector = new ArrayList<>();

            Map<String, String> sec1 = new HashMap<>();
            sec1.put("id", Constantes.ORIGEN_MAY);
            sec1.put("nombre", "Inicio");
            seccionesSelector.add(sec1);

            Map<String, String> sec4 = new HashMap<>();
            sec4.put("id", "blog");
            sec4.put("nombre", "Blog");
            seccionesSelector.add(sec4);

            for (Map<String, Object> row : dataBD) {
                String nombre = (String) row.get("nombre");
                String id = (String) row.get("id");
                Map<String, String> sec = new HashMap<>();
                sec.put("id", id);
                sec.put("nombre", nombre);
                seccionesSelector.add(sec);
            }

            model.addAttribute("statsGlobal", statsGlobal != null ? statsGlobal : new ResumenVisitas());
            model.addAttribute("seccionesStats", seccionesStats);
            model.addAttribute("seccionesSelector", seccionesSelector);

        } catch (Exception e) {
            model.addAttribute("statsGlobal", new ResumenVisitas());
            model.addAttribute("seccionesStats", new ArrayList<>());
            model.addAttribute("seccionesSelector", new ArrayList<>());
        }
        return "visitas";
    }

    // ===== REDIRECCIÓN PARA CLAVE =====
    @PostMapping("/clave")
    public String addClave(Usuarios usuario, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", 
            "Esta funcionalidad no está disponible en Railway. Por favor, regístrate en la página principal.");
        return "redirect:/";
    }
}