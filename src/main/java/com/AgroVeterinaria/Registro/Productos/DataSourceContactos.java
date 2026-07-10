package com.AgroVeterinaria.Registro.Productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataSourceContactos {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String schema = "registro";
    private int num = 0;

    @Autowired
    public DataSourceContactos(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Inserta un nuevo contacto en la tabla registro.contactos.
     *
     * @param origen   Origen del registro (ej. "web", "app")
     * @param annio    Año (ej. 2026)
     * @param mes      Mes (1-12)
     * @param dia      Día (1-31)
     * @param telefono Número de teléfono (con código de país)
     * @param email    Correo electrónico (puede ser null)
     * @param estatus  Estado del contacto (puede ser null)
     * @return Número de filas insertadas (1 si éxito, 0 si error)
     */
    public int insertData(String origen, int annio, int mes, int dia, 
            String telefono, String email, String estatus,
            Integer hora, Integer minuto, Integer segundos, String nombre, String mensaje,
            String fechaCita, String horaCita) {
		
			String sql = ""
			  + " INSERT INTO " + schema + ".contactos ( "
			  + "        origen,     "
			  + "        annio,      "
			  + "        mes,        "
			  + "        dia,        "
			  + "        telefono,   "
			  + "        email,      "
			  + "        estatus,    "
			  + "        hora,       "
			  + "        minuto,     "
			  + "        segundos,     "
			  + "        nombre,     "
			  + "        mensaje,    "
			  + "        fecha_cita, "
			  + "        hora_cita   "
			  + " ) VALUES ( "
			  + "        :origen,    "
			  + "        :annio,     "
			  + "        :mes,       "
			  + "        :dia,       "
			  + "        :telefono,  "
			  + "        :email,     "
			  + "        :estatus,   "
			  + "        :hora,      "
			  + "        :minuto,    "
			  + "        :segundos,     "
			  + "        :nombre,    "
			  + "        :mensaje,   "
			  + "        :fechaCita, "
			  + "        :horaCita   "
			  + " )";
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("origen", origen);
			params.addValue("annio", annio);
			params.addValue("mes", mes);
			params.addValue("dia", dia);
			params.addValue("telefono", telefono);
			params.addValue("email", email);
			params.addValue("estatus", estatus);
			params.addValue("hora", hora);
			params.addValue("minuto", minuto);
			params.addValue("segundos", segundos);
			params.addValue("nombre", nombre);
			params.addValue("mensaje", mensaje);
			
		// ===== CONVERSIÓN SEGURA DE FECHA Y HORA =====
		try {
			if (fechaCita != null && !fechaCita.trim().isEmpty()) {
			  params.addValue("fechaCita", java.sql.Date.valueOf(fechaCita)); // "yyyy-MM-dd"
			} else {
			  params.addValue("fechaCita", null);
			}
		} catch (IllegalArgumentException e) {
				System.out.println("Error en formato de fechaCita: " + fechaCita);
				params.addValue("fechaCita", null);
		}
			
		try {
			if (horaCita != null && !horaCita.trim().isEmpty()) {
			  // Si la hora viene sin segundos (ej. "10:30"), le agregamos ":00"
			  String horaConSegundos = horaCita;
			  if (horaCita.split(":").length == 2) {
			      horaConSegundos = horaCita + ":00";
			  }
			  params.addValue("horaCita", java.sql.Time.valueOf(horaConSegundos));
			} else {
			  params.addValue("horaCita", null);
			}
		} catch (IllegalArgumentException e) {
				System.out.println("Error en formato de horaCita: " + horaCita);
				params.addValue("horaCita", null);
		}
		
		try {
			num = namedParameterJdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
				System.out.println("Error al insertar en registro.contactos: " + e.getMessage());
			return 0;
		}
		return num;
    }
}