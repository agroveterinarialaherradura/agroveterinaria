package com.AgroVeterinaria.Herramientas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import com.SQLSpring.Usuarios.SendMail;

@Controller
public class EmailController {
	
	@Autowired
	private EmailService EmailService;
	
	@GetMapping("/mail")
	public String index() {
		return "send_mail";
	}

	@GetMapping("/sendMail/{mail}")
  //public String sendMail(@RequestParam("name") String nombre, @RequestParam("mail") String Correo) {
	public String sendMail(@PathVariable String mail) {
		String mensaje = "Mensaje enviado a "+mail;
		System.out.println("Correo Enviado a:"+mail);
		EmailService.sendMail("no-repli@betha.com", mail, "Test", mensaje);
		return "correo_enviado";
	}

	@GetMapping("/formMail")
	public String formMail(@RequestParam("username") String username, @RequestParam("mail") String Correo) {
		String mensaje = "Mensaje enviado a "+ username + " : "+ Correo;
		EmailService.sendMail("no-repli@betha.com", Correo, "Test", mensaje);
		return "correo_enviado";
	}
}