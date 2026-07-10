package com.AgroVeterinaria.Registro.Visitas;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.AgroVeterinaria.DTO.Visitas;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class VisitasFlushService {

    @Autowired
    private VisitasCache visitasCache;

    @Autowired
    private DataSourceVisitas dataSourceVisitas;

    @Scheduled(fixedRate = 30000)
    public void flushCache() {

        Map<String, Map<String, Long>> datos = visitasCache.obtenerYLimpiar();

        if (datos.isEmpty()) return;
        LocalDateTime ahora = LocalDateTime.now();

        for (Map.Entry<String, Map<String, Long>> entry : datos.entrySet()) {

            String[] partes = entry.getKey().split("\\|");
            String origen = partes[0];
            String dispositivo = partes[1];

            Map<String, Long> ips = entry.getValue();

            for (Map.Entry<String, Long> ipEntry : ips.entrySet()) {

                String ip = ipEntry.getKey();
                Long valor = ipEntry.getValue();

                Visitas v = new Visitas(
                    ahora.getYear(),
                    ahora.getMonthValue(),
                    ahora.getDayOfMonth(),
                    ahora.getHour(),
                    ahora.getMinute(),
                    ahora.getSecond(),
                    origen,
                    dispositivo,
                    valor,
                    ip
                );

                dataSourceVisitas.usuarioUpdateVisitas(v);
            }
        }

        System.out.println("Cache con IPs guardado en BD");
    }
}