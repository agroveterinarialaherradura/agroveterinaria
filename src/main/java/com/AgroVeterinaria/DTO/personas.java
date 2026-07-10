package com.AgroVeterinaria.DTO;

public class personas { 
	private String rfc;
	
	private String nombre;
	private String paterno;
	private String materno;
	private String razonSocial;
	private Integer TPersona;
	private Integer Sexo;

	public personas() {	}
	public personas(String rfc, String nombre, String paterno, String materno, String razonSocial, Integer TPersona, Integer Sexo) {		
		this.rfc = rfc;
		this.nombre = nombre;
		this.paterno = paterno;
		this.materno = materno;
		this.razonSocial= razonSocial;
		this.TPersona = TPersona;
		this.Sexo = Sexo;
	}


	public String getRFC() {		return rfc;	}
	public void setRFC(String id) {		this.rfc = id;	}

	public String getNombre() {		return nombre;	}
	public void setNombre(String estatus) {		this.nombre = estatus;	}

	public String getPaterno() {		return paterno;	}
	public void setPaterno(String paterno) {		this.paterno = paterno;	}

	public String getMaterno() {		return materno;	}
	public void setMaterno(String materno) {		this.materno = materno;	}

	public String getRazonSocial() {		return razonSocial;	}
	public void setRazonSocial(String razon) {		this.razonSocial = razon;	}

	public Integer getTPersona() {		return TPersona;	}
	public void setTPersona(Integer persona) {		this.TPersona = persona;	}

	public Integer getSexo() {		return Sexo;	}
	public void setSexo(Integer id) {		this.Sexo = id;	}

	@Override
	public String toString() {
		return "Sexo [rfc=" + rfc + ", Nombre=" + nombre + ", Paterno=" + paterno + ", Materno=" + materno + ", RazonSocial=" + razonSocial + ", Sexo=" + Sexo + ", TPersona=" + TPersona +"]";
	}
}