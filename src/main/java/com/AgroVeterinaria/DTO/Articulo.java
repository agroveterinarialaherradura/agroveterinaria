package com.AgroVeterinaria.DTO;
import java.sql.Date;

public class Articulo {
    private String titulo;      // PK en tu tabla
    private String pregunta;
    private String comentario;  // Este suele usarse como el "resumen" de la tarjeta
    private String respuesta;
    private String origen;
    private String urlBase;
    private String urlBuscar;
    private String urlOrigen;
    private Date fecha;
    private int estatus;
    private String autor;
    private String urlImagen;
    private String urlCard;
    
    public Articulo() {}
	public Articulo(String titulo, String pregunta, String comentario, String respuesta, String origen, String urlBase,
			String urlBuscar, String urlOrigen, Date fecha, int estatus, String autor, String urlImagen,
			String urlCard) {
		super();
		this.titulo = titulo;
		this.pregunta = pregunta;
		this.comentario = comentario;
		this.respuesta = respuesta;
		this.origen = origen;
		this.urlBase = urlBase;
		this.urlBuscar = urlBuscar;
		this.urlOrigen = urlOrigen;
		this.fecha = fecha;
		this.estatus = estatus;
		this.autor = autor;
		this.urlImagen = urlImagen;
		this.urlCard = urlCard;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getUrlBase() {
		return urlBase;
	}
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	public String getUrlBuscar() {
		return urlBuscar;
	}
	public void setUrlBuscar(String urlBuscar) {
		this.urlBuscar = urlBuscar;
	}
	public String getUrlOrigen() {
		return urlOrigen;
	}
	public void setUrlOrigen(String urlOrigen) {
		this.urlOrigen = urlOrigen;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public String getUrlCard() {
		return urlCard;
	}
	public void setUrlCard(String urlCard) {
		this.urlCard = urlCard;
	}
	@Override
	public String toString() {
		return "Articulo [titulo=" + titulo + ", pregunta=" + pregunta + ", comentario=" + comentario + ", respuesta="
				+ respuesta + ", origen=" + origen + ", urlBase=" + urlBase + ", urlBuscar=" + urlBuscar
				+ ", urlOrigen=" + urlOrigen + ", fecha=" + fecha + ", estatus=" + estatus + ", autor=" + autor
				+ ", urlImagen=" + urlImagen + ", urlCard=" + urlCard + "]";
	}
}