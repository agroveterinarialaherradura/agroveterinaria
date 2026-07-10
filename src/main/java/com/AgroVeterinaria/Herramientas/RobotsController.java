package com.AgroVeterinaria.Herramientas;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RobotsController {
//String global ="";

	@RequestMapping(value={"/robots.txt", "/robot.txt"})
	@ResponseBody
	public String getRobotsTxt() {
	    return "User-agent: *\n" +
	    		"Allow: /\n" +
	    		"Sitemap: " + Constantes.URL_BASE +
	            "";
	}
}