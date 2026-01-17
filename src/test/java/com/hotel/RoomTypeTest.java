package com.hotel;

import com.hotel.domain.RoomType;
import com.hotel.domain.valueobjects.RoomKind;
import com.hotel.domain.valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class RoomTypeTest {
    
    @Test
    void createRoomType_ValidParameters_ShouldCreateRoomType() {
        // Arrange
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        
        // Act
        RoomType roomType = new RoomType(RoomKind.SINGLE, cost);
        
        // Assert
        assertNotNull(roomType);
        assertEquals(RoomKind.SINGLE, roomType.getKind());
        assertEquals(cost, roomType.getCost());
        assertNotNull(roomType.getRoomTypeId());
        assertTrue(roomType.getRoomTypeId().startsWith("ROOM_SINGLE_"));
    }
    
    @ParameterizedTest
    @NullSource
    void createRoomType_NullRoomKind_ShouldThrowException(RoomKind nullKind) {
        // Arrange
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RoomType(nullKind, cost)
        );
        assertEquals("Room kind cannot be null", exception.getMessage());
    }
    
    @ParameterizedTest
    @NullSource
    void createRoomType_NullCost_ShouldThrowException(Money nullCost) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RoomType(RoomKind.SINGLE, nullCost)
        );
        assertEquals("Cost cannot be null", exception.getMessage());
    }
    
    @Test
    void equals_SameRoomType_ShouldReturnTrue() {
        // Arrange
        Money cost1 = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        Money cost2 = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        RoomType roomType1 = new RoomType(RoomKind.SINGLE, cost1);
        RoomType roomType2 = new RoomType(RoomKind.SINGLE, cost2);
        
        // Act & Assert
        assertEquals(roomType1, roomType2);
    }
    
    @Test
    void equals_DifferentRoomKind_ShouldReturnFalse() {
        // Arrange
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        RoomType single = new RoomType(RoomKind.SINGLE, cost);
        RoomType doubleRoom = new RoomType(RoomKind.DOUBLE, cost);
        
        // Act & Assert
        assertNotEquals(single, doubleRoom);
    }
    
    @Test
    void equals_DifferentCost_ShouldReturnFalse() {
        // Arrange
        Money cost1 = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        Money cost2 = new Money(BigDecimal.valueOf(150), Currency.getInstance("USD"));
        RoomType roomType1 = new RoomType(RoomKind.SINGLE, cost1);
        RoomType roomType2 = new RoomType(RoomKind.SINGLE, cost2);
        
        // Act & Assert
        assertNotEquals(roomType1, roomType2);
    }
}