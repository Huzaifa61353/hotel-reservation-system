package com.hotel;

import com.hotel.domain.Guest;
import com.hotel.domain.valueobjects.Name;
import com.hotel.domain.valueobjects.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class GuestTest {
    
    @Test
    void createGuest_ValidParameters_ShouldCreateGuest() {
        // Arrange
        Name name = new Name("John", "Doe");
        Address address = new Address("123 Main St", "City", "12345", "Country");
        
        // Act
        Guest guest = new Guest(name, address);
        
        // Assert
        assertNotNull(guest);
        assertEquals(name, guest.getName());
        assertEquals(address, guest.getAddressDetails());
        assertNotNull(guest.getGuestId());
        assertTrue(guest.getGuestId().startsWith("GUEST_"));
    }
    
    @ParameterizedTest
    @NullSource
    void createGuest_NullName_ShouldThrowException(Name nullName) {
        // Arrange
        Address address = new Address("123 Main St", "City", "12345", "Country");
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Guest(nullName, address)
        );
        assertEquals("Name cannot be null", exception.getMessage());
    }
    
    @ParameterizedTest
    @NullSource
    void createGuest_NullAddress_ShouldThrowException(Address nullAddress) {
        // Arrange
        Name name = new Name("John", "Doe");
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Guest(name, nullAddress)
        );
        assertEquals("Address cannot be null", exception.getMessage());
    }
    
    @Test
    void getGuestId_ShouldReturnUniqueId() {
        // Arrange
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("Jane", "Smith");
        Address address = new Address("123 Main St", "City", "12345", "Country");
        
        Guest guest1 = new Guest(name1, address);
        Guest guest2 = new Guest(name2, address);
        
        // Act & Assert
        assertNotEquals(guest1.getGuestId(), guest2.getGuestId());
    }
}