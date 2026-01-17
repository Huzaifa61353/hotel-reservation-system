package com.hotel.valueobjects;

public record Address(String street, String city, String zipCode, String country) {
    public Address {
        validateField(street, "Street");
        validateField(city, "City");
        validateField(zipCode, "Zip code");
        validateField(country, "Country");
    }
    
    private void validateField(String field, String fieldName) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
}
