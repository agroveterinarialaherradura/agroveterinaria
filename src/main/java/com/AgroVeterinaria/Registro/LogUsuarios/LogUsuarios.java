package com.AgroVeterinaria.Registro.LogUsuarios;
import java.sql.Date;
import java.sql.Time;

public class LogUsuarios{
	private String origen;
	private String email;
	private Date Fecha;
	private Time Hora;

	private String Descripcion;

	public LogUsuarios() {	}
	public LogUsuarios(String origen, String email, Date fecha, Time hora, String Descripcion) {		
			this.origen = origen;
			this.email = email;
			this.Fecha = fecha;
			this.Hora = hora;
			this.Descripcion = Descripcion;
	}

    public String getOrigen() {        return origen;    }
    public void setOrigen(String origen) {        this.origen = origen;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public Date getFecha() {        return Fecha;    }
    public void setFecha(Date fecha) {        Fecha = fecha;    }

    public Time getHora() {        return Hora;    }
    public void setHora(Time hora) {        Hora = hora;    }

    public String getDescripcion() {        return Descripcion;    }
    public void setDescripcion(String descripcion) {        Descripcion = descripcion;    }

    @Override
    public String toString() {
        return "LogUsuarios [origen=" + origen + ", email=" + email + ", Fecha=" + Fecha + ", Hora=" + Hora
                + ", Descripcion=" + Descripcion + "]";
    }
}