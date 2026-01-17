# Hotel Reservation System - CCP Project

## Project Overview
This is a complete implementation of a hotel reservation system based on the provided UML diagram. The system demonstrates strict UML compliance, object-oriented design principles, clean code practices, defensive programming, and comprehensive unit testing.

## UML Compliance
The implementation strictly follows the provided UML diagram with:
- **8 Domain Classes**: HotelChain, Hotel, Room, RoomType, Guest, Reservation, HowMany, ReservePayer
- **6 Value Objects**: Name, Address, CreditCardId, Money, RoomKind, BookingDate
- **Correct Associations**: All UML associations (1..1, 1..*, 0..1) are correctly implemented
- **Exact Method Signatures**: All methods match the UML diagram exactly

## Features
- Hotel chain management with multiple hotels
- Room inventory management with different room types
- Guest registration and management
- Reservation system with date validation
- Check-in/check-out functionality
- Payment system integration
- Comprehensive input validation and error handling

## Prerequisites
- Java 17 or higher
- Maven 3.6+ or Gradle 7+
- JUnit 5

## Build and Run

### Using Maven:
```bash
# Clone the repository
git clone <repository-url>
cd hotel-reservation-system

# Build the project
mvn clean compile

# Run tests
mvn test

# Run the main application
mvn exec:java -Dexec.mainClass="com.hotel.Main"