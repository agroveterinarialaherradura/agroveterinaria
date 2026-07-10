package com.AgroVeterinaria.DTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRequest {
	@NotBlank(message = "email es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "email inválido")
    private String email;
    
    @NotBlank(message = "name es obligatorio")
    private String name;
    
    private String phone; // se valida manualmente
    
    @NotBlank(message = "password es obligatorio")
    private String password;
    
    @NotBlank(message = "tax_id es obligatorio")
    private String taxId;

    public UserRequest() {
    	super();
    }
	public UserRequest(
			@NotBlank(message = "email es obligatorio") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "email inválido") String email,
			@NotBlank(message = "name es obligatorio") String name, String phone,
			@NotBlank(message = "password es obligatorio") String password,
			@NotBlank(message = "tax_id es obligatorio") String taxId) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.taxId = taxId;
	}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getPhone() {return phone;}
	public void setPhone(String phone) {this.phone = phone;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public String getTaxId() {return taxId;}
	public void setTaxId(String taxId) {this.taxId = taxId;}
	@Override
	public String toString() {
		return "UserRequest [email=" + email + ", name=" + name + ", phone=" + phone + ", password=" + password
				+ ", taxId=" + taxId + "]";
	}
}