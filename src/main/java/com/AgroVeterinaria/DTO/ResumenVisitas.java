package com.AgroVeterinaria.DTO;

public class ResumenVisitas {
	private int global;
	private int escritorio;
	private int movil;
	private int tablet;
	private int otros;
	
	public ResumenVisitas() {}
	public ResumenVisitas(int global, int escritorio, int movil, int tablet) {
		super();
		this.global = global;
		this.escritorio = escritorio;
		this.movil = movil;
		this.tablet = tablet;
	}
	public int getGlobal() {
		return global;
	}
	public void setGlobal(int global) {
		this.global = global;
	}
	public int getEscritorio() {
		return escritorio;
	}
	public void setEscritorio(int escritorio) {
		this.escritorio = escritorio;
	}
	public int getMovil() {
		return movil;
	}
	public void setMovil(int movil) {
		this.movil = movil;
	}
	public int getTablet() {
		return tablet;
	}
	public void setTablet(int tablet) {
		this.tablet = tablet;
	}
	public int getOtros() {
		return otros;
	}
	public void setOtros(int otros) {
		this.otros = otros;
	}
	
	@Override
	public String toString() {
		return "ResumenVisitas [Global=" + global + ", Escritorio=" + escritorio + ", Movil=" + movil + ", Tablet="
				+ tablet + ", Otros=" + otros + "]";
	}
}