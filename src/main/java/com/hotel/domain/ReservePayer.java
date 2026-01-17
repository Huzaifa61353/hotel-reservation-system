package com.hotel.domain;

import com.hotel.domain.valueobjects.CreditCardId;

public class ReservePayer {
    private final CreditCardId creditCardDetails;
    private final String payerId;
    
    public ReservePayer(CreditCardId creditCardDetails) {
        validateCreditCardDetails(creditCardDetails);
        this.creditCardDetails = creditCardDetails;
        this.payerId = generatePayerId();
    }
    
    private void validateCreditCardDetails(CreditCardId creditCardDetails) {
        if (creditCardDetails == null) {
            throw new IllegalArgumentException("Credit card details cannot be null");
        }
    }
    
    private String generatePayerId() {
        return "PAYER_" + System.currentTimeMillis() + "_" + 
               creditCardDetails.hashCode();
    }
    
    // Getters
    public CreditCardId getCreditCardDetails() { return creditCardDetails; }
    public String getPayerId() { return payerId; }
}