package com.AgroVeterinaria.Registro.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Registro.Blogs.BlogAsyncService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private BlogAsyncService blogAsyncService;
    
    @GetMapping("/reintento/{mail}")
	public String index(@PathVariable String mail) {
    	Usuarios dto = new Usuarios();
    	dto.setEmail(mail);
    	try {
			usuarioService.reintentarCorreo(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "correo_enviado";
	}
    
    @GetMapping("/reintentoBlog/{mail}")
	public String reintentarBlog(@PathVariable String mail) {
    	Blog dto = new Blog();
    	dto.setEmail(mail);
    	try {
    		blogAsyncService.reProcesarMail(mail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "correo_enviado";
	}
}