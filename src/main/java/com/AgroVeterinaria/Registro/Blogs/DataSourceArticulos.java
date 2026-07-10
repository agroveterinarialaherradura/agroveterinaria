package com.AgroVeterinaria.Registro.Blogs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.AgroVeterinaria.DTO.Articulo;
import com.AgroVeterinaria.Herramientas.Constantes;

import org.springframework.jdbc.core.RowMapper;

@Repository
public class DataSourceArticulos {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. CREAS EL MAPPER UNA SOLA VEZ
    private final RowMapper<Articulo> articuloRowMapper = (rs, rowNum) -> {
        Articulo art = new Articulo();
        art.setTitulo(rs.getString("titulo"));
        art.setPregunta(rs.getString("pregunta"));
        art.setComentario(rs.getString("comentario"));
        art.setRespuesta(rs.getString("respuesta"));
        art.setOrigen(rs.getString("origen"));
        art.setUrlBase(rs.getString("url_base"));
        art.setUrlBuscar(rs.getString("url_buscar"));
        art.setUrlOrigen(rs.getString("url_origen"));
        art.setFecha(rs.getDate("fecha"));
        art.setEstatus(rs.getInt("estatus"));
        art.setAutor(rs.getString("autor"));
        art.setUrlImagen(rs.getString("url_imagen"));
        art.setUrlCard(rs.getString("url_card"));
        return art;
    };

    
 // Obtiene todos los IDs (o títulos) de artículos activos
    public List<String> obtenerTodosLosIds() {
        String sql = "SELECT titulo "
        		+ "   FROM blog.articulos "
        		+ "   WHERE estatus = 3"
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ "";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // Obtiene artículos a partir de una lista de IDs (manteniendo el orden de la lista)
    public List<Articulo> obtenerArticulosPorIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        String sql = "SELECT * "
        		+ "   FROM blog.articulos "
        		+ "   WHERE titulo IN (" +
                     String.join(",", Collections.nCopies(ids.size(), "?")) +
                     ") "
                + "		AND url_base = '" + Constantes.VALIDABLOG + "' "
                + "   ORDER BY array_position(ARRAY[" + 
                     String.join(",", Collections.nCopies(ids.size(), "?")) + "], titulo)"
                
                + "";
        // Esta ordenación por array_position funciona en PostgreSQL. 
        // Si usas otro motor, ordena en memoria.
        List<Object> params = new ArrayList<>();
        params.addAll(ids);
        params.addAll(ids);
        return jdbcTemplate.query(sql, params.toArray(), articuloRowMapper);
    }
    
    // 2. LO USAS EN LA CARGA GENERAL
    public List<Articulo> obtenerArticulosPaginados(int pagina, int tamano) {
        int offset = pagina * tamano;
        String sql = "	SELECT * "
        		+ "    	FROM blog.articulos "
        		+ "		WHERE estatus = 3 "
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ "		ORDER BY fecha "
        		+ "		DESC LIMIT ? OFFSET ? ";
        return jdbcTemplate.query(sql, new Object[]{tamano, offset}, articuloRowMapper);
    }

    // 3. LO USAS EN LA BÚSQUEDA (Sin repetir código)
    public List<Articulo> buscarArticulosPaginados(String q, int pagina, int tamano) {
        int offset = pagina * tamano;
        String sql = "SELECT * FROM blog.articulos "
        		+ "   WHERE estatus = 3 "
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ "     AND (titulo ILIKE ? OR comentario ILIKE ?) "
        		+ "   ORDER BY fecha DESC LIMIT ? OFFSET ?";
        String p = "%" + q + "%";
        return jdbcTemplate.query(sql, new Object[]{p, p, tamano, offset}, articuloRowMapper);
    }

    
    
    // En DataSourceArticulos.java
    public Articulo obtenerArticuloPorUrlBuscar(String urlBuscar) {
        String sql = "SELECT * "
        		+ "   FROM blog.articulos "
        		+ "   WHERE url_buscar = ? "
        		+ "     AND estatus = 3 "
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ "   LIMIT 1";
        List<Articulo> resultados = jdbcTemplate.query(sql, articuloRowMapper, urlBuscar);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public Articulo obtenerArticuloPorUrlOrigen(String urlBuscar) {
        String sql = "SELECT * "
        		+ "   FROM blog.articulos "
        		+ "   WHERE url_origen = ? "
        		+ "     AND estatus = 3 "
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ "   LIMIT 1";
        List<Articulo> resultados = jdbcTemplate.query(sql, articuloRowMapper, urlBuscar);
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    /**
     * Obtiene todos los url_buscar de los artículos activos (estatus = 3)
     * para generar el sitemap dinámico.
     */
    public List<Map<String, Object>> obtenerTodosLosUrlBuscarConFecha() {
        String sql = "SELECT url_buscar, fecha "
        		+ "   FROM blog.articulos "
        		+ "   WHERE estatus = 3"
        		+ "		AND url_base = '" + Constantes.VALIDABLOG + "' "
        		+ " ";
        return jdbcTemplate.queryForList(sql);
    }
}