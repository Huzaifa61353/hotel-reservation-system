package com.hotel.domain.valueobjects;

import java.time.LocalDate;

public record BookingDate(LocalDate localDate) {
    public BookingDate {
        if (localDate == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }
    
    public boolean isBefore(BookingDate other) {
        return this.localDate.isBefore(other.localDate);
    }
    
    public boolean isAfter(BookingDate other) {
        return this.localDate.isAfter(other.localDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BookingDate other)) return false;
        return this.localDate.equals(other.localDate);
    }
    
    @Override
    public int hashCode() {
        return localDate.hashCode();
    }
}