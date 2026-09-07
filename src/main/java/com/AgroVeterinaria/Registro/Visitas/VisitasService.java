package com.AgroVeterinaria.Registro.Visitas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import com.AgroVeterinaria.DTO.Visitas;
import com.AgroVeterinaria.Herramientas.Constantes;

@Service
public class VisitasService implements VisitasPort {

 @Autowired
 private RestTemplate restTemplate;

 @Value("${external.visitas.api.url}")
 private String externalApiUrl;

 @Override
 public void registrarVisitaSiNueva(HttpServletRequest request, String origen) {
     String sessionKey = "visitado_" + origen;
     
     // Verificar si ya se registró esta visita en la sesión (evita contar refrescos)
     if (request.getSession().getAttribute(sessionKey) == null) {
         String ipCliente = obtenerIpCliente(request);
         String dispositivo = clasificarDispositivo(request.getHeader("User-Agent"));
         
         LocalDateTime ahora = LocalDateTime.now();
         
         Visitas v = new Visitas(
             ahora.getYear(),
             ahora.getMonthValue(),
             ahora.getDayOfMonth(),
             ahora.getHour(),
             ahora.getMinute(),
             ahora.getSecond(),
             origen,
             dispositivo,
             1L,
             ipCliente
         );

         // Enviar asíncronamente al servicio externo usando @Async
         enviarVisitaAlServicioExterno(v);
         
         // Marcar como visitado en la sesión
         request.getSession().setAttribute(sessionKey, ipCliente);
         
         // Log para debug
         System.out.println("✅ Nueva visita registrada [" + origen + "] desde IP: " + ipCliente + " | Dispositivo: " + dispositivo);
     } else {
         // Log opcional para verificar que no se duplican
         System.out.println("🔄 Refresh ignorado para [" + origen + "] - Sesión ya registrada");
     }
 }

 @Override
 @Async("taskExecutor")  // Usa el AsyncConfig que ya tienes
 public void enviarVisitaAlServicioExterno(Visitas visita) {
     try {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         
         HttpEntity<Visitas> requestEntity = new HttpEntity<>(visita, headers);
         
         // Realizar la petición POST al servicio externo
         restTemplate.postForEntity(externalApiUrl, requestEntity, String.class);
         
         System.out.println("📤 Visita enviada al servicio externo: " + visita.getOrigen());
         
     } catch (Exception e) {
         // Solo logueamos el error, pero NO interrumpimos la petición del usuario
         System.err.println("❌ ERROR al enviar visita al servicio externo: " + e.getMessage());
         // Aquí podrías enviar el error a un sistema de logs como Sentry
     }
 }

 @Override
 public String clasificarDispositivo(String userAgent) {
     if (userAgent == null) return "Escritorio";
     String ua = userAgent.toLowerCase();
     if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) {
         return "Movil";
     }
     if (ua.contains("tablet") || ua.contains("ipad")) {
         return "Tablet";
     }
     return "Escritorio";
 }

 @Override
 public String obtenerIpCliente(HttpServletRequest request) {
     String ip = request.getHeader("X-Forwarded-For");
     if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getRemoteAddr();
     }
     // Si hay múltiples IPs (por ejemplo, detrás de un proxy), tomar la primera
     return ip.contains(",") ? ip.split(",")[0].trim() : ip;
 }

 @Override
 public String determinarOrigen(String uri) {
     // Si es la raíz o index, usar el origen principal
     if (uri.equals("/") || uri.equals("/index")) {
         return Constantes.ORIGEN_MAY;
     }
     // Decodificar URI y eliminar la barra inicial
     try {
         return URLDecoder.decode(uri.substring(1), "UTF-8");
     } catch (Exception e) {
         return uri.substring(1);
     }
 }
}