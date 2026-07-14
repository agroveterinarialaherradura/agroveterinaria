package com.AgroVeterinaria.Controllers;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.AgroVeterinaria.DTO.ContactosRequestDTO;
import com.AgroVeterinaria.Registro.Productos.ContactosExternasService;

@RestController
@RequestMapping("/api")
public class ContactosController {

    @Autowired
    private ContactosExternasService contactosExternasService;

    @PostMapping("/newContacto")
    public ResponseEntity<Map<String, Object>> crearContacto(@Valid @RequestBody ContactosRequestDTO dto) {
        Map<String, Object> response = contactosExternasService.enviarContacto(dto);
        if (response.containsKey("exito") && (boolean) response.get("exito")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}