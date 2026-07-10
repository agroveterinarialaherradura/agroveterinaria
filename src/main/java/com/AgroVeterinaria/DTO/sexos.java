package com.AgroVeterinaria.DTO;

public class sexos { 
	private Integer id;
	private String Sexo;
	
	public sexos() {	}
	public sexos(Integer id, String sexo) {		
		this.id = id;
		this.Sexo = sexo;
	}

	public Integer getId() {System.out.println("getId: " + id);		return id;	}
	public void setId(Integer id) {System.out.println("setId: " + id);		this.id = id;	}

	public String getNombre() {		return Sexo;	}
	public void setNombre(String estatus) {		this.Sexo = estatus;	}

	@Override
	public String toString() {
		return "Sexo [id=" + id + ", Sexo=" + Sexo + "]";
	}
}