package com.AgroVeterinaria.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Herramientas.EmailService;
import com.AgroVeterinaria.Registro.LogUsuarios.DataSourceLogUsuarios;
import com.AgroVeterinaria.Registro.Usuarios.DataSourceUsuarios;
import com.AgroVeterinaria.Registro.Usuarios.UsuarioService;
import com.AgroVeterinaria.Registro.Visitas.DataSourceVisitas;
import com.AgroVeterinaria.Registro.Visitas.VisitasService;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {
    @Autowired
    private EmailService EmailService;
    
	@Autowired
	private DataSourceUsuarios dataSourceUsuarios;
	
	@Autowired
	private DataSourceLogUsuarios LogUsuariosRepository;
	
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private VisitasService visitasService; // Inyectamos tu nuevo servicio
    
    @Autowired
    private DataSourceVisitas dataSourceVisitas;
    
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuarios usuario,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "inicio"; // vuelve al formulario con errores
        }
        try {
        	
            // 🔐 Anti-enumeración
            //if (usuarioService.existeUsuario(usuario.getEmail())) {
            //    System.out.println("Intento registro duplicado");
            //    model.addAttribute("mensaje", "Solicitud procesada");
            //    return "inicio";
            //}
        	
        	usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        	usuario.setOrigen(Constantes.ORIGEN);
        	usuario.setEstatus(Constantes.INT_ESTATUS_CRO);
        	usuario.setPerfil(Constantes.INT_ESTATUS_DOS);
        	// Guardar en BD (usando tu servicio o repositorio)
            usuarioService.registrarUsuario(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Revisa tu correo para activar la cuenta.");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/visitas")
    public String visitas(Model model) {
        try {
            //ResumenVisitas statsPedidos = visitasService.procesarVisitas("pedidos").get();
            ResumenVisitas statsGlobal = visitasService.procesarVisitas("TuPaginaWebHoy").get();
            List<Map<String, Object>> seccionesStats = dataSourceVisitas.findVisitsGroupByOrigen();

            // 🔥 NUEVO: dinámico desde BD
            List<Map<String, Object>> dataBD = dataSourceVisitas.findSeccionesDinamicas();
            System.out.println(" DataBD: " + dataBD.toString());
            
            List<Map<String, String>> seccionesSelector = new ArrayList<>();

            Map<String, String> sec1 = new HashMap<>();
            sec1.put("id", "TuPaginaWebHoy");
            sec1.put("nombre", "Inicio");
            seccionesSelector.add(sec1);

            Map<String, String> sec4 = new HashMap<>();
            sec4.put("id", "blog");
            sec4.put("nombre", "Blog");
            seccionesSelector.add(sec4);

            // 🔥 DINÁMICO DESDE BD
            for (Map<String, Object> row : dataBD) {

                String nombre = (String) row.get("nombre");
                String id = (String) row.get("id");

                Map<String, String> sec = new HashMap<>();
                sec.put("id", id);
                sec.put("nombre", nombre);

                seccionesSelector.add(sec);
            }
            
            model.addAttribute("statsGlobal", statsGlobal != null ? statsGlobal : new ResumenVisitas());
            model.addAttribute("seccionesStats", seccionesStats);
            model.addAttribute("seccionesSelector", seccionesSelector);

        } catch (Exception e) {
            model.addAttribute("statsPedidos", new ResumenVisitas());
            model.addAttribute("statsGlobal", new ResumenVisitas());
            model.addAttribute("seccionesStats", new ArrayList<>());
            model.addAttribute("seccionesSelector", new ArrayList<>());
        }
        return "visitas";
    }
    
    @PostMapping(path="/clave")
    public String addClave(Usuarios usuario) {
        System.out.println("Post usuario:"+ usuario.toString());
        int num = 0;
        usuario.setEstatus(Constantes.INT_ESTATUS_CRO); //Establece el Usuario en Cero!! Nuevo Usuario
        String encode = Base64.getEncoder().encodeToString(usuario.getEmail().getBytes());
        usuario.setContrasena(encode);
        usuario.setUsername(usuario.getEmail());
        System.out.println("Post username:"+ usuario.toString());
        
        Usuarios U = dataSourceUsuarios.findByOrigenEMail(Constantes.ORIGEN, usuario.getEmail());
        if (U == null) {
            //** Primer Registro del Usuario en la BD **/
            try {
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                num = dataSourceUsuarios.updateData(usuario.getOrigen(), usuario.getEmail(), usuario.getContrasena(), usuario.getUsername(), usuario.getEstatus(), usuario.getPerfil() );
                num = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Se Guardo Correo en la BD");             
            } catch(Exception e) {
                System.out.println("Registro de Correo: Error " + e);
                return "error";
            }
            
            try {//Envío de Correo a la cuenta Registrada si Existe!!
                EmailService.sendMail(
                        "ferbethasistemas@gmail.com", 
                        usuario.getEmail(), 
                        "Active su Registro en TuPaginaWebHoy.com ",
                        encode);//EmailService.getHTML()
                        //"Active su Cuenta: " + usuario.getUsername() + " accediendo al siguiente Link: http://betha-spring.herokuapp.com/valida/"+encode);
                Date currentDate = new Date(System.currentTimeMillis());
                Time currentTime = new Time(System.currentTimeMillis());
                usuario.setEstatus(Constantes.INT_ESTATUS_TRES);
                System.out.println("Usuario: " + usuario.toString() );
                //U= dataSourceUsuarios.save(usuario);
                num = dataSourceUsuarios.usuarioUpdateEstatus(usuario);
                num = LogUsuariosRepository.insertData(usuario.getOrigen(), usuario.getEmail(), currentDate, currentTime, "Se envia Mensaje para validar Correo");
            } catch(Exception e) {
                System.out.println("182-AddClave Envio de Correo: Error " + e);
            }
            return "registro";
        }else {
            System.out.println("Post: username" + usuario.getUsername() + " Correo ya Registrado!");
            Usuarios uTemp = new Usuarios();
            return "registrado";
        }
        //return "registrado"; 
    }
	
    
    //@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/public/");
    }

    /**Para que funcione el método Async
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }*/
    
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}