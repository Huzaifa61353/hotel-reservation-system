package com.hotel;

import com.hotel.domain.*;
import com.hotel.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HotelChainTest {
    private HotelChain hotelChain;
    private Hotel hotel;
    private RoomType roomType;
    private HowMany howMany;
    private BookingDate startDate;
    private BookingDate endDate;
    private Guest guest;
    
    @BeforeEach
    void setUp() {
        Name chainName = new Name("Test", "Chain");
        hotelChain = new HotelChain(chainName);
        
        hotel = new Hotel("Test Hotel");
        hotelChain.setHotel(hotel);
        
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        roomType = new RoomType(RoomKind.SINGLE, cost);
        
        Room room = new Room("101", roomType);
        hotel.addRoom(room);
        
        howMany = new HowMany(1);
        startDate = new BookingDate(LocalDate.now().plusDays(1));
        endDate = new BookingDate(LocalDate.now().plusDays(3));
        
        Name guestName = new Name("John", "Doe");
        Address address = new Address("123 St", "City", "12345", "Country");
        guest = new Guest(guestName, address);
    }
    
    @Test
    void makeReservation_ValidParameters_ShouldCreateReservation() {
        // Arrange
        // Setup done in @BeforeEach
        
        // Act
        Reservation reservation = hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        
        // Assert
        assertNotNull(reservation);
        assertEquals(roomType, reservation.getRoomType());
        assertNotNull(reservation.getRoom());
        assertEquals(howMany, reservation.getHowMany());
        assertEquals(1, hotelChain.getReservations().size());
    }
    
    @Test
    void makeReservation_NoHotel_ShouldThrowException() {
        // Arrange
        hotelChain.setHotel(null);
        
        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> hotelChain.makeReservation(roomType, startDate, endDate, howMany)
        );
        assertTrue(exception.getMessage().contains("Cannot make reservation"));
    }
    
    @Test
    void makeReservation_DuplicateReservation_ShouldThrowException() {
        // Arrange
        hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        
        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> hotelChain.makeReservation(roomType, startDate, endDate, howMany)
        );
        assertTrue(exception.getMessage().contains("Cannot make reservation"));
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidReservationParameters")
    void makeReservation_InvalidParameters_ShouldThrowException(
            RoomType invalidRoomType, BookingDate invalidStartDate, 
            BookingDate invalidEndDate, HowMany invalidHowMany, String expectedMessage) {
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> hotelChain.makeReservation(invalidRoomType, invalidStartDate, 
                                           invalidEndDate, invalidHowMany)
        );
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    
    private static Stream<Object[]> provideInvalidReservationParameters() {
        Money cost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        RoomType validRoomType = new RoomType(RoomKind.SINGLE, cost);
        
        BookingDate validStartDate = new BookingDate(LocalDate.now().plusDays(1));
        BookingDate validEndDate = new BookingDate(LocalDate.now().plusDays(3));
        HowMany validHowMany = new HowMany(1);
        
        return Stream.of(
            new Object[]{null, validStartDate, validEndDate, validHowMany, "Room type cannot be null"},
            new Object[]{validRoomType, null, validEndDate, validHowMany, "Start date cannot be null"},
            new Object[]{validRoomType, validStartDate, null, validHowMany, "End date cannot be null"},
            new Object[]{validRoomType, validStartDate, validEndDate, null, "HowMany cannot be null"}
        );
    }
    
    @Test
    void cancelReservation_BeforeCheckIn_ShouldReturnTrue() {
        // Arrange
        Reservation reservation = hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        
        // Act
        boolean result = hotelChain.cancelReservation(reservation.getNumber());
        
        // Assert
        assertTrue(result);
        assertEquals(0, hotelChain.getReservations().size());
    }
    
    @Test
    void cancelReservation_NonExistentReservation_ShouldReturnFalse() {
        // Act
        boolean result = hotelChain.cancelReservation("NON_EXISTENT");
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    void checkInGuest_ValidReservation_ShouldOccupyRoom() {
        // Arrange
        Reservation reservation = hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        reservation.assignGuest(guest);
        
        // Act
        boolean result = hotelChain.checkInGuest(reservation.getNumber());
        
        // Assert
        assertTrue(result);
        assertTrue(reservation.getRoom().isOccupied());
        assertEquals(guest, reservation.getRoom().getOccupiedBy());
    }
    
    @Test
    void checkInGuest_ReservationWithoutGuest_ShouldReturnFalse() {
        // Arrange
        Reservation reservation = hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        // No guest assigned
        
        // Act
        boolean result = hotelChain.checkInGuest(reservation.getNumber());
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    void createReservePayer_ValidCreditCard_ShouldCreatePayer() {
        // Arrange
        CreditCardId creditCard = new CreditCardId("1234567812345678", "12/25", "123");
        
        // Act
        ReservePayer payer = hotelChain.createReservePayer(creditCard);
        
        // Assert
        assertNotNull(payer);
        assertEquals(creditCard, payer.getCreditCardDetails());
        assertEquals(payer, hotelChain.getReservePayer());
    }
    
    @ParameterizedTest
    @NullSource
    void createReservePayer_NullCreditCard_ShouldThrowException(CreditCardId nullCard) {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> hotelChain.createReservePayer(nullCard)
        );
        assertEquals("Credit card details cannot be null", exception.getMessage());
    }
    
    @Test
    void canMakeReservation_PrivateMethod_TestedViaPublicInterface() {
        // First reservation should succeed
        Reservation r1 = hotelChain.makeReservation(roomType, startDate, endDate, howMany);
        assertNotNull(r1);
        
        // Second reservation should fail (no available rooms)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> hotelChain.makeReservation(roomType, startDate, endDate, howMany)
        );
        assertTrue(exception.getMessage().contains("Cannot make reservation"));
    }
}