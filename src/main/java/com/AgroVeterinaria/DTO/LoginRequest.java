package com.AgroVeterinaria.DTO;
import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "tax_id es obligatorio")
    private String taxId;
    
    @NotBlank(message = "password es obligatorio")
    private String password;

    public LoginRequest() {
        super();
    }
    
	public LoginRequest(@NotBlank(message = "tax_id es obligatorio") String taxId,
			@NotBlank(message = "password es obligatorio") String password) {
		super();
		this.taxId = taxId;
		this.password = password;
	}

	public String getTaxId() {return taxId;}
	public void setTaxId(String taxId) {this.taxId = taxId;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	@Override
	public String toString() {
		return "LoginRequest [taxId=" + taxId + ", password=" + password + "]";
	}
}