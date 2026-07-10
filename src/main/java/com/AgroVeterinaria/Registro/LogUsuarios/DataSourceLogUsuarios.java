package com.AgroVeterinaria.Registro.LogUsuarios;
import java.sql.Time;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.AgroVeterinaria.DTO.Usuarios;

@Repository
public class DataSourceLogUsuarios {
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
    @Autowired
    public DataSourceLogUsuarios(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }
    
	private String schema="registro";
	int num = 0;
	LogUsuarios usua = new LogUsuarios();
	
    public int insertData(String Origen, String Email, Date Fecha, Time Hora, String Descripcion) {
    	String sql =""
    			+ " INSERT INTO "+schema+".logusuarios ( "
    			+ "        origen,     "
    			+ "		   email,      "
    			+ "        fecha,      "
    			+ "        hora,       "
    			+ "        descripcion "
				+ " ) VALUES ( "
				+ "	       :Origen,    "
				+ "        :Email,     "
				+ "        :Fecha,     "
				+ "        :Hora,      "
				+ "        :Descripcion"
				+ " )";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
    	params.addValue("Email", 		Email);
    	params.addValue("Fecha", 		Fecha);
    	params.addValue("Hora", 		Hora);
    	params.addValue("Descripcion", 	Descripcion);
        try {
        	num =namedParameterJdbcTemplate.update(sql, params);
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return num;
        }
        return num;
    }
}