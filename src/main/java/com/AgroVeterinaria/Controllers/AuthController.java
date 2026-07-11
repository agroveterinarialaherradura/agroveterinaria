package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {
   
    @Autowired
    private VisitasExternasService visitasExternasService;
    
    @GetMapping("/visitas")
    public String visitas(Model model) {
        try {
            //ResumenVisitas statsPedidos = visitasService.procesarVisitas("pedidos").get();
        	ResumenVisitas statsGlobal = visitasExternasService.procesarVisitas(Constantes.ORIGEN_MAY);
            //List<Map<String, Object>> seccionesStats = dataSourceVisitas.findVisitsGroupByOrigen();
            List<Map<String, Object>> seccionesStats = visitasExternasService.findVisitsGroupByOrigen();
            
            // 🔥 NUEVO: dinámico desde BD
            //List<Map<String, Object>> dataBD = dataSourceVisitas.findSeccionesDinamicas();
            List<Map<String, Object>> dataBD = visitasExternasService.findSeccionesDinamicas();
            System.out.println(" DataBD: " + dataBD.toString());
            
            List<Map<String, String>> seccionesSelector = new ArrayList<>();

            Map<String, String> sec1 = new HashMap<>();
            sec1.put("id", Constantes.ORIGEN_MAY);
            sec1.put("nombre", "Inicio");
            seccionesSelector.add(sec1);

            Map<String, String> sec4 = new HashMap<>();
            sec4.put("id", "blog");
            sec4.put("nombre", "Blog");
            seccionesSelector.add(sec4);

            // 🔥 DINÁMICO DESDE BD
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
            model.addAttribute("statsPedidos", new ResumenVisitas());
            model.addAttribute("statsGlobal", new ResumenVisitas());
            model.addAttribute("seccionesStats", new ArrayList<>());
            model.addAttribute("seccionesSelector", new ArrayList<>());
        }
        return "visitas";
    }
}