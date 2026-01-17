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

class RoomTest {
    private RoomType roomType;
    
    @BeforeEach
    void setUp() {
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        roomType = new RoomType(RoomKind.SINGLE, cost);
    }
    
    @Test
    void createRoom_ValidParameters_ShouldCreateRoom() {
        // Act
        Room room = new Room("101", roomType);
        
        // Assert
        assertNotNull(room);
        assertEquals("101", room.getRoomNumber());
        assertEquals(roomType, room.getRoomType());
        assertFalse(room.isOccupied());
        assertNull(room.getOccupiedBy());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    void createRoom_InvalidRoomNumber_ShouldThrowException(String invalidRoomNumber) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Room(invalidRoomNumber, roomType)
        );
        assertTrue(exception.getMessage().contains("Room number cannot be null or empty"));
    }
    
    @Test
    void createRoom_NullRoomType_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Room("101", null)
        );
        assertEquals("Room type cannot be null", exception.getMessage());
    }
    
    @Test
    void occupy_ValidGuest_ShouldOccupyRoom() {
        // Arrange
        Room room = new Room("101", roomType);
        Guest guest = new Guest(new Name("John", "Doe"), 
                               new Address("123 St", "City", "12345", "Country"));
        
        // Act
        room.occupy(guest);
        
        // Assert
        assertTrue(room.isOccupied());
        assertEquals(guest, room.getOccupiedBy());
    }
    
    @Test
    void occupy_AlreadyOccupiedRoom_ShouldThrowException() {
        // Arrange
        Room room = new Room("101", roomType);
        Guest guest1 = new Guest(new Name("John", "Doe"), 
                                new Address("123 St", "City", "12345", "Country"));
        Guest guest2 = new Guest(new Name("Jane", "Smith"), 
                                new Address("456 St", "City", "12345", "Country"));
        room.occupy(guest1);
        
        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> room.occupy(guest2)
        );
        assertEquals("Room is already occupied", exception.getMessage());
    }
    
    @Test
    void occupy_NullGuest_ShouldThrowException() {
        // Arrange
        Room room = new Room("101", roomType);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> room.occupy(null)
        );
        assertEquals("Guest cannot be null", exception.getMessage());
    }
    
    @Test
    void vacate_OccupiedRoom_ShouldVacateRoom() {
        // Arrange
        Room room = new Room("101", roomType);
        Guest guest = new Guest(new Name("John", "Doe"), 
                               new Address("123 St", "City", "12345", "Country"));
        room.occupy(guest);
        
        // Act
        room.vacate();
        
        // Assert
        assertFalse(room.isOccupied());
        assertNull(room.getOccupiedBy());
    }
    
    @Test
    void vacate_VacantRoom_ShouldRemainVacant() {
        // Arrange
        Room room = new Room("101", roomType);
        
        // Act
        room.vacate();
        
        // Assert
        assertFalse(room.isOccupied());
        assertNull(room.getOccupiedBy());
    }
}