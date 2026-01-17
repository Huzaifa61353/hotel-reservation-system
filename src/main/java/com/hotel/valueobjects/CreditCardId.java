package com.hotel.valueobjects;

public record CreditCardId(String cardNumber, String expiryDate, String cvv) {
    public CreditCardId {
        validateCardNumber(cardNumber);
        validateExpiryDate(expiryDate);
        validateCVV(cvv);
    }
    
    private void validateCardNumber(String cardNumber) {
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number. Must be 16 digits");
        }
    }
    
    private void validateExpiryDate(String expiryDate) {
        if (expiryDate == null || !expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            throw new IllegalArgumentException("Invalid expiry date format. Use MM/YY");
        }
    }
    
    private void validateCVV(String cvv) {
        if (cvv == null || !cvv.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("Invalid CVV. Must be 3 or 4 digits");
        }
    }
}
