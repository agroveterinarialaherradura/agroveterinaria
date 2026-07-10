package com.AgroVeterinaria.Registro.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.AgroVeterinaria.DTO.Lead;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;
import com.AgroVeterinaria.Registro.Visitas.VisitasService;

@Controller
public class VisitasProductos {

    @Autowired
    private VisitasService visitasService;

    @GetMapping("/Portafolio de Servicios Web")
    public String mostrarPortafolio(Model model) {
        ResumenVisitasProductos stats = visitasService.procesarVisitasProductos("PortafolioServicioWeb").join();
        model.addAttribute("stats", stats);
        model.addAttribute("lead", new Lead());
        return "productos/PortafolioServicioWeb";
    }
}