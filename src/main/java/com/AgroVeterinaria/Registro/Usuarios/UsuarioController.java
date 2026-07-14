package com.AgroVeterinaria.Registro.Usuarios;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.AgroVeterinaria.Registro.Usuarios.UsuarioExternasService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioExternasService usuarioExternasService;

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
        // Usamos el mismo servicio de reintento de correo, ya que Heroku maneja ambos
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
}