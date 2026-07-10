package com.AgroVeterinaria.Registro.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;

@Repository
public class DataSourceUsuarios {
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
    @Autowired
    public DataSourceUsuarios(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }
	private String schema=Constantes.ESQUEMA_REGISTRO;
	int num = 0;
	Usuarios usua = new Usuarios();
	
    public int insertData(String Origen, String Email, String Contrasena, String Username, Integer Estatus, Integer Perfil) {
    	String sql =""
    			+ " INSERT INTO "+schema+".usuarios ( "
    			+ "        origen,"
    			+ "		   email, "
    			+ "        contrasena, "
    			+ "        username,   "
    			+ "        estatus,    "
    			+ "        perfil      "
				+ " ) VALUES ( "
				+ "	       :Origen,    "
				+ "        :Email,     "
				+ "        :Contrasena,"
				+ "        :Username,  "
				+ "        :Estatus,   "
				+ "        :Perfil     "
				+ ")";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
    	params.addValue("Email", 		Email);
    	params.addValue("Contrasena", 	Contrasena);
    	params.addValue("Username", 	Username);
    	params.addValue("Estatus", 		Estatus);
    	params.addValue("Perfil", 		Perfil);

        try {
        	num =namedParameterJdbcTemplate.update(sql, params);
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return num;
        }
        return num;
    }
    
    public int usuarioUpdateEstatus(Usuarios user) {
    	String sql =""
    			+ " UPDATE "+schema+".usuarios "
    			//+ "       SET contrasena= :Contrasena, "
    			//+ "       SET username  = :Username,   "
    			+ "       SET estatus   = :Estatus    "
    			//+ "       SET perfil    = :Perfil      "
				+ " WHERE  "
				+ "	 ( origen= :Origen  ) AND ( email = :Email )   "
				+ " ";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		user.getOrigen());
    	params.addValue("Email", 		user.getEmail());
    	params.addValue("Contrasena", 	user.getContrasena());
    	params.addValue("Username", 	user.getUsername());
    	params.addValue("Estatus", 		user.getEstatus());
    	params.addValue("Perfil", 		user.getPerfil());

        try {
        	num =namedParameterJdbcTemplate.update(sql, params);
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return num;
        }
        return num;
    }
    
    public int updateData(String Origen, String Email, String Contrasena, String Username, Integer Estatus, Integer Perfil) {
    	String sql =" "
    			+ " UPDATE "+schema+".usuarios SET  "
    			+ "        origen=:Origen, "
    			+ "		   email=:Email, "
    			+ "        contrasena=:Contrasena, "
    			+ "        username=:Username, "
    			+ "        estatus=:Estatus, "
    			+ "        perfil=:Perfil "
				+ " WHERE ( origen=:Origen ) and "
				+ "       ( email=:Email   ) ;";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
    	params.addValue("Email", 		Email);
    	params.addValue("Contrasena", 	Contrasena);
    	params.addValue("Username", 	Username);
    	params.addValue("Estatus", 		Estatus);
    	params.addValue("Perfil", 		Perfil);

        try {
        	num =namedParameterJdbcTemplate.update(sql, params);
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return num;
        }
        return num;
    }
    
    public Usuarios findByOrigenEMailEstatus(String Origen, String Email, Integer Estatus) {
    	String sql =""
    			+ " SELECT * FROM "+schema+".usuarios WHERE  "
    			+ "        ( origen=:Origen ) and "
    			+ "		   ( email=:Email )   and "
    			+ "		   ( estatus<>:Estatus ) "
				+ " ";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
    	params.addValue("Email", 		Email);
    	params.addValue("Estatus", 		Estatus);
        try {
        	usua =namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Usuarios.class));
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return null;
        }
        return usua;
    }

    public Usuarios findByOrigenEMail(String Origen, String Email) {//, String Estatus) {
    	String sql =""
    			+ " SELECT * FROM "+schema+".usuarios WHERE  "
    			+ "        ( origen=:Origen ) and "
    			+ "		   ( email=:Email ) "
    			//+ "		   ( estatus=:Estatus ) "
				+ " ";
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("Origen", 		Origen);
    	params.addValue("Email", 		Email);
    	//params.addValue("Estatus", 		Estatus);
        try {
        	 return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Usuarios.class));
        }catch (DataAccessException e) {
        	System.out.println("Error: " + e);
        	return null;
        }
    }

}