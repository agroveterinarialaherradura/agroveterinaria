package com.AgroVeterinaria.DTO;

public class estatus { 

	private Integer id;
	private String Nombre;
	
	public estatus() {}
	public estatus(Integer id, String estatus) {		
		this.id = id;
		this.Nombre = estatus;
	}

	public Integer getId() {		System.out.println("getId: " + id);		return id;	}
	public void setId(Integer id) {		System.out.println("setId: " + id);		this.id = id;	}

	public String getNombre() {		return Nombre;	}
	public void setNombre(String estatus) {		this.Nombre = estatus;	}

	@Override
	public String toString() {
		return "Estatus [id=" + id + ", Estatus=" + Nombre + "]";
	}
}