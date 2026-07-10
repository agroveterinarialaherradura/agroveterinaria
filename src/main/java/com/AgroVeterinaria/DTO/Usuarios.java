package com.AgroVeterinaria.DTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class Usuarios {

	private String origen;
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
	private String contrasena;
    
	private String username;
	private Integer estatus;
	private Integer perfil;
	
	public Usuarios() {		super();	}
	public Usuarios(String origen, String email, String contrasena, String username, Integer status, Integer perfil) { 
	    this.origen = origen;	
	    this.email = email;
		this.contrasena= contrasena;
		this.username = username;
		this.estatus = status;
		this.perfil = perfil;
	}

	public String getOrigen() {        return origen;    }
    public void setOrigen(String origen) {        this.origen = origen;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public String getContrasena() {        return contrasena;    }
    public void setContrasena(String contrasena) {        this.contrasena = contrasena;    }

    public String getUsername() {        return username;    }
    public void setUsername(String username) {        this.username = username;    }

    public Integer getEstatus() {        return estatus;    }
    public void setEstatus(Integer estatus) {        this.estatus = estatus;    }

    public Integer getPerfil() {        return perfil;    }
    public void setPerfil(Integer perfil) {        this.perfil = perfil;    }

    @Override
    public String toString() {
        return "Usuarios [origen=" + origen + ", email=" + email + ", contrasena=" + contrasena + ", username="
                + username + ", estatus=" + estatus + ", perfil=" + perfil + "]";
    }
}