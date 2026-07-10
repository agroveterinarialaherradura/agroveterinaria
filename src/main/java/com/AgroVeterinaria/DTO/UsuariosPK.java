package com.AgroVeterinaria.DTO;
import java.io.Serializable;
import java.util.Objects;

public class UsuariosPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String origen;
    private String email;
    
    public UsuariosPK() {
    }

    public UsuariosPK(String origen, String email) {
        //super();
        this.origen = origen;
        this.email = email;
    }

    public String getOrigen() {        return origen;    }
    public void setOrigen(String origen) {        this.origen = origen;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    @Override
    public int hashCode() {
        return Objects.hash(email, origen);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsuariosPK other = (UsuariosPK) obj;
        return Objects.equals(email, other.email) && Objects.equals(origen, other.origen);
    }
}