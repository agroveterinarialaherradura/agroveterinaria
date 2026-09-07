package com.AgroVeterinaria.Registro.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioExternasService usuarioExternasService;

    @Value("${external.heroku.api.url}")
    private String herokuApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    // Reintento de correo (redirige a Heroku)
    @GetMapping("/reintento/{mail}")
    public String reintentarCorreo(@PathVariable String mail, RedirectAttributes redirectAttributes) {
        Map<String, String> response = usuarioExternasService.reintentarCorreo(mail);
        if (response.containsKey("error")) {
            redirectAttributes.addFlashAttribute("error", response.get("error"));
        } else {
            redirectAttributes.addFlashAttribute("mensaje", response.get("mensaje"));
        }
        return "redirect:/";
    }

    // Reintento de correo para blog (redirige a Heroku)
    @GetMapping("/reintentoBlog/{mail}")
    public String reintentarBlog(@PathVariable String mail, RedirectAttributes redirectAttributes) {
        Map<String, String> response = usuarioExternasService.reintentarCorreo(mail);
        if (response.containsKey("error")) {
            redirectAttributes.addFlashAttribute("error", response.get("error"));
        } else {
            redirectAttributes.addFlashAttribute("mensaje", response.get("mensaje"));
        }
        return "redirect:/";
    }

    // Verificar si el email ya existe
    @GetMapping("/api/existe-usuario")
    @ResponseBody
    public boolean existeUsuario(@RequestParam String email) {
        return usuarioExternasService.existeUsuario(email);
    }

    // ===== VALIDACIÓN DE CORREO CON VISTA PROPIETA =====
    @GetMapping("/valida/{clave}")
    public String validarUsuario(@PathVariable String clave, Model model) {
        try {
            System.out.println("📧 Validando usuario desde Railway: " + clave);
            System.out.println("📧 URL Base Heroku: " + herokuApiUrl);

            // Llamar a Heroku para validar
            String url = herokuApiUrl + "/usuarios/validar/" + clave;
            System.out.println("🔄 Llamando a Heroku: " + url);

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();

                if (Boolean.TRUE.equals(body.get("exito"))) {
                    // ✅ Éxito - Mostrar correoValidado.html
                    model.addAttribute("mensaje", body.get("mensaje"));
                    return "correoValidado";
                } else {
                    // ❌ Error - Mostrar correoNoValidado.html
                    model.addAttribute("error", body.get("mensaje"));
                    return "correoNoValidado";
                }
            } else {
                model.addAttribute("error", "Error al validar el correo");
                return "correoNoValidado";
            }

        } catch (HttpClientErrorException e) {
            // Capturar errores HTTP específicos (409, 404, 403, etc.)
            System.err.println("❌ Error HTTP al validar usuario: " + e.getStatusCode() + " - " + e.getMessage());
            
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                // 409: El correo ya fue validado anteriormente
                model.addAttribute("error", "Este correo ya ha sido validado anteriormente. Por favor, inicia sesión.");
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // 404: Correo no registrado
                model.addAttribute("error", "El correo no está registrado en nuestro sistema.");
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                // 403: Correo bloqueado o dado de baja
                model.addAttribute("error", "Este correo está bloqueado o dado de baja.");
            } else if (e.getStatusCode() == HttpStatus.GONE) {
                // 410: Enlace expirado
                model.addAttribute("error", "El enlace de validación ha expirado. Solicita uno nuevo.");
            } else {
                model.addAttribute("error", "Error al validar el correo: " + e.getMessage());
            }
            return "correoNoValidado";

        } catch (Exception e) {
            System.err.println("❌ Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al validar el correo: " + e.getMessage());
            return "correoNoValidado";
        }
    }
}