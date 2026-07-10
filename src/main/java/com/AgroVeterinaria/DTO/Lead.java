package com.AgroVeterinaria.DTO;
public class Lead {
    // Campos del formulario (coinciden con los th:field)
    private String origen;
    private String nombre;
    private String email;
    private String telefono;
    private String mensaje;
    
    // Campos adicionales que suelen gestionarse en backend (fecha, hora, estatus)
    private String fecha;
    private String hora;
    private int estatus; // 0 = registrado, 1 = validado, etc.

    public Lead() {    }
    public Lead(String origen, String nombre, String email, String telefono, String mensaje) {
        this.origen = origen;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.mensaje = mensaje;
    }
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	@Override
	public String toString() {
		return "Lead [origen=" + origen + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
				+ ", mensaje=" + mensaje + ", fecha=" + fecha + ", hora=" + hora + ", estatus=" + estatus + "]";
	}
}