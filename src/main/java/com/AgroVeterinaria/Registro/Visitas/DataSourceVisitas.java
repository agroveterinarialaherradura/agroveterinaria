package com.AgroVeterinaria.Registro.Visitas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.ResumenVisitasProductos;
import com.AgroVeterinaria.DTO.Visitas;
import com.AgroVeterinaria.Herramientas.Constantes;

@Repository
public class DataSourceVisitas {
	
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private String 	schema		= Constantes.ESQUEMA_REGISTRO;
	Visitas 		visaTotales = new Visitas();
	ResumenVisitas 	resumen 	= new ResumenVisitas();
	
	public int usuarioUpdateVisitas(Visitas visitas) {
	    // Usamos un alias 'h' para la tabla para evitar problemas de esquema en el UPDATE
		String sql = "INSERT INTO " + schema + ".historial_visitas "
		        + " (annio, mes, dia, hora, minuto, segundo, dispositivo, origen, valor, ip) "
		        + " VALUES (:Annio, :Mes, :Dia, :Hora, :Minuto, :Segundo, :Dispositivo, :Origen, :Valor, :Ip) "
		        + " ON CONFLICT (annio, mes, dia, hora, minuto, segundo, dispositivo, origen) "
		        + " DO UPDATE SET "
		        + "    valor = " + schema + ".historial_visitas.valor + 1, "
		        + "               ip = EXCLUDED.ip";  // EXCLUDED toma el valor nuevo que intentabas insertar

	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("Annio",       visitas.getAnnio());
	    params.addValue("Mes",         visitas.getMes());
	    params.addValue("Dia",         visitas.getDia());
	    params.addValue("Hora",        visitas.getHora());
	    params.addValue("Minuto",      visitas.getMinuto());
	    params.addValue("Segundo",     visitas.getSegundo());
	    params.addValue("Dispositivo", visitas.getDispositivo());
	    params.addValue("Origen",      visitas.getOrigen());
	    params.addValue("Valor",       visitas.getValor());
	    params.addValue("Ip",          visitas.getIp());

	    try {
	        return namedParameterJdbcTemplate.update(sql, params);
	    } catch (DataAccessException e) {
	        System.err.println("Error en usuarioUpdateVisitas: " + e.getMessage());
	        return 0;
	    }
	}


    public Visitas findVisitasByOrigen(String Origen) {
    	String sql =""
    			+ " SELECT * FROM "+schema+".historial_visitas WHERE  "
    			+ "        ( origen=:Origen ) "
				+ " ";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
        try {
        	visaTotales =namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Visitas.class));
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return null;
        }
        return visaTotales;
    }

    public int countVisitasByOrigenAndDispositivo(String origen, String dispositivo) {
        String sql = "SELECT COALESCE(SUM(valor), 0) FROM " + schema + ".historial_visitas WHERE origen = :Origen AND dispositivo = :Dispositivo";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("Origen", origen);
        params.addValue("Dispositivo", dispositivo);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        } catch (DataAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
    
    public ResumenVisitas findVisitasResumenByOrigen(String Origen) {
        // Corrección: Se agregó el WHERE y se corrigió el FROM
        String sql = " SELECT "
        		
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Escritorio' THEN valor ELSE 0 END), 0) AS Escritorio, "
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Movil' THEN valor ELSE 0 END), 0) AS Movil, "
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Tablet' THEN valor ELSE 0 END), 0) AS Tablet, "
                + " COALESCE(SUM(CASE WHEN dispositivo NOT IN ('Escritorio', 'Movil', 'Tablet') THEN valor ELSE 0 END), 0) AS Otros, "
                + " COALESCE(SUM(valor), 0) AS Global " // Suma total de todos los dispositivos para ese origen
                + " FROM " + schema + ".historial_visitas "
                + " WHERE origen = :Origen ";
                //+ " AND url_base = '" + Constantes.VALIDABLOG + "' ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("Origen", Origen);
        
        try {
            // Usamos BeanPropertyRowMapper para mapear a tu DTO ResumenVisitas
            return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ResumenVisitas.class));
        } catch (DataAccessException e) {
            System.err.println("Error en findVisitasResumenByOrigen: " + e.getMessage());
            return new ResumenVisitas(); // Retornar objeto vacío en lugar de null para evitar NullPointer en el Front
        }
    }

    public ResumenVisitasProductos findVisitasResumenByOrigenProductos(String Origen) {
        // Corrección: Se agregó el WHERE y se corrigió el FROM
        String sql = " SELECT "
        		
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Escritorio' THEN valor ELSE 0 END), 0) AS Escritorio, "
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Movil' THEN valor ELSE 0 END), 0) AS Movil, "
                + " COALESCE(SUM(CASE WHEN dispositivo = 'Tablet' THEN valor ELSE 0 END), 0) AS Tablet, "
                + " COALESCE(SUM(CASE WHEN dispositivo NOT IN ('Escritorio', 'Movil', 'Tablet') THEN valor ELSE 0 END), 0) AS Otros, "
                + " COALESCE(SUM(valor), 0) AS Global " // Suma total de todos los dispositivos para ese origen
                + " FROM " + schema + ".historial_visitas "
                + " WHERE origen = :Origen ";
                //+ " AND url_base = '" + Constantes.PRODUCTOS + "' "; // Agregado WHERE

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("Origen", Origen);
        
        try {
            // Usamos BeanPropertyRowMapper para mapear a tu DTO ResumenVisitas
            return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ResumenVisitasProductos.class));
        } catch (DataAccessException e) {
            System.err.println("Error en findVisitasResumenByOrigen: " + e.getMessage());
            return new ResumenVisitasProductos(); // Retornar objeto vacío en lugar de null para evitar NullPointer en el Front
        }
    }
    
    
    public List<Map<String, Object>> findVisitsGroupByOrigen() {
        String sql = "SELECT origen AS nombre, COALESCE(SUM(valor), 0) AS visitas " +
                     "FROM " + schema + ".historial_visitas " +
                     "WHERE url_base = '" + Constantes.VALIDABLOG + "' " +  // ← Filtro agregado
                     "GROUP BY origen ORDER BY visitas DESC";
        try {
            return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("nombre", rs.getString("nombre"));
                map.put("visitas", rs.getInt("visitas"));
                return map;
            });
        } catch (DataAccessException e) {
            System.err.println("Error agrupando por origen: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Map<String, Object>> findSeccionesDinamicas() {
        String sql = "" +
                     "SELECT DISTINCT a.url_buscar, a.url_origen "
                   + "    FROM " + Constantes.ESQUEMA_BLOG + ".articulos a "
                   + "    WHERE a.origen = '" + Constantes.ORIGEN_MAY + "' "
                   + "      AND a.estatus = " + Constantes.STR_ESTATUS_TRES
                   + "      AND a.url_buscar IS NOT NULL "
                   + "      AND a.url_origen IS NOT NULL "
                   + "    ORDER BY a.url_buscar ASC" +
                     "";
        try {
            return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getString("url_buscar"));
                map.put("nombre", rs.getString("url_buscar"));
                return map;
            });
        } catch (DataAccessException e) {
            System.err.println("Error agrupando por url_buscar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}