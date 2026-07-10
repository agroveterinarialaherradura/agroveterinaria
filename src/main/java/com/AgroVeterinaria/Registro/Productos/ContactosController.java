package com.AgroVeterinaria.Registro.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AgroVeterinaria.DTO.ContactosRequestDTO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/newContacto")
public class ContactosController {

    //private final AsyncContactosService asyncContactosService;

    @Autowired
    AsyncContactosService asyncContactosService;
    //public ContactosController(AsyncContactosService asyncContactosService) {
    //    this.asyncContactosService = asyncContactosService;
    //}

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, Object>>> crearContacto(@Valid @RequestBody ContactosRequestDTO dto) {
        return asyncContactosService.guardarContactoAsync(dto)
            .thenApply(guardado -> {
                Map<String, Object> response = new HashMap<>();
                if (guardado) {
                    response.put("mensaje", "Contacto registrado correctamente");
                    response.put("exito", true);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                } else {
                    response.put("mensaje", "Error al registrar el contacto");
                    response.put("exito", false);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            })
            .exceptionally(ex -> {
                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Error interno: " + ex.getCause().getMessage());
                response.put("exito", false);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            });
    }
}