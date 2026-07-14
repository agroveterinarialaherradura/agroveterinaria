package com.AgroVeterinaria.Registro.Blogs;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.AgroVeterinaria.DTO.Articulo;
import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.DTO.ResumenVisitas;
import com.AgroVeterinaria.Registro.Blogs.BlogExternasService;
import com.AgroVeterinaria.Registro.Visitas.VisitasExternasService;

@Controller
public class BlogController {

    @Autowired
    private BlogExternasService blogExternasService;

    @Autowired
    private VisitasExternasService visitasExternasService;

    @GetMapping("/{urlBuscar:^(?!sitemap\\.xml$|robots\\.txt$|favicon\\.ico$|Portafolio de Servicios Web$).*$}")
    public String verArticulo(@PathVariable String urlBuscar, Model model) {

        Articulo articulo = blogExternasService.obtenerArticuloPorUrlBuscar(urlBuscar);
        if (articulo == null) {
            return "blog/NoExiste";
        }

        String origen = articulo.getOrigen();
        String urlOrigen = articulo.getUrlOrigen();
        //String urlBuscar = articulo.getUrlBuscar();
        // Estadísticas
        ResumenVisitas stats = visitasExternasService.procesarVisitas(urlBuscar);
        model.addAttribute("stats", stats);

        // Formulario de comentarios
        Blog blogForm = new Blog();
        blogForm.setOrigen(urlOrigen);
        model.addAttribute("blog", blogForm);

        // Comentarios existentes
        List<Blog> comentarios = blogExternasService.obtenerComentarios(urlOrigen);
        model.addAttribute("blogs", comentarios);

        return "blog/" + urlOrigen;
    }

    @PostMapping("/registroBlog")
    public String addNewBlog(Blog blog, RedirectAttributes redirectAttributes) {
        Date currentDate = new Date(System.currentTimeMillis());
        Time currentTime = new Time(System.currentTimeMillis());
        blog.setFecha(currentDate.toString());
        blog.setHora(currentTime.toString());
        blog.setEstatus(0);

        Map<String, String> response = blogExternasService.registrarComentario(blog);
        if (response.containsKey("error")) {
            redirectAttributes.addFlashAttribute("error", response.get("error"));
        } else {
            redirectAttributes.addFlashAttribute("mensaje", response.get("mensaje"));
        }
        return "redirect:/blog";
    }

    @GetMapping("/blog")
    public String mostrarBlog(Model model, HttpSession session) {
        List<String> idsAleatorios = (List<String>) session.getAttribute("idsAleatorios");
        if (idsAleatorios == null) {
            idsAleatorios = blogExternasService.obtenerTodosLosIds();
            Collections.shuffle(idsAleatorios);
            session.setAttribute("idsAleatorios", idsAleatorios);
        }

        int page = 0;
        int size = 6;
        int start = page * size;
        int end = Math.min(start + size, idsAleatorios.size());
        List<String> idsPagina = idsAleatorios.subList(start, end);
        List<Articulo> iniciales = blogExternasService.obtenerArticulosPorIds(idsPagina);

        model.addAttribute("articulos", iniciales);
        session.setAttribute("paginaAleatoriaActual", page);
        return "blog/blog";
    }

    @GetMapping("/blog/api/articulos")
    public String apiArticulos(@RequestParam(value = "q", required = false) String q,
                               @RequestParam(defaultValue = "0") int page,
                               Model model, HttpSession session) {
        List<Articulo> lista;
        if (q == null || q.trim().isEmpty() || q.equals("null")) {
            List<String> idsAleatorios = (List<String>) session.getAttribute("idsAleatorios");
            if (idsAleatorios == null) {
                idsAleatorios = blogExternasService.obtenerTodosLosIds();
                Collections.shuffle(idsAleatorios);
                session.setAttribute("idsAleatorios", idsAleatorios);
            }
            int size = 6;
            int start = page * size;
            int end = Math.min(start + size, idsAleatorios.size());
            if (start >= idsAleatorios.size()) {
                lista = new ArrayList<>();
            } else {
                List<String> idsPagina = idsAleatorios.subList(start, end);
                lista = blogExternasService.obtenerArticulosPorIds(idsPagina);
            }
        } else {
            lista = blogExternasService.buscarArticulos(q, page, 6);
        }
        model.addAttribute("articulos", lista);
        return "blog/blog-principal :: lista-articulos";
    }

    @GetMapping("/blog/randomize")
    public String randomizeOrder(HttpSession session) {
        session.removeAttribute("idsAleatorios");
        return "redirect:/blog";
    }
}