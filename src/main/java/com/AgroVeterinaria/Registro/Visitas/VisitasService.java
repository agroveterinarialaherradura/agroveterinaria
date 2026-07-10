package com.AgroVeterinaria.Registro.Visitas;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;

@Service
public class VisitasService {

    @Autowired
    private DataSourceVisitas repository; // Tu fuente de datos

    @Async
    public CompletableFuture<ResumenVisitas> procesarVisitas(String origen) {
        try {
            ResumenVisitas resumen = repository.findVisitasResumenByOrigen(origen);
            return CompletableFuture.completedFuture(resumen != null ? resumen : new ResumenVisitas());
        } catch (Exception e) {
            System.err.println("Error en VisitasService: " + e.getMessage());
            return CompletableFuture.completedFuture(new ResumenVisitas());
        }
    }
    
    @Async
    public CompletableFuture<ResumenVisitasProductos> procesarVisitasProductos(String origen) {
        try {
            ResumenVisitasProductos resumen = repository.findVisitasResumenByOrigenProductos(origen);
            return CompletableFuture.completedFuture(resumen != null ? resumen : new ResumenVisitasProductos());
        } catch (Exception e) {
            System.err.println("Error en VisitasService: " + e.getMessage());
            return CompletableFuture.completedFuture(new ResumenVisitasProductos());
        }
    }
}