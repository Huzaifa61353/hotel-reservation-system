package com.hotel.domain;

public class HowMany {
    private Integer number;
    
    public HowMany(Integer number) {
        setNumber(number);
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        if (number == null || number <= 0) {
            throw new IllegalArgumentException("Number must be a positive integer");
        }
        this.number = number;
    }
}