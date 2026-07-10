package com.AgroVeterinaria.DTO;
import java.util.List;

public class UserResponse {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private String createdAt; // formato dd-MM-yyyy HH:mm
    private List<AddressResponse> addresses;
    
    public UserResponse() {
        super();
    }

	public UserResponse(String id, String email, String name, String phone, String taxId, String createdAt,
			List<AddressResponse> addresses) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.taxId = taxId;
		this.createdAt = createdAt;
		this.addresses = addresses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public List<AddressResponse> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressResponse> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", email=" + email + ", name=" + name + ", phone=" + phone + ", taxId="
				+ taxId + ", createdAt=" + createdAt + "]";
	}
    
}