package com.AgroVeterinaria.Config;
import com.AgroVeterinaria.Registro.Visitas.VisitasPort;  // ← Importar interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class VisitasInterceptor implements HandlerInterceptor {

    @Autowired
    private VisitasPort visitasService;  // ← Inyectar interfaz

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        
        String uri = request.getRequestURI();
        
        if (debeIgnorar(uri)) {
            return true;
        }

        String origen = visitasService.determinarOrigen(uri);
        visitasService.registrarVisitaSiNueva(request, origen);

        return true;
    }

    private boolean debeIgnorar(String uri) {
        return uri.contains(".") || 
               uri.startsWith("/static") ||
               uri.startsWith("/css") ||
               uri.startsWith("/js") ||
               uri.startsWith("/images") ||
               uri.startsWith("/fonts") ||
               uri.startsWith("/webjars") ||
               uri.startsWith("/api") ||
               uri.equals("/favicon.ico");
    }
}