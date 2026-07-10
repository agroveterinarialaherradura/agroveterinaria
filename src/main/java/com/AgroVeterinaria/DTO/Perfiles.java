package com.AgroVeterinaria.DTO;

public class Perfiles { 

	private Integer id;
	private String Nombre;
	private Integer Padre;
	
	public Perfiles() {	}
	public Perfiles(Integer id, String estatus, Integer idPadre) {		
		this.id = id;
		this.Nombre = estatus;
		this.Padre = idPadre;
	}

	public Integer getId() {System.out.println("getId: " + id);		return id;	}
	public void setId(Integer id) {System.out.println("setId: " + id);		this.id = id;	}

	public String getNombre() {		return Nombre;	}
	public void setNombre(String estatus) {		this.Nombre = estatus;	}

	public Integer getPerfil() {		return Padre;	}
	public void setPerfil(Integer id) {		this.Padre = id;	}

	@Override
	public String toString() {
		return "Estatus [id=" + id + ", Estatus=" + Nombre + ", Padre=" + Padre + "]";
	}
}