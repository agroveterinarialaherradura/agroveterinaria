package com.AgroVeterinaria.Registro.Blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.AgroVeterinaria.DTO.Articulo;
import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Herramientas.EmailService;
import com.AgroVeterinaria.Registro.LogUsuarios.DataSourceLogUsuarios;

import java.util.Date;
import java.util.Locale;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Base64;

@Service
public class BlogAsyncService {

    @Autowired
    private DataSourceBlog crudBlog;
    
	@Autowired
	private DataSourceLogUsuarios logUsuariosRepository;
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private DataSourceArticulos dataSourceArticulos;
    
    Articulo articulo = new Articulo();
    
    
    @Async
    public void procesarRegistroCompleto(Blog blog) throws ParseException {
        System.out.println("prcesarRegistroCompleto blog:"+ blog.toString());
        // Fecha y hora actuales
        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(System.currentTimeMillis());

        // Preparar códigos encode (aunque ya se hicieron en el controlador, 
        // es mejor generarlos aquí para evitar dependencias)
        String encode = Base64.getEncoder().encodeToString(blog.getEmail().getBytes());
        String encodeFecha = Base64.getEncoder().encodeToString(currentDate.toString().getBytes());
        String encodeHora = Base64.getEncoder().encodeToString(currentTime.toString().getBytes());
        String getOrigen = blog.getOrigen();
        
        SimpleDateFormat sdfEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date fecha = sdfEntrada.parse(currentDate.toString());
        SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdfSalida.format(fecha);
        
        blog.setFecha(fechaFormateada);
        blog.setHora(currentTime.toString());
        
        System.out.println("Blog: " + blog.toString());
        System.out.println("getOrigen: " + blog.getOrigen());
        try {
        	articulo = dataSourceArticulos.obtenerArticuloPorUrlOrigen(getOrigen);
        	System.out.println("articulo: " + articulo.toString());
        	if (articulo != null) {
        		
            	try {
                    // 1. Guardar el blog en BD
                    Blog savedBlog = crudBlog.save(blog);
                    logUsuariosRepository.insertData(
                    		blog.getOrigen(), 
                    		blog.getEmail(),
                            new Date(System.currentTimeMillis()), 
                            new Time(System.currentTimeMillis()),
                            "Se Guardo Comentario en la BD");

                    // 2. Enviar correo de validación
                    try {
                    	System.out.println("*****************  articulo.getUrlOrigen(): " + articulo.getUrlOrigen() + ", Constantes.CORREO_BASE: " + Constantes.CORREO_BASE + ", blog.getEmail():" + blog.getEmail() + ", Encode: " + encode + ", encodeFecha: " + encodeFecha +  ", encodeHora: " + encodeHora );
                    	
                        emailService.sendMailGenerico(
                        		articulo,
                                Constantes.CORREO_BASE,
                                blog.getEmail(),
                                "Active su Comentario de " + blog.getOrigen(),
                                encode,
                                encodeFecha,
                                encodeHora);

                        logUsuariosRepository.insertData(
                        		blog.getOrigen(), 
                        		blog.getEmail(),
                                new Date(System.currentTimeMillis()), 
                                new Time(System.currentTimeMillis()),
                                "Se envia Mensaje para validar Comentario Registrado en el Blog");

                        // 3. Actualizar estatus a 2 (validado)
                        savedBlog.setEstatus(Constantes.INT_ESTATUS_DOS);
                        crudBlog.save(savedBlog);
                        System.out.println("Blog Final: " + blog.toString());
                    } catch (Exception e) {
                        System.out.println("Blog: Envio de Correo Error: " + e);
                        logUsuariosRepository.insertData(blog.getOrigen(), blog.getEmail(),
                                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
                                "Error al enviar Mensaje para validar Comentario Registrado en el Blog");
                    }
                } catch (Exception e) {
                    System.out.println("Registro de Blog: Error " + e);
                    logUsuariosRepository.insertData(blog.getOrigen(), blog.getEmail(),
                            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
                            "Error al guardar comentario en BD");
                }
        	}
        }catch (Exception e) {
        	System.out.println("No Existe: " + blog.getOrigen());
        }
        
        // El blog ya tiene origen y estatus inicial (0) desde el controlador
        
    }
    

    @Async
    public void reProcesarMail(String mail) throws ParseException {
        System.out.println("reProcesarMail Mail:"+ mail);
        // Fecha y hora actuales
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
        //Date currentDate = null;//new Date(System.currentTimeMillis());
        //Time currentTime = null;//new Time(System.currentTimeMillis());
        
        Blog unBlog = new Blog();
        
        unBlog = crudBlog.findOneByEmail(mail).orElse(null);
        String fechaString = unBlog.getFecha();
        Date currentDate = formato.parse(fechaString);
        Time currentTime = Time.valueOf(LocalTime.parse(unBlog.getHora()));

        System.out.println("Blog: 		" + unBlog.toString());
        System.out.println("getOrigen:  " + unBlog.getOrigen());
        try {
        	articulo = dataSourceArticulos.obtenerArticuloPorUrlOrigen(unBlog.getOrigen());
        	if (articulo != null) {
        		System.out.println("articulo: " + articulo.toString());
            	try {
                    // 2. Enviar correo de validación
                    String encode = Base64.getEncoder().encodeToString(unBlog.getEmail().getBytes());
                    String encodeFecha = Base64.getEncoder().encodeToString(currentDate.toString().getBytes());
                    String encodeHora = Base64.getEncoder().encodeToString(currentTime.toString().getBytes());
                    String getOrigen = unBlog.getOrigen();
                    
                    try {
                    	System.out.println("*****************  articulo.getUrlOrigen(): " + articulo.getUrlOrigen() + ", Constantes.CORREO_BASE: " + Constantes.CORREO_BASE + ", blog.getEmail():" + unBlog.getEmail() + ", Encode: " + unBlog.getEmail() + ", encodeFecha: " + unBlog.getFecha() +  ", encodeHora: " + unBlog.getHora());
                    	
                        emailService.sendMailGenerico(
                        		articulo,
                                Constantes.CORREO_BASE,
                                unBlog.getEmail(),
                                "Active su Comentario de " + getOrigen,
                                encode,
                                encodeFecha,
                                encodeHora);
                        
                        logUsuariosRepository.insertData(
                        		unBlog.getOrigen(), 
                        		unBlog.getEmail(),
                                new Date(System.currentTimeMillis()), 
                                new Time(System.currentTimeMillis()),
                                "Se re Envia Mensaje para validar Comentario Registrado en el Blog");

                        // 3. Actualizar estatus a 2 (validado)
                        unBlog.setEstatus(Constantes.INT_ESTATUS_DOS);
                        crudBlog.save(unBlog);
                        System.out.println("Blog Final: " + unBlog.toString());
                    } catch (Exception e) {
                        System.out.println("Blog: Envio de Correo Error: " + e);
                        logUsuariosRepository.insertData(unBlog.getOrigen(), unBlog.getEmail(),
                                new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
                                "Error al enviar Mensaje para validar Comentario Registrado en el Blog");
                    }
                } catch (Exception e) {
                    System.out.println("Registro de Blog: Error " + e);
                    logUsuariosRepository.insertData(unBlog.getOrigen(), unBlog.getEmail(),
                            new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
                            "Error al guardar comentario en BD");
                }
        	}
        }catch (Exception e) {
        	System.out.println("No Existe: " + unBlog.getOrigen());
        }
        
        // El blog ya tiene origen y estatus inicial (0) desde el controlador
        
    }
}