package com.AgroVeterinaria.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.AgroVeterinaria.DTO.Visitas;
import com.AgroVeterinaria.Registro.Visitas.DataSourceVisitas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class VisitasInterceptor implements HandlerInterceptor {

    @Autowired
    private DataSourceVisitas dataSourceVisitas;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // 1. Evitar recursos estáticos (CSS, JS, Imágenes)
        String uri = request.getRequestURI();
        if (uri.contains(".") || uri.startsWith("/static")) return true;

        // 2. Definir origen dinámico
        String origen;
        if (uri.equals("/") || uri.equals("/index")) {
            origen = "TuPaginaWebHoy";
        } else {
            // Decodifica la URL para que no guarde "%20" sino espacios reales
            origen = java.net.URLDecoder.decode(uri.substring(1), "UTF-8");
        }

        // 3. Obtener IP real e Identificar dispositivo
        String ipCliente = obtenerIpCliente(request);
        String ua = request.getHeader("User-Agent");
        String dispositivo = clasificarDispositivo(ua);

        // 4. CONTROL DE REFRESH Y SESIÓN UNIFICADO
        String sessionKey = "visitado_" + origen;
        if (request.getSession().getAttribute(sessionKey) == null) {
            
            LocalDateTime ahora = LocalDateTime.now();
            
            // Creamos el objeto incluyendo la IP
            Visitas v = new Visitas(
                ahora.getYear(), ahora.getMonthValue(), ahora.getDayOfMonth(),
                ahora.getHour(), ahora.getMinute(), ahora.getSecond(),
                origen, dispositivo, 1L, ipCliente
            );

            // Guardamos en la base de datos de Railway
            dataSourceVisitas.usuarioUpdateVisitas(v);

            // Marcamos la sesión con la IP para que no cuente de nuevo en el refresh
            request.getSession().setAttribute(sessionKey, ipCliente);
            
            System.out.println("Nueva visita registrada [" + origen + "] desde IP: " + ipCliente);
        }

        return true;
    }

    private String clasificarDispositivo(String ua) {
        if (ua == null) return "Escritorio";
        ua = ua.toLowerCase();
        if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) return "Movil";
        if (ua.contains("tablet") || ua.contains("ipad")) return "Tablet";
        return "Escritorio";
    }

    private String obtenerIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains(",") ? ip.split(",")[0].trim() : ip;
    }
}