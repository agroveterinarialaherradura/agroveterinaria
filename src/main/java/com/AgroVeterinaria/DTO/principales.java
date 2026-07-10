package com.AgroVeterinaria.DTO;

public class principales { 

	private Integer id;
	private String Nombre;
	
	public principales() {	}
	public principales(Integer id, String principal) {		
		this.id = id;
		this.Nombre = principal;
	}

	public Integer getId() {		return id;	}
	public void setId(Integer id) {		this.id = id;	}

	public String getNombre() {		return Nombre;	}
	public void setNombre(String estatus) {		this.Nombre = estatus;	}

	@Override
	public String toString() {
		return "Principal [id=" + id + ", Principal=" + Nombre + "]";
	}
}