package com.AgroVeterinaria.Registro.Usuarios;
import java.sql.Date;
import java.sql.Time;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Herramientas.EmailService;
import com.AgroVeterinaria.Registro.LogUsuarios.DataSourceLogUsuarios;

@Service
public class UsuarioService {
	
    @Autowired
    private DataSourceUsuarios dataSourceUsuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    
	@Autowired
	private DataSourceLogUsuarios LogUsuariosRepository;
	
	@Async
    @Transactional
    public void registrarUsuario(Usuarios dto) throws Exception {
        // Verificar si ya existe
    	Usuarios usuario = new Usuarios();
    	Usuarios existente = new Usuarios();
    	try {
    	    existente = dataSourceUsuarios.findByOrigenEMail(dto.getOrigen(), dto.getEmail());
    	    System.out.println(dto.getOrigen());
            System.out.println("039-registrarUsr Existente: eMail:" + dto.getEmail() + " existente:" + existente);
            if (existente != null) {
                throw new RuntimeException("El correo ya está registrado");
            }
    	} catch (EmptyResultDataAccessException e) {
    		//throw new RuntimeException("El correo No Existe");
    		System.out.println("No Existente");
    	}

        // Enviar correo de activación (token, etc.)
        String encode = Base64.getEncoder().encodeToString(dto.getEmail().getBytes());
        //String token = generarToken(dto.getEmail());
        if (existente == null) {
            // Crear nuevo usuario
            
            usuario.setEmail(dto.getEmail());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));//usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            usuario.setOrigen(Constantes.ORIGEN);
            usuario.setEstatus(Constantes.INT_ESTATUS_CRO); // por ejemplo: 0 = pendiente
            usuario.setPerfil(Constantes.INT_ESTATUS_DOS); // 2 = usuario normal

            //** Primer Registro del Usuario en la BD **/
            try {
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                
                // Guardar en BD: String Origen, String Email, String Contrasena, String Username, Integer Estatus, Integer Perfil
                dataSourceUsuarios.insertData(usuario.getOrigen(), usuario.getEmail(), usuario.getContrasena(), usuario.getUsername(), usuario.getEstatus(), usuario.getPerfil());
                //num = dataSourceUsuarios.updateData(usuario.getOrigen(), usuario.getEmail(), usuario.getContrasena(), usuario.getUsername(), usuario.getEstatus(), usuario.getPerfil() );
                int num = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Se Guardo Correo en la BD");             

            } catch(Exception e) {
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                int num = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Registro de Correo: Error " + e);                
                System.out.println("Registro de Correo: Error " + e);
            }
            
            try {//Envío de Correo a la cuenta Registrada si Existe!!
                emailService.sendMail(
                		"ferbethasistemas@gmail.com", 
                		dto.getEmail(), 
                		"Active su Registro en Sistemas Betha S.A. de C.V. ",
                		//" accediendo al siguiente Link: " + Constantes.URL_BASE + "/valida/"+
                		encode
                );

                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                usuario.setEstatus(Constantes.INT_ESTATUS_DOS);
                System.out.println("Usuario: " + usuario.toString() );
                //U= dataSourceUsuarios.save(usuario);
                int num1 = dataSourceUsuarios.usuarioUpdateEstatus(usuario);
                int num2 = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Se envia Mensaje para validar Correo");
            } catch(Exception e) {
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                String errorMsg = "Error en Envío de Correo: " + e.getMessage();
                if (errorMsg.length() > 500) {
                    errorMsg = errorMsg.substring(0, 497) + "...";
                }
                int num2 = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, errorMsg);
                System.out.println("101-regUsr Envio de Correo: Error " + e);
            }
        }else {
            System.out.println("Post: username" + usuario.getUsername() + " Correo ya Registrado!");
            Usuarios uTemp = new Usuarios();
        }
    }

    @Async
    @Transactional
    public void reintentarCorreo(Usuarios dto) throws Exception {
        // Verificar si ya existe
    	Usuarios usuario = new Usuarios();
        Usuarios existente = dataSourceUsuarios.findByOrigenEMail(Constantes.ORIGEN, dto.getEmail());
        System.out.println(Constantes.ORIGEN);
        System.out.println("115-reintentoMail - Existente Email:" + dto.getEmail()+ " existente:" + existente);
        
        // Enviar correo de activación (token, etc.)
        String encode = Base64.getEncoder().encodeToString(dto.getEmail().getBytes());
        //String token = generarToken(dto.getEmail());
        if (existente != null) {
        	//Se llena el Objeto
            usuario.setEmail(dto.getEmail());
            //usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));//usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            usuario.setOrigen(Constantes.ORIGEN);
            usuario.setEstatus(Constantes.INT_ESTATUS_CRO); // por ejemplo: 0 = pendiente
            usuario.setPerfil(Constantes.INT_ESTATUS_DOS); // 2 = usuario normal
            
            try {//Envío de Correo a la cuenta Registrada si Existe!!
                emailService.sendMail(
                		"ferbethasistemas@gmail.com", 
                		dto.getEmail(), 
                		"Active su Registro en Sistemas Betha S.A. de C.V. ",
                		//" accediendo al siguiente Link: " + Constantes.URL_BASE + "/valida/"+
                		encode
                );

                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                usuario.setEstatus(Constantes.INT_ESTATUS_DOS);
                System.out.println("Usuario: " + usuario.toString() );
                //U= dataSourceUsuarios.save(usuario);
                int num1 = dataSourceUsuarios.usuarioUpdateEstatus(usuario);
                int num2 = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Se envia Mensaje para validar Correo");
            } catch(Exception e) {
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                String errorMsg = "Error en Envío de Correo: " + e.getMessage();
                if (errorMsg.length() > 500) {
                    errorMsg = errorMsg.substring(0, 497) + "...";
                }
                int num2 = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, errorMsg);
                System.out.println("152-Reintento Envio de Correo: Error " + e);
            }
        }else {
            System.out.println("Post: username" + usuario.getUsername() + " Correo ya Registrado!");
            Usuarios uTemp = new Usuarios();
        }
    }
    
    private String generarToken(String email) {
        return java.util.Base64.getEncoder().encodeToString(email.getBytes());
    }
}