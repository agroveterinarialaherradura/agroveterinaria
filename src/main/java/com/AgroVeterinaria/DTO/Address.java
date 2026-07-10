package com.AgroVeterinaria.DTO;
import java.util.UUID;

public class Address {
    private UUID userId;
    private Integer addressId;
    private String name;
    private String street;
    private String countryCode;

    public Address() {
        super();
    }

	public Address(UUID userId, Integer addressId, String name, String street, String countryCode) {
		super();
		this.userId = userId;
		this.addressId = addressId;
		this.name = name;
		this.street = street;
		this.countryCode = countryCode;
	}

	public UUID getUserId() {return userId;}
	public void setUserId(UUID userId) {this.userId = userId;}

	public Integer getAddressId() {return addressId;}
	public void setAddressId(Integer addressId) {this.addressId = addressId;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getStreet() {return street;}
	public void setStreet(String street) {this.street = street;}

	public String getCountryCode() {return countryCode;}
	public void setCountryCode(String countryCode) {this.countryCode = countryCode;}

	@Override
	public String toString() {
		return "Address [userId=" + userId + ", addressId=" + addressId + ", name=" + name + ", street=" + street
				+ ", countryCode=" + countryCode + "]";
	}
}