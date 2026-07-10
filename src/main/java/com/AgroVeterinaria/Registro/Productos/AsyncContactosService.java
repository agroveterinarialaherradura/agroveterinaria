package com.AgroVeterinaria.Registro.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.AgroVeterinaria.DTO.ContactosRequestDTO;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncContactosService {

    private final DataSourceContactos dataSourceContactos;

    @Autowired
    public AsyncContactosService(DataSourceContactos dataSourceContactos) {
        this.dataSourceContactos = dataSourceContactos;
    }

    @Async
    public CompletableFuture<Boolean> guardarContactoAsync(ContactosRequestDTO dto) {
        try {
            int filas = dataSourceContactos.insertData(
                    dto.getOrigen(),
                    dto.getAnnio(),
                    dto.getMes(),
                    dto.getDia(),
                    dto.getTelefono(),      // ← orden corregido
                    dto.getEmail(),
                    dto.getEstatus(),
                    dto.getHora(),
                    dto.getMinuto(),
                    dto.getSegundos(),
                    dto.getNombre(),
                    dto.getMensaje(),
                    dto.getFechaCita(),
                    dto.getHoraCita()
            );
            return CompletableFuture.completedFuture(filas > 0);
        } catch (Exception e) {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }
}