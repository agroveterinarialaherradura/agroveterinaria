package com.AgroVeterinaria.DTO;

public class WhatsAppRequest {
    private String phoneNumber;
    private String message;

    public WhatsAppRequest() {}
    public WhatsAppRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WhatsAppRequest{phoneNumber='" + phoneNumber + "', message='" + message + "'}";
    }
}