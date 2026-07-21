package com.AgroVeterinaria.Registro.Blogs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.AgroVeterinaria.DTO.Articulo;
import com.AgroVeterinaria.DTO.Blog;

@Service
public class BlogExternasService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.heroku.api.url}")
    private String herokuApiUrl;

    @Value("${blog.origen:AgroVeterinaria}")
    private String blogOrigen;
    
    public Articulo obtenerArticuloPorUrlBuscar(String urlBuscar) {
        try {
            String url = herokuApiUrl + "/articulos/" + urlBuscar;
            System.out.println("🌐 00033-Llamando a: " + ".../articulos/..." + urlBuscar);
            ResponseEntity<Articulo> response = restTemplate.getForEntity(url, Articulo.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener artículo: " + e.getMessage());
            return null;
        }
    }

    public List<Articulo> obtenerArticulosPaginados(int page, int size) {
        try {
            String url = herokuApiUrl + "/articulos/list?page=" + page + "&size=" + size;
            System.out.println("🌐 00045-Llamando a: " + ".../articulos/list..." + size);
            ResponseEntity<List<Articulo>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Articulo>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener artículos paginados: " + e.getMessage());
            return new ArrayList<>();
        }
    }



    public Map<String, String> registrarComentario(Blog blog) {
        try {
            String url = herokuApiUrl + "/blog/comentario";
            System.out.println("🌐 00064-Llamando a: " + ".../blog/...comentario");
            ResponseEntity<Map> response = restTemplate.postForEntity(url, blog, Map.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al registrar comentario: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al registrar el comentario");
            return error;
        }
    }

    public List<Blog> obtenerComentarios(String urlOrigen) {
        try {
            String url = herokuApiUrl + "/blog/comentarios/" + urlOrigen;
            System.out.println("🌐 00078-Llamando a: " + ".../blog/comentarios/..." );  // ← LOG
            ResponseEntity<List<Blog>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Blog>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener comentarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    
    

    public List<String> obtenerTodosLosIds() {
        try {
            String url = herokuApiUrl + "/articulos/ids/all?origen=" + blogOrigen;
            System.out.println("🌐 00098-Llamando a: " + ".../articulos/ids.../all?..." + blogOrigen);
            ResponseEntity<List<String>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener IDs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Articulo> obtenerArticulosPorIds(List<String> ids) {
        try {
            String url = herokuApiUrl + "/articulos/by-ids?origen=" + blogOrigen;
            System.out.println("🌐 00113-Llamando a: " + ".../articulos/by...?..." + blogOrigen);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<List<String>> requestEntity = new HttpEntity<>(ids, headers);
            ResponseEntity<List<Articulo>> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<Articulo>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error al obtener artículos por IDs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Articulo> buscarArticulos(String q, int page, int size) {
        try {
            String url = herokuApiUrl + "/articulos/search?q=" + q + "&page=" + page + "&size=" + size + "&origen=" + blogOrigen;
            System.out.println("🌐 00131-Llamando a: " + ".../articulos/search...?..." + blogOrigen);
            ResponseEntity<List<Articulo>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Articulo>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error en búsqueda: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}