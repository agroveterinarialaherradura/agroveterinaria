package com.AgroVeterinaria.DTO;

public class tpersonas { 
	private Integer id;
	private String TPersona;
	
	public tpersonas() {	}
	public tpersonas(Integer id, String tpersona) {	
		this.id = id;
		this.TPersona = tpersona;
	}

	public Integer getId() {		System.out.println("getId: " + id);		return id;	}
	public void setId(Integer id) {		System.out.println("setId: " + id);		this.id = id;	}

	public String getNombre() {		return TPersona;	}
	public void setNombre(String tpersona) {		this.TPersona = tpersona;	}

	@Override
	public String toString() {
		return "TPersona [id=" + id + ", TPersonas=" + TPersona + "]";
	}
}