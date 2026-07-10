package com.AgroVeterinaria.DTO;

public class productos { 

	private Integer id;
	private String Nombre;
	
	public productos() {	}
	public productos(Integer id) {		
		this.id = id;
		//this.Nombre = producto;
	}

	public Integer getId() {		return id;	}
	public void setId(Integer id) {		this.id = id;	}

	public String getNombre() {		return Nombre;	}
	public void setNombre(String estatus) {		this.Nombre = estatus;	}

	@Override
	public String toString() {
		return "Productos [id=" + id + ", Producto=" + Nombre + "]";
	}
}