package com.AgroVeterinaria.Registro.Blogs;
import java.io.Serializable;
import java.util.Objects;

public class BlogPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String origen;
    private String email;
    private String fecha;
    private String hora;
    
    public BlogPK() {
    }

    public BlogPK(String origen, String email, String fecha, String hora) {
        this.origen = origen;
        this.email = email;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getOrigen() {        return origen;}
    public void setOrigen(String origen) {        this.origen = origen;}

    public String getEmail() {        return email;}
    public void setEmail(String email) {        this.email = email;}

    public String getFecha() {        return fecha;}
    public void setFecha(String fecha) {        this.fecha = fecha;}

    public String getHora() {        return hora;}
    public void setHora(String hora) {        this.hora = hora;}

    @Override
    public int hashCode() {
        return Objects.hash(email, fecha, hora, origen);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogPK other = (BlogPK) obj;
        return Objects.equals(email, other.email) && Objects.equals(fecha, other.fecha)
                && Objects.equals(hora, other.hora) && Objects.equals(origen, other.origen);
    }
}