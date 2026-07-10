package com.AgroVeterinaria.Registro.LogUsuarios;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class LogUsuariosPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private String origen;
	private String email;
	private Date Fecha;
	private Time Hora;
	private String Descripcion;
	
	public LogUsuariosPK() {
	}

	public LogUsuariosPK(String origen, String email) {
		//super();
		this.origen = origen;
		this.email = email;
	}

	public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public Time getHora() {
        return Hora;
    }

    public void setHora(Time hora) {
        Hora = hora;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


	@Override
    public String toString() {
        return "logUsuariosPK [origen=" + origen + ", email=" + email + ", Fecha=" + Fecha + ", Hora=" + Hora
                + ", Descripcion=" + Descripcion + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(Descripcion, Fecha, Hora, email, origen);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogUsuariosPK other = (LogUsuariosPK) obj;
        return Objects.equals(Descripcion, other.Descripcion) && Objects.equals(Fecha, other.Fecha)
                && Objects.equals(Hora, other.Hora) && Objects.equals(email, other.email)
                && Objects.equals(origen, other.origen);
    }
}