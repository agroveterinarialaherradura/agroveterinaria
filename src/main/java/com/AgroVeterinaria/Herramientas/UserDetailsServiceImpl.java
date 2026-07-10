package com.AgroVeterinaria.Herramientas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AgroVeterinaria.DTO.Usuarios;
import com.AgroVeterinaria.Registro.Usuarios.DataSourceUsuarios;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private DataSourceUsuarios dataSourceUsuarios;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = dataSourceUsuarios.findByOrigenEMail(Constantes.ORIGEN, email); // Asegúrate de tener este método
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContrasena()) // Debe estar encriptada con BCrypt
                .roles("USER")
                .build();
    }
}