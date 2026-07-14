package com.AgroVeterinaria.Registro.Visitas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;

@Service
public class VisitasExternasService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.visitas.api.url}")
    private String externalApiUrl; // "https://tupaginawebhoy-fe12dd345e09.herokuapp.com/api/visitas"

    public List<Map<String, Object>> findVisitsGroupByOrigen() {
        try {
            String url = externalApiUrl + "/stats/group-by-origen";
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener group-by-origen: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> findSeccionesDinamicas() {
        try {
            String url = externalApiUrl + "/stats/secciones-dinamicas";
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener secciones dinámicas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ResumenVisitas procesarVisitas(String origen) {
        try {
            String url = externalApiUrl + "/stats/resumen?origen=" + origen;
            System.out.println(" procesarVisitas: " + origen);
            ResponseEntity<ResumenVisitas> response = restTemplate.getForEntity(url, ResumenVisitas.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener resumen de visitas: " + e.getMessage());
            return new ResumenVisitas();
        }
    }

    public ResumenVisitasProductos procesarVisitasProductos(String origen) {
        try {
            String url = externalApiUrl + "/stats/resumen-productos?origen=" + origen;
            ResponseEntity<ResumenVisitasProductos> response = restTemplate.getForEntity(url, ResumenVisitasProductos.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener resumen de productos: " + e.getMessage());
            return new ResumenVisitasProductos();
        }
    }
}