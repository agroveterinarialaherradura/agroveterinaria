package com.AgroVeterinaria.Registro.Blogs;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.LogUsuarios.DataSourceLogUsuarios;
import com.AgroVeterinaria.Registro.Usuarios.DataSourceUsuarios;

@Controller
public class ValidateController {
String global ="";

	@Autowired
	private DataSourceUsuarios UsuariosRepository;

	@Autowired
	private DataSourceLogUsuarios LogUsuariosRepository;
	
    @Autowired
    private DataSourceBlog crudBlog;
    
	@GetMapping("/valida/{clave}")
	public String valida(@PathVariable("clave") String Clave, Model model) {
		System.out.println("Validando Clave de Usuario "+Clave);
        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(System.currentTimeMillis());
		int num = 0;
		if (Clave ==null) {
			System.out.println("Valores Incorrectos");
			return "no_existe";
		}else {
			//System.out.println("Decodificando Clave de eMail del Usuario");
			System.out.println("Codificado:"+Clave);
			byte[] decode = Base64.getDecoder().decode(Clave);
			String decodi = new String(decode);
			System.out.println("Decodificado:"+decodi);
			global = decodi;
			Usuarios UsuariosReturn = UsuariosRepository.findByOrigenEMailEstatus(Constantes.ORIGEN, decodi, Constantes.INT_ESTATUS_CRO);
			if (UsuariosReturn==null) {
				System.out.println("Clave de Usuario Caduca!");
				num = LogUsuariosRepository.insertData(Constantes.ORIGEN, decodi, currentDate, currentTime, "Error al Validar Correo desde el Link del Usuario.");
			}else {
			    model.addAttribute("usuario", UsuariosReturn);
				System.out.println("Clave de Usuario: "+ decodi + " Existe!, puede Escribir su Contraseña!");
                num = LogUsuariosRepository.insertData(UsuariosReturn.getOrigen(), UsuariosReturn.getEmail(), currentDate, currentTime, "Se valida Correo");
                UsuariosReturn.setEstatus(Constantes.INT_ESTATUS_TRES);
                num = UsuariosRepository.usuarioUpdateEstatus(UsuariosReturn);
				return "correoValidado";
			}
		}
		return "error";
	}

	/**
	 * Método genérico que procesa la validación de un blog según el origen.
	 *
	 * @param origenLog   Constante de origen para los logs (ej. Constantes.ORIGEN_TIPO_TRABAJADOR)
	 * @param clave       Clave codificada en Base64
	 * @param fecha       Fecha codificada en Base64 (formato original "EEE MMM dd HH:mm:ss zzz yyyy")
	 * @param hora        Hora codificada en Base64
	 * @param model       Modelo de Spring para agregar atributos
	 * @param findFunc    Función que busca el blog dados (clave, fecha, hora) decodificados
	 * @param updateFunc  Función que actualiza el blog dados (clave, fecha, hora) decodificados
	 * @return Nombre de la vista a renderizar
	 */
	private String procesarValidation(String origenLog, String clave, String fecha, String hora, Model model) {
	    System.out.println("000078 procesarValidation  Validando " + origenLog + ": " + clave + "/" + fecha + "/" + hora);

	    //if (clave == null || fecha == null || hora == null) {
	    //    return "no_existe";
	    //}

	    try {
	        // 1. DECODIFICAR PRIMERO
	        String decodiClave = new String(Base64.getDecoder().decode(clave));
	        String decodiFechaCruda = new String(Base64.getDecoder().decode(fecha));
	        String decodiHora = new String(Base64.getDecoder().decode(hora));

	        // Formatear fecha
	        SimpleDateFormat sdfEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	        java.util.Date fechaUtil = sdfEntrada.parse(decodiFechaCruda);
	        SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd");
	        String decodiFecha = sdfSalida.format(fechaUtil);

	        // 2. BUSCAR EL BLOG (Usando los valores ya decodificados)
	        // Nota: Asegúrate de que 'crudBlog' esté inyectado en la clase
	        System.out.println("00055 procesarValidation 				origenLog: " + origenLog + ",  decodiClave: " + decodiClave + ", decodiFecha: " + decodiFecha + ", decodiHora: " + decodiHora);
	        Optional<Blog> optBlog = crudBlog.findOneByOrigenAndEmailAndFechaAndHora(origenLog, decodiClave, decodiFecha, decodiHora);

	        if (!optBlog.isPresent()) {
	            System.out.println("Clave de Usuario Caduca o No Existe!");
	            LogUsuariosRepository.insertData(origenLog, decodiClave,
	                    new java.sql.Date(System.currentTimeMillis()),
	                    new java.sql.Time(System.currentTimeMillis()),
	                    "Error en " + origenLog + " al Validar Correo");
	            return "no_existe";
	        }

	        // 3. SI EXISTE, PROCESAR ÉXITO
	        Blog blog = optBlog.get();
	        model.addAttribute("blog", blog);
	        
	        System.out.println("Clave de Usuario: " + decodiClave + " Existe!, se activa Comentario!");

	        LogUsuariosRepository.insertData(origenLog, blog.getEmail(),
	                new java.sql.Date(System.currentTimeMillis()),
	                new java.sql.Time(System.currentTimeMillis()),
	                "Se valida Correo");

	        // Actualizar estatus
	        blog.setEstatus(Constantes.INT_ESTATUS_TRES);
	        crudBlog.save(blog); // Guardar cambios directamente

	        return "blog/comentario_validado";

	    } catch (ParseException e) {
	        System.err.println("Error parseando fecha: " + e.getMessage());
	        return "error";
	    } catch (Exception e) {
	        System.err.println("Error inesperado: " + e.getMessage());
	        e.printStackTrace();
	        return "error";
	    }
	}




    @GetMapping("/validaBlog/{origen}/{clave}/{fecha}/{hora}")
    public String validaBlogDinamico(@PathVariable String origen,
                                     @PathVariable String clave,
                                     @PathVariable String fecha,
                                     @PathVariable String hora,
                                     Model model) {
	    
	    // Lógica de búsqueda usando el origen dinámico
		System.out.println("origen: " + origen + ",  clave: " + clave + ", fecha: " + fecha + ", hora: " + hora);
		// Llamada al método que hará el trabajo pesado
	    return procesarValidation(origen, clave, fecha, hora, model);
	}
	
	
}