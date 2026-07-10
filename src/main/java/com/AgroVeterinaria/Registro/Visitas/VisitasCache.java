package com.AgroVeterinaria.Registro.Visitas;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class VisitasCache {

    // clave: origen|dispositivo
    // valor: mapa de IP -> contador
    private final Map<String, Map<String, Long>> cache = new ConcurrentHashMap<>();

    public void incrementar(String origen, String dispositivo, String ip) {

        String key = origen + "|" + dispositivo;

        cache.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
             .merge(ip, 1L, Long::sum);
    }

    public Map<String, Map<String, Long>> obtenerYLimpiar() {
        Map<String, Map<String, Long>> copia = new ConcurrentHashMap<>(cache);
        cache.clear();
        return copia;
    }
}