package com.hotel;

import com.hotel.domain.ReservePayer;
import com.hotel.domain.valueobjects.CreditCardId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class ReservePayerTest {
    
    @Test
    void createReservePayer_ValidCreditCard_ShouldCreatePayer() {
        // Arrange
        CreditCardId creditCard = new CreditCardId("1234567812345678", "12/25", "123");
        
        // Act
        ReservePayer payer = new ReservePayer(creditCard);
        
        // Assert
        assertNotNull(payer);
        assertEquals(creditCard, payer.getCreditCardDetails());
        assertNotNull(payer.getPayerId());
        assertTrue(payer.getPayerId().startsWith("PAYER_"));
    }
    
    @ParameterizedTest
    @NullSource
    void createReservePayer_NullCreditCard_ShouldThrowException(CreditCardId nullCard) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new ReservePayer(nullCard)
        );
        assertEquals("Credit card details cannot be null", exception.getMessage());
    }
    
    @Test
    void getPayerId_ShouldReturnUniqueId() {
        // Arrange
        CreditCardId creditCard1 = new CreditCardId("1234567812345678", "12/25", "123");
        CreditCardId creditCard2 = new CreditCardId("8765432187654321", "12/26", "456");
        
        ReservePayer payer1 = new ReservePayer(creditCard1);
        ReservePayer payer2 = new ReservePayer(creditCard2);
        
        // Act & Assert
        assertNotEquals(payer1.getPayerId(), payer2.getPayerId());
    }
}