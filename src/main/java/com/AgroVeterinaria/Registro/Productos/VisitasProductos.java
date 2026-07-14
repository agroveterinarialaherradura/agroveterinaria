package com.AgroVeterinaria.Registro.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.AgroVeterinaria.DTO.Lead;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;

@Controller
public class VisitasProductos {

    @Autowired
    private VisitasExternasService visitasExternasService;

    /*--
     * Desde BlogController
     * Primero hay que filtrar los productos para redirigirlo de manera estática aquí:
     * 
     * @GetMapping("/{urlBuscar:^(?!sitemap\\.xml$|robots\\.txt$|favicon\\.ico$|Portafolio de Servicios Web$).*$}")
     * 
     * Portafolio de Servicios Web
     *  
    --*/
    @GetMapping("/Portafolio de Servicios Web")
    public String mostrarPortafolio(Model model) {
    	String origen = "Portafolio de Servicios Web";//urlBuscar en Articulos
        ResumenVisitasProductos stats = visitasExternasService.procesarVisitasProductos(origen);
        model.addAttribute("stats", stats);
        model.addAttribute("lead", new Lead());
        return "productos/PortafolioServicioWeb";
    }
    
    
}