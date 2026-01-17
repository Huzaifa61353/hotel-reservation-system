package com.hotel;

import com.hotel.domain.HowMany;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class HowManyTest {
    
    @Test
    void createHowMany_ValidNumber_ShouldCreateHowMany() {
        // Act
        HowMany howMany = new HowMany(1);
        
        // Assert
        assertNotNull(howMany);
        assertEquals(1, howMany.getNumber());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void createHowMany_InvalidNumber_ShouldThrowException(int invalidNumber) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new HowMany(invalidNumber)
        );
        assertEquals("Number must be a positive integer", exception.getMessage());
    }
    
    @Test
    void createHowMany_NullNumber_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new HowMany(null)
        );
        assertEquals("Number must be a positive integer", exception.getMessage());
    }
    
    @Test
    void setNumber_ValidNumber_ShouldUpdateNumber() {
        // Arrange
        HowMany howMany = new HowMany(1);
        
        // Act
        howMany.setNumber(2);
        
        // Assert
        assertEquals(2, howMany.getNumber());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void setNumber_InvalidNumber_ShouldThrowException(int invalidNumber) {
        // Arrange
        HowMany howMany = new HowMany(1);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> howMany.setNumber(invalidNumber)
        );
        assertEquals("Number must be a positive integer", exception.getMessage());
    }
}