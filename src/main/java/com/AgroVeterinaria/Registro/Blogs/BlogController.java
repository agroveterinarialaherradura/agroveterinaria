package com.AgroVeterinaria.Registro.Blogs;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AgroVeterinaria.DTO.Articulo;
import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.DTO.Visitas;
import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Visitas.DataSourceVisitas;
import com.AgroVeterinaria.Registro.Visitas.VisitasService;

@CrossOrigin()
@Controller
@RequestMapping(path="")
public class BlogController {

    @Autowired
    private BlogAsyncService blogAsyncService; 
    
    @Autowired
    private DataSourceBlog crudBlog;
	
    @Autowired
    private DataSourceVisitas dataSourceVisitas;

    @Autowired
    private HttpServletRequest request; // Para capturar el User-Agent automáticamente

    @Autowired
    private DataSourceArticulos dataSourceArticulos;
    
    @Autowired
    private HttpSession session;
    
	@Autowired
    private VisitasService visitasService;
	
    /***
     * @param Blogs
     * @return blog
     * 
    
    @GetMapping("/{urlBuscar:^(?!sitemap\\.xml$|robots\\.txt$|favicon\\.ico$).*$}")
    
    @GetMapping(path="/blog")
    public String Blog(Usuarios usuario, Model model) {
    	model.addAttribute("stats", dataSourceVisitas.findVisitasResumenByOrigen("blog"));
        System.out.println("Blog:"+ usuario.toString());
        return "blog/blog";
    }
    */
    
    
    @GetMapping("/{urlBuscar:^(?!sitemap\\.xml$|robots\\.txt$|favicon\\.ico$).*$}")
    public String verArticulo(@PathVariable String urlBuscar, Model model) {
    	System.out.println("urlBuscar: " + urlBuscar);
    	
    	// 1. Obtener el producto desde la base de datos por su urlBuscar
    	if ("Portafolio de Servicios Web".equals(urlBuscar)) {
    		return "redirect:/Portafolio de Servicios Web";
    	}
    	
        // Redirigir a productos si corresponde
        //if ("Portafolio de Servicios Web".equals(urlBuscar)) {
        //    return "redirect:/PortafolioServicioWeb"; // ← Redirige al nuevo controlador
        //}
        
        // 2. Obtener el artículo desde la base de datos por su urlBuscar
        Articulo articulo = dataSourceArticulos.obtenerArticuloPorUrlBuscar(urlBuscar);
        if (articulo == null) {
            return "blog/NoExiste"; // o lanzar excepción
        }
        
        // 3. Usamos el campo "origen" del artículo para las visitas y comentarios
        String origen = articulo.getOrigen();
        System.out.println("Origen: " + origen);
        System.out.println("urlBuscar: " + urlBuscar);
        System.out.println("urlOrigen: " + articulo.getUrlOrigen());
        model.addAttribute("stats", dataSourceVisitas.findVisitasResumenByOrigen(urlBuscar));
        
        // 4. Crear objeto Blog para el formulario de comentarios
        Blog blogForm = new Blog();
        blogForm.setOrigen(articulo.getUrlOrigen());
        //System.out.println("Origen: " + articulo.getUrlOrigen());
        model.addAttribute("blog", blogForm);
        
        // 5. Lista de comentarios
        List<Blog> comentarios = crudBlog.findUltimosDiezByOrigen(articulo.getUrlOrigen(), Constantes.INT_ESTATUS_TRES);
        //System.out.println("comentarios:" + comentarios.toString());
        model.addAttribute("blogs", comentarios);
        
        // 6. Retornar la vista específica de ese artículo, usando urlOrigen de la BD
        //    Ejemplo: si urlOrigen = "AbogadoBrujo" → "blog/AbogadoBrujo"
        System.out.println("Ruta: blog/" + articulo.getUrlOrigen());
        return "blog/" + articulo.getUrlOrigen();
    }
    
    @PostMapping(path = "/registroBlog")
    public String addNewBlog(Blog blog) {
        System.out.println("Post blog addNewBlog: " + blog.toString());

        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(System.currentTimeMillis());

        // Establecer campos comunes (fecha, hora, estatus)
        blog.setFecha(currentDate.toString());
        blog.setHora(currentTime.toString());
        blog.setEstatus(0); // Blog registrado = 0, validado = 2

        // El campo 'origen' ya viene en el objeto Blog desde el formulario
        // (se envía mediante un campo oculto), por lo que NO lo sobreescribimos.

        System.out.println("Post Blog procesado: " + blog.toString());

        try {
            blogAsyncService.procesarRegistroCompleto(blog);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "blog/registroBlog"; // Vista de éxito
    }
    
    private void registrarVisita(String origen, Model model) {
        try {
            // 1. Verificar si ya se contó esta sección en la sesión actual
            String sessionKey = "visitado_" + origen;
            boolean yaContado = request.getSession().getAttribute(sessionKey) != null;

            if (!yaContado) {
                LocalDateTime ahora = LocalDateTime.now();
                String ua = request.getHeader("User-Agent");
                
                // Capturar la IP Real (Considerando el proxy de Railway)
                String ipCliente = request.getHeader("X-Forwarded-For");
                if (ipCliente == null || ipCliente.isEmpty()) {
                    ipCliente = request.getRemoteAddr();
                } else {
                    ipCliente = ipCliente.split(",")[0].trim();
                }
                
                String dispositivo = "Escritorio";
                if (ua != null) {
                    ua = ua.toLowerCase();
                    if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) dispositivo = "Movil";
                    else if (ua.contains("tablet") || ua.contains("ipad")) dispositivo = "Tablet";
                }

                // Crear el objeto Visitas con los 10 parámetros del constructor
                Visitas v = new Visitas(
                    ahora.getYear(), ahora.getMonthValue(), ahora.getDayOfMonth(),
                    ahora.getHour(), ahora.getMinute(), ahora.getSecond(),
                    origen, dispositivo, 1L, ipCliente
                );
                
                // Solo incrementamos en la base de datos si NO ha sido contado
                dataSourceVisitas.usuarioUpdateVisitas(v);
                
                // Marcamos la sección como visitada en la sesión guardando la IP
                request.getSession().setAttribute(sessionKey, ipCliente);
            }

            // 2. Cargar el resumen para el footer
            model.addAttribute("stats", dataSourceVisitas.findVisitasResumenByOrigen(origen));
            
        } catch (Exception e) {
            System.err.println("Error en contador (registrarVisita): " + e.getMessage());
        }
    }


    /**
    @GetMapping("/blog")
    public String mostrarBlog(Model model) {
        List<Articulo> iniciales = dataSourceArticulos.obtenerArticulosPaginados(0, 6);
        model.addAttribute("articulos", iniciales);
        
        // SI está dentro de una carpeta llamada blog:
        return "blog/blog"; 
    }*/
    
    @GetMapping("/blog")
    public String mostrarBlog(Model model) {
        // Obtener o generar la lista aleatoria de IDs
        @SuppressWarnings("unchecked")
        List<String> idsAleatorios = (List<String>) session.getAttribute("idsAleatorios");
        if (idsAleatorios == null) {
            idsAleatorios = dataSourceArticulos.obtenerTodosLosIds();
            Collections.shuffle(idsAleatorios);
            session.setAttribute("idsAleatorios", idsAleatorios);
            session.setAttribute("esAleatorio", true);
        }
        
        // Primera página
        int pagina = 0;
        int tamano = 6;
        int start = pagina * tamano;
        int end = Math.min(start + tamano, idsAleatorios.size());
        List<String> idsPagina = idsAleatorios.subList(start, end);
        
        List<Articulo> iniciales = dataSourceArticulos.obtenerArticulosPorIds(idsPagina);
        model.addAttribute("articulos", iniciales);
        // Guardamos en sesión la página actual (por si se usa en el API)
        session.setAttribute("paginaAleatoriaActual", pagina);
        return "blog/blog";
    }

    /** 
     * 
	@GetMapping("/blog/api/articulos")
	String apiArticulos
		@RequestParam ( value = q)
		@RequestParam(defaultValue)
     
    @GetMapping("/blog/api/articulos")
    public String apiArticulos(@RequestParam(value = "q", required = false) String q, 
                               @RequestParam(defaultValue = "0") int page, Model model) {
        List<Articulo> lista;
        
        // Si q es null o "null" (por error de JS), lo tratamos como búsqueda vacía
        if (q == null || q.trim().isEmpty() || q.equals("null")) {
            lista = dataSourceArticulos.obtenerArticulosPaginados(page, 6);
        } else {
            lista = dataSourceArticulos.buscarArticulosPaginados(q, page, 6);
        }
        
        model.addAttribute("articulos", lista);
        // IMPORTANTE: Asegúrate que la ruta al fragmento sea la correcta
        return "blog/blog-principal :: lista-articulos";
    }*/
    @GetMapping("/blog/api/articulos")
    public String apiArticulos(@RequestParam(value = "q", required = false) String q, 
                               @RequestParam(defaultValue = "0") int page, 
                               Model model) {
        List<Articulo> lista;
        
        if (q == null || q.trim().isEmpty() || q.equals("null")) {
            // Sin búsqueda → usamos orden aleatorio fijo en sesión
            @SuppressWarnings("unchecked")
            List<String> idsAleatorios = (List<String>) session.getAttribute("idsAleatorios");
            if (idsAleatorios == null) {
                // Si no existe (por si acceden directamente a la API sin pasar por /blog), lo generamos
                idsAleatorios = dataSourceArticulos.obtenerTodosLosIds();
                Collections.shuffle(idsAleatorios);
                session.setAttribute("idsAleatorios", idsAleatorios);
            }
            int tamano = 6;
            int start = page * tamano;
            int end = Math.min(start + tamano, idsAleatorios.size());
            if (start >= idsAleatorios.size()) {
                lista = Collections.emptyList();
            } else {
                List<String> idsPagina = idsAleatorios.subList(start, end);
                lista = dataSourceArticulos.obtenerArticulosPorIds(idsPagina);
            }
        } else {
            // Búsqueda → orden cronológico descendente (sin aleatoriedad)
            lista = dataSourceArticulos.buscarArticulosPaginados(q, page, 6);
        }
        
        model.addAttribute("articulos", lista);
        return "blog/blog-principal :: lista-articulos";
    }

    @GetMapping("/blog/randomize")
    public String randomizeOrder() {
        // Forzar regeneración de la lista aleatoria
        session.removeAttribute("idsAleatorios");
        return "redirect:/blog";
    }
    
}