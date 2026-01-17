package com.hotel;

import com.hotel.domain.*;
import com.hotel.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {
    private Hotel hotel;
    private RoomType roomType;
    
    @BeforeEach
    void setUp() {
        hotel = new Hotel("Test Hotel");
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        roomType = new RoomType(RoomKind.SINGLE, cost);
    }
    
    @Test
    void createHotel_ValidName_ShouldCreateHotel() {
        // Act
        Hotel hotel = new Hotel("Valid Hotel Name");
        
        // Assert
        assertNotNull(hotel);
        assertEquals("Valid Hotel Name", hotel.getName());
        assertTrue(hotel.getRooms().isEmpty());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    void createHotel_InvalidName_ShouldThrowException(String invalidName) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Hotel(invalidName)
        );
        assertTrue(exception.getMessage().contains("Hotel name cannot be null or empty"));
    }
    
    @Test
    void addRoom_ValidRoom_ShouldAddRoom() {
        // Arrange
        Room room = new Room("101", roomType);
        
        // Act
        hotel.addRoom(room);
        
        // Assert
        assertEquals(1, hotel.getRooms().size());
        assertEquals(room, hotel.getRooms().get(0));
    }
    
    @Test
    void addRoom_NullRoom_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> hotel.addRoom(null)
        );
        assertEquals("Room cannot be null", exception.getMessage());
    }
    
    @Test
    void getAvailableRooms_WithVacantRooms_ShouldReturnAllRooms() {
        // Arrange
        Room room1 = new Room("101", roomType);
        Room room2 = new Room("102", roomType);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        
        // Act
        var availableRooms = hotel.getAvailableRooms();
        
        // Assert
        assertEquals(2, availableRooms.size());
    }
    
    @Test
    void getAvailableRooms_WithOccupiedRoom_ShouldReturnOnlyVacant() {
        // Arrange
        Room room1 = new Room("101", roomType);
        Room room2 = new Room("102", roomType);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        
        // Occupy room1
        Guest guest = new Guest(new Name("John", "Doe"), 
                               new Address("123 St", "City", "12345", "Country"));
        room1.occupy(guest);
        
        // Act
        var availableRooms = hotel.getAvailableRooms();
        
        // Assert
        assertEquals(1, availableRooms.size());
        assertEquals(room2, availableRooms.get(0));
    }
    
    @Test
    void findRoomByNumber_ExistingRoom_ShouldReturnRoom() {
        // Arrange
        Room room = new Room("101", roomType);
        hotel.addRoom(room);
        
        // Act
        Room found = hotel.findRoomByNumber("101");
        
        // Assert
        assertNotNull(found);
        assertEquals("101", found.getRoomNumber());
    }
    
    @Test
    void findRoomByNumber_NonExistentRoom_ShouldReturnNull() {
        // Act
        Room found = hotel.findRoomByNumber("999");
        
        // Assert
        assertNull(found);
    }
}