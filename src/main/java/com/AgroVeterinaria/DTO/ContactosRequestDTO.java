package com.AgroVeterinaria.DTO;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactosRequestDTO {

    @NotBlank(message = "El origen es obligatorio")
    private String origen;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 2000, message = "Año debe ser mayor a 1999")
    private Integer annio;

    @NotNull(message = "El mes es obligatorio")
    @Min(value = 1, message = "Mes debe estar entre 1 y 12")
    @Max(value = 12, message = "Mes debe estar entre 1 y 12")
    private Integer mes;

    @NotNull(message = "El día es obligatorio")
    @Min(value = 1, message = "Día debe estar entre 1 y 31")
    @Max(value = 31, message = "Día debe estar entre 1 y 31")
    private Integer dia;

    @NotNull(message = "La hora es obligatorio")
    @Min(value = 0, message = "Hora debe estar entre 0 y 23")
    @Max(value = 23, message = "Hora debe estar entre 0 y 23")
    private Integer hora;

    @NotNull(message = "El minuto es obligatorio")
    @Min(value = 0, message = "Minuto debe estar entre 0 y 59")
    @Max(value = 59, message = "Minuto debe estar entre 0 y 59")
    private Integer minuto;

    @Min(value = 0, message = "Segundos debe estar entre 0 y 59")
    @Max(value = 59, message = "Segundos debe estar entre 0 y 59")
    private Integer segundos;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Formato de teléfono inválido (solo números y opcional '+')")
    private String telefono;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Formato de email inválido")
    private String email;  // puede ser null

    private String estatus; // puede ser null
    private String nombre;
    private String mensaje;
    private String fechaCita;   // en formato "yyyy-MM-dd"
    private String horaCita;    // en formato "HH:mm"

    public ContactosRequestDTO() {}
	public ContactosRequestDTO(@NotBlank(message = "El origen es obligatorio") String origen,
			@NotNull(message = "El año es obligatorio") @Min(value = 2000, message = "Año debe ser mayor a 1999") Integer annio,
			@NotNull(message = "El mes es obligatorio") @Min(value = 1, message = "Mes debe estar entre 1 y 12") @Max(value = 12, message = "Mes debe estar entre 1 y 12") Integer mes,
			@NotNull(message = "El día es obligatorio") @Min(value = 1, message = "Día debe estar entre 1 y 31") @Max(value = 31, message = "Día debe estar entre 1 y 31") Integer dia,
			@NotNull(message = "La hora es obligatorio") @Min(value = 0, message = "Hora debe estar entre 0 y 23") @Max(value = 23, message = "Hora debe estar entre 0 y 23") Integer hora,
			@NotNull(message = "El minuto es obligatorio") @Min(value = 0, message = "Minuto debe estar entre 0 y 59") @Max(value = 59, message = "Minuto debe estar entre 0 y 59") Integer minuto,
			Integer segundos,
			@NotBlank(message = "El teléfono es obligatorio") @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Formato de teléfono inválido (solo números y opcional '+')") String telefono,
			@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Formato de email inválido") String email,
			String estatus, String nombre, String mensaje, String fechaCita, String horaCita) {
		super();
		this.origen = origen;
		this.annio = annio;
		this.mes = mes;
		this.dia = dia;
		this.hora = hora;
		this.minuto = minuto;
		this.segundos = segundos;
		this.telefono = telefono;
		this.email = email;
		this.estatus = estatus;
		this.nombre = nombre;
		this.mensaje = mensaje;
		this.fechaCita = fechaCita;
		this.horaCita = horaCita;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Integer getAnnio() {
		return annio;
	}
	public void setAnnio(Integer annio) {
		this.annio = annio;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public Integer getMinuto() {
		return minuto;
	}
	public void setMinuto(Integer minuto) {
		this.minuto = minuto;
	}
	public Integer getSegundos() {
		return segundos;
	}
	public void setSegundos(Integer segundos) {
		this.segundos = segundos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getFechaCita() {
		return fechaCita;
	}
	public void setFechaCita(String fechaCita) {
		this.fechaCita = fechaCita;
	}
	public String getHoraCita() {
		return horaCita;
	}
	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}
	
	@Override
	public String toString() {
		return "ContactosRequestDTO [origen=" + origen + ", annio=" + annio + ", mes=" + mes + ", dia=" + dia
				+ ", hora=" + hora + ", minuto=" + minuto + ", segundos=" + segundos + ", telefono=" + telefono
				+ ", email=" + email + ", estatus=" + estatus + ", nombre=" + nombre + ", mensaje=" + mensaje
				+ ", fechaCita=" + fechaCita + ", horaCita=" + horaCita + "]";
	}
}