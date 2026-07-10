package com.AgroVeterinaria.Registro.Visitas;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;

public class VisitasController {

    @Autowired
    private VisitasService service;

    // ===== 1. Endpoint para visitas generales (validaBlog) =====
    @GetMapping("/dashboard-stats")
    public Map<String, Object> getDashboardData() throws ParseException {
        Map<String, Object> response = new HashMap<>();
        CompletableFuture<ResumenVisitas> resumen = service.procesarVisitas("TuPaginaWebHoy");
        response.put("Resumen", resumen);
        return response;
    }

    // ===== 2. Endpoint para visitas de productos =====
    @GetMapping("/dashboard-stats-productos")
    public Map<String, Object> getDashboardDataProductos() throws ParseException {
        Map<String, Object> response = new HashMap<>();
        CompletableFuture<ResumenVisitasProductos> resumen = service.procesarVisitasProductos("TuPaginaWebHoy");
        response.put("Resumen", resumen);
        return response;
    }

    // ===== 3. (Opcional) Endpoint unificado con parámetro =====
    @GetMapping("/dashboard-stats-all")
    public Map<String, Object> getDashboardDataWithType(@RequestParam(defaultValue = "blog") String tipo) throws ParseException {
        Map<String, Object> response = new HashMap<>();
        if ("productos".equalsIgnoreCase(tipo)) {
            CompletableFuture<ResumenVisitasProductos> resumen = service.procesarVisitasProductos("TuPaginaWebHoy");
            response.put("Resumen", resumen);
        } else {
            CompletableFuture<ResumenVisitas> resumen = service.procesarVisitas("TuPaginaWebHoy");
            response.put("Resumen", resumen);
        }
        return response;
    }
}