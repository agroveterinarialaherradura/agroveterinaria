package com.AgroVeterinaria.DTO;

public class Blog {

    private String origen;
    private String email;
    private String fecha;
    private String hora;
    private String comentario;
    private Integer estatus;
    
    public Blog() {};
	public Blog(String origen, String email, String fecha, String hora, String comentario, Integer estatus) {
		super();
		this.origen = origen;
		this.email = email;
		this.fecha = fecha;
		this.hora = hora;
		this.comentario = comentario;
		this.estatus = estatus;
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
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	@Override
	public String toString() {
		return "Blog [origen=" + origen + ", email=" + email + ", fecha=" + fecha + ", hora=" + hora + ", comentario="
				+ comentario + ", estatus=" + estatus + "]";
	}
}