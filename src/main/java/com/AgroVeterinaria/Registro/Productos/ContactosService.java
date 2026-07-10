package com.AgroVeterinaria.Registro.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AgroVeterinaria.DTO.ContactosRequestDTO;

@Service
public class ContactosService {

    private final DataSourceContactos dataSourceContactos;

    @Autowired
    public ContactosService(DataSourceContactos dataSourceContactos) {
        this.dataSourceContactos = dataSourceContactos;
    }

    @Transactional
    public boolean guardarContacto(ContactosRequestDTO dto) {
        int filas = dataSourceContactos.insertData(
                dto.getOrigen(),
                dto.getAnnio(),
                dto.getMes(),
                dto.getDia(),
                dto.getTelefono(),      // ← orden corregido
                dto.getEmail(),
                dto.getEstatus(),
                dto.getHora(),
                dto.getMinuto(),
                dto.getSegundos(), 
                dto.getNombre(),
                dto.getMensaje(),
                dto.getFechaCita(),
                dto.getHoraCita()
        );
        return filas > 0;
    }
}