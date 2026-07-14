package com.AgroVeterinaria.Registro.Usuarios;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.AgroVeterinaria.DTO.Usuarios;

@Service
public class UsuarioExternasService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.heroku.api.url}")
    private String herokuApiUrl;

    // Registrar usuario
    public Map<String, String> registrarUsuario(Usuarios usuario) {
        try {
            String url = herokuApiUrl + "/usuarios/registro";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, usuario, Map.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar usuario: " + e.getMessage());
            return error;
        }
    }

    // Validar usuario por clave (token)
    public Map<String, Object> validarUsuario(String clave) {
        try {
            String url = herokuApiUrl + "/usuarios/validar/" + clave;
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("exito", false);
            error.put("error", "Error al validar: " + e.getMessage());
            return error;
        }
    }

    // Reenviar correo de activación
    public Map<String, String> reintentarCorreo(String email) {
        try {
            String url = herokuApiUrl + "/usuarios/reintento?email=" + email;
            ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al reenviar correo: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al reenviar correo: " + e.getMessage());
            return error;
        }
    }

    // Verificar si el email ya existe
    public boolean existeUsuario(String email) {
        try {
            String url = herokuApiUrl + "/usuarios/existe?email=" + email;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map body = response.getBody();
            return body != null && (boolean) body.get("existe");
        } catch (Exception e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }
}