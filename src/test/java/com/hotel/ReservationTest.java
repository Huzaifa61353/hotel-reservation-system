package com.hotel;

import com.hotel.domain.*;
import com.hotel.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Room room;
    private RoomType roomType;
    private HowMany howMany;
    private BookingDate reservationDate;
    private BookingDate startDate;
    private BookingDate endDate;
    
    @BeforeEach
    void setUp() {
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        roomType = new RoomType(RoomKind.SINGLE, cost);
        room = new Room("101", roomType);
        howMany = new HowMany(1);
        reservationDate = new BookingDate(LocalDate.now());
        startDate = new BookingDate(LocalDate.now().plusDays(1));
        endDate = new BookingDate(LocalDate.now().plusDays(3));
    }
    
    @Test
    void createReservation_ValidParameters_ShouldCreateReservation() {
        // Act
        Reservation reservation = new Reservation(
            reservationDate, startDate, endDate, "RES123", room, roomType, howMany
        );
        
        // Assert
        assertNotNull(reservation);
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        assertEquals("RES123", reservation.getNumber());
        assertEquals(room, reservation.getRoom());
        assertEquals(roomType, reservation.getRoomType());
        assertEquals(howMany, reservation.getHowMany());
        assertNull(reservation.getGuest());
    }
    
    @Test
    void createReservation_InvalidDates_ShouldThrowException() {
        // Arrange - end date before start date
        BookingDate invalidEndDate = new BookingDate(LocalDate.now().minusDays(1));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Reservation(
                reservationDate, startDate, invalidEndDate, "RES123", room, roomType, howMany
            )
        );
        assertTrue(exception.getMessage().contains("Start date must be before end date"));
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    void createReservation_InvalidNumber_ShouldThrowException(String invalidNumber) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Reservation(
                reservationDate, startDate, endDate, invalidNumber, room, roomType, howMany
            )
        );
        assertTrue(exception.getMessage().contains("Reservation number cannot be null or empty"));
    }
    
    @Test
    void assignGuest_ValidGuest_ShouldAssignGuest() {
        // Arrange
        Reservation reservation = new Reservation(
            reservationDate, startDate, endDate, "RES123", room, roomType, howMany
        );
        Guest guest = new Guest(new Name("John", "Doe"), 
                               new Address("123 St", "City", "12345", "Country"));
        
        // Act
        reservation.assignGuest(guest);
        
        // Assert
        assertEquals(guest, reservation.getGuest());
    }
    
    @Test
    void assignGuest_NullGuest_ShouldThrowException() {
        // Arrange
        Reservation reservation = new Reservation(
            reservationDate, startDate, endDate, "RES123", room, roomType, howMany
        );
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> reservation.assignGuest(null)
        );
        assertEquals("Guest cannot be null", exception.getMessage());
    }
    
    @Test
    void reservation_IsStateHolder_NoBusinessLogic() {
        // Arrange
        Reservation reservation = new Reservation(
            reservationDate, startDate, endDate, "RES123", room, roomType, howMany
        );
        
        // Act - Try to find business methods (should not exist)
        Class<?> clazz = reservation.getClass();
        
        // Assert - Only getters and assignGuest should exist
        assertEquals(8, clazz.getDeclaredMethods().length);
    }
}