package com.AgroVeterinaria.Controllers;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AgroVeterinaria.Herramientas.Constantes;
import com.AgroVeterinaria.Registro.Blogs.DataSourceArticulos;

@Controller
@RequestMapping(path="")
public class SiteMapsController {
	
    @Autowired
    private DataSourceArticulos dataSourceArticulos;
	
	@GetMapping(value = "/sitemap.xml", produces = "application/xml")
	@ResponseBody
	public String sitemap() {
        // 1. Obtener lista de artículos con url_buscar y fecha
        List<Map<String, Object>> articulos = dataSourceArticulos.obtenerTodosLosUrlBuscarConFecha();
        System.out.println("Cantidad de artículos: " + articulos.size());

        // 2. Construir XML
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // 3. URL raíz (index)
        xml.append("  <url>\n");
        xml.append("    <loc>").append(Constantes.URL_BASE).append("</loc>\n");
        xml.append("    <lastmod>").append(java.time.LocalDate.now()).append("</lastmod>\n");
        xml.append("    <changefreq>daily</changefreq>\n");
        xml.append("    <priority>1.0</priority>\n");
        xml.append("  </url>\n");

        // 4. URL del blog (listado)
        xml.append("  <url>\n");
        xml.append("    <loc>").append(Constantes.URL_BASE).append("blog</loc>\n");
        xml.append("    <lastmod>").append(java.time.LocalDate.now()).append("</lastmod>\n");
        xml.append("    <changefreq>daily</changefreq>\n");
        xml.append("    <priority>0.9</priority>\n");
        xml.append("  </url>\n");

        // 5. URLs de cada artículo
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, Object> row : articulos) {
            String urlBuscar = (String) row.get("url_buscar");
            java.sql.Date fecha = (java.sql.Date) row.get("fecha");
            String fechaStr = (fecha != null) ? sdf.format(fecha) : java.time.LocalDate.now().toString();

            xml.append("  <url>\n");
            xml.append("    <loc>").append(Constantes.URL_BASE).append("").append(urlBuscar).append("</loc>\n");
            xml.append("    <lastmod>").append(fechaStr).append("</lastmod>\n");
            xml.append("    <changefreq>weekly</changefreq>\n");
            xml.append("    <priority>0.5</priority>\n");
            xml.append("  </url>\n");
        }

        xml.append("</urlset>");
        return xml.toString();
    }
}
