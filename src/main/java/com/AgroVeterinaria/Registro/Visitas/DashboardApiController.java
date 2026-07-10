package com.AgroVeterinaria.Registro.Visitas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;

@RestController
@RequestMapping("/admin")
public class DashboardApiController {

    @Autowired
    private DataSourceVisitas dataSourceVisitas;

    @GetMapping("/seccion/{dispositivo}/{origen}")
    public int getVisitasCount(@PathVariable String dispositivo, @PathVariable String origen) {
        return dataSourceVisitas.countVisitasByOrigenAndDispositivo(origen, dispositivo);
    }

    // Obtener estadísticas globales (si decides crear el método en tu DataSource)
    @GetMapping("/origen/{origen}")
    public ResumenVisitas getGlobalStats(@PathVariable String origen) {
        // Aquí podrías llamar a un método que no filtre por origen
        return dataSourceVisitas.findVisitasResumenByOrigen(origen); 
    }
    
    @GetMapping("/productos/{origen}")
    public ResumenVisitasProductos getProductosStats(@PathVariable String origen) {
        return dataSourceVisitas.findVisitasResumenByOrigenProductos(origen);
    }
}