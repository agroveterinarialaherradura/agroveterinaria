package com.AgroVeterinaria.DTO;

public class Visitas {
    private int annio;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;
    private String origen;
    private String dispositivo;
    private Long valor;
    private String ip;
    
    public Visitas() {}
	public Visitas(int annio, int mes, int dia, int hora, int minuto, int segundo, String origen, String dispositivo, Long valor, String ip) {
		super();
		this.annio = annio;
		this.mes = mes;
		this.dia = dia;
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
		this.origen = origen;
		this.valor = valor;
		this.dispositivo = dispositivo;
		this.ip = ip;
	}
	public int getAnnio() {
		return annio;
	}
	public void setAnnio(int annio) {
		this.annio = annio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getMinuto() {
		return minuto;
	}
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}
	public int getSegundo() {
		return segundo;
	}
	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	public String getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}	
	
	@Override
	public String toString() {
		return "VisitasDTO [annio=" + annio + ", mes=" + mes + ", dia=" + dia + ", hora=" + hora + ", minuto=" + minuto
				+ ", segundo=" + segundo + ", origen=" + origen + ", dispositivo=" + dispositivo + ", valor=" + valor + ", ip=" + ip + "]";
	}
    
    
}