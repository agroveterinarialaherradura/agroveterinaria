package com.AgroVeterinaria.Registro.Visitas;
import javax.servlet.http.HttpServletRequest;
import com.AgroVeterinaria.DTO.Visitas;

public interface VisitasPort {
 
 void registrarVisitaSiNueva(HttpServletRequest request, String origen);
 
 void enviarVisitaAlServicioExterno(Visitas visita);
 
 String clasificarDispositivo(String userAgent);
 
 String obtenerIpCliente(HttpServletRequest request);
 
 String determinarOrigen(String uri);
}