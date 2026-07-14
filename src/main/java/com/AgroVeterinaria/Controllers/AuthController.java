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
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Registro.Usuarios.UsuarioExternasService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UsuarioExternasService usuarioExternasService;

    @Autowired
    private VisitasExternasService visitasExternasService;

    // ===== REGISTRO DE USUARIO =====
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuarios usuario,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "inicio";
        }

        // Delegar el registro a Heroku vía API
        Map<String, String> response = usuarioExternasService.registrarUsuario(usuario);
        if (response.containsKey("error")) {
            redirectAttributes.addFlashAttribute("error", response.get("error"));
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("mensaje", response.get("mensaje"));
        return "redirect:/";
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

    // ===== (OPCIONAL) Si necesitas la funcionalidad /clave, la delegas a Heroku =====
    // Pero Railway no debería manejar esto, mejor que el usuario vaya directamente a Heroku.
    // Puedes redirigir a la URL de Heroku para ese flujo.
    @PostMapping("/clave")
    public String addClave(Usuarios usuario, RedirectAttributes redirectAttributes) {
        // Redirigir a Heroku para que maneje el envío de correo
        // Ejemplo: return "redirect:https://tupaginawebhoy-fe12dd345e09.herokuapp.com/clave";
        // O bien, usar el servicio externo si existe un endpoint para esto.
        redirectAttributes.addFlashAttribute("error", "Esta funcionalidad no está disponible en Railway. Por favor, regístrate en la página principal.");
        return "redirect:/";
    }
}