package com.hotel.domain;

import com.hotel.domain.valueobjects.Name;
import com.hotel.domain.valueobjects.Address;

public class Guest {
    private final Name name;
    private final Address addressDetails;
    private final String guestId;
    
    public Guest(Name name, Address addressDetails) {
        validateParameters(name, addressDetails);
        this.name = name;
        this.addressDetails = addressDetails;
        this.guestId = generateGuestId();
    }
    
    private void validateParameters(Name name, Address addressDetails) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (addressDetails == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
    }
    
    private String generateGuestId() {
        return "GUEST_" + System.currentTimeMillis() + "_" + 
               name.firstName().hashCode();
    }
    
    // Getters
    public Name getName() { return name; }
    public Address getAddressDetails() { return addressDetails; }
    public String getGuestId() { return guestId; }
}