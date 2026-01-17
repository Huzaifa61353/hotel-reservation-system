package com.hotel;

import com.hotel.domain.*;
import com.hotel.domain.valueobjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hotel Reservation System (UML Compliant) ===\n");
        
        // 1. Create Hotel Chain
        Name hotelChainName = new Name("Luxury", "Hotels International");
        HotelChain hotelChain = new HotelChain(hotelChainName);
        System.out.println("1. Created HotelChain: " + hotelChainName.firstName() + " " + 
                          hotelChainName.lastName());
        
        // 2. Create Hotel
        Hotel hotel = new Hotel("Grand Luxury Hotel");
        hotelChain.setHotel(hotel);
        System.out.println("2. Created Hotel: " + hotel.getName());
        
        // 3. Create Room Types
        Money singleCost = new Money(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        Money doubleCost = new Money(BigDecimal.valueOf(150), Currency.getInstance("USD"));
        
        RoomType singleRoomType = new RoomType(RoomKind.SINGLE, singleCost);
        RoomType doubleRoomType = new RoomType(RoomKind.DOUBLE, doubleCost);
        
        System.out.println("\n3. Created RoomTypes:");
        System.out.println("   - " + singleRoomType.getKind() + ": $" + singleRoomType.getCost().amount());
        System.out.println("   - " + doubleRoomType.getKind() + ": $" + doubleRoomType.getCost().amount());
        
        // 4. Create Rooms
        Room room101 = new Room("101", singleRoomType);
        Room room102 = new Room("102", singleRoomType);
        Room room201 = new Room("201", doubleRoomType);
        
        hotel.addRoom(room101);
        hotel.addRoom(room102);
        hotel.addRoom(room201);
        
        System.out.println("\n4. Created Rooms:");
        System.out.println("   - Room " + room101.getRoomNumber() + " (" + room101.getRoomType().getKind() + ")");
        System.out.println("   - Room " + room102.getRoomNumber() + " (" + room102.getRoomType().getKind() + ")");
        System.out.println("   - Room " + room201.getRoomNumber() + " (" + room201.getRoomType().getKind() + ")");
        
        // 5. Create Guests
        Name guest1Name = new Name("John", "Doe");
        Address guest1Address = new Address("123 Main St", "New York", "10001", "USA");
        Guest guest1 = new Guest(guest1Name, guest1Address);
        
        Name guest2Name = new Name("Jane", "Smith");
        Address guest2Address = new Address("456 Oak Ave", "Los Angeles", "90001", "USA");
        Guest guest2 = new Guest(guest2Name, guest2Address);
        
        System.out.println("\n5. Created Guests:");
        System.out.println("   - " + guest1.getName().firstName() + " " + guest1.getName().lastName());
        System.out.println("   - " + guest2.getName().firstName() + " " + guest2.getName().lastName());
        
        // 6. Create HowMany
        HowMany howMany = new HowMany(1);
        System.out.println("\n6. Created HowMany: " + howMany.getNumber() + " room(s)");
        
        // 7. Create ReservePayer
        CreditCardId creditCard = new CreditCardId("1234567812345678", "12/25", "123");
        ReservePayer payer = hotelChain.createReservePayer(creditCard);
        System.out.println("\n7. Created ReservePayer with Credit Card: **** **** **** " + 
                          creditCard.cardNumber().substring(12));
        
        // 8. Make Reservation for John Doe (TODAY for immediate check-in)
        BookingDate today = new BookingDate(LocalDate.now());
        BookingDate tomorrow = new BookingDate(LocalDate.now().plusDays(1));
        
        System.out.println("\n8. Making Reservation for John Doe (UML: makeReservation()):");
        Reservation reservation1 = hotelChain.makeReservation(singleRoomType, today, tomorrow, howMany);
        reservation1.assignGuest(guest1);
        System.out.println("   - Reservation #: " + reservation1.getNumber());
        System.out.println("   - Room: " + reservation1.getRoom().getRoomNumber());
        System.out.println("   - Type: " + reservation1.getRoomType().getKind());
        System.out.println("   - Dates: " + reservation1.getStartDate().localDate() + " to " + 
                          reservation1.getEndDate().localDate());
        System.out.println("   - Guest: " + reservation1.getGuest().getName().firstName());
        
        // 9. Make another reservation for Jane Smith (next week - won't check in)
        System.out.println("\n9. Making Reservation for Jane Smith:");
        BookingDate nextWeek = new BookingDate(LocalDate.now().plusDays(7));
        BookingDate nextWeekPlus2 = new BookingDate(LocalDate.now().plusDays(9));
        
        Reservation reservation2 = hotelChain.makeReservation(doubleRoomType, nextWeek, nextWeekPlus2, howMany);
        reservation2.assignGuest(guest2);
        System.out.println("   - Reservation #: " + reservation2.getNumber());
        System.out.println("   - Room: " + reservation2.getRoom().getRoomNumber());
        System.out.println("   - Type: " + reservation2.getRoomType().getKind());
        
        // 10. Check-in Guest (UML: checkInGuest()) - SHOULD SUCCEED because start date is today
        System.out.println("\n10. Checking in John Doe (UML: checkInGuest()):");
        System.out.println("   - Today's date: " + today.localDate());
        System.out.println("   - Reservation start date: " + reservation1.getStartDate().localDate());
        
        boolean checkInSuccess = hotelChain.checkInGuest(reservation1.getNumber());
        System.out.println("   - Check-in successful: " + checkInSuccess);
        
        if (checkInSuccess) {
            System.out.println("   - Room " + reservation1.getRoom().getRoomNumber() + 
                             " occupied: " + reservation1.getRoom().isOccupied());
            if (reservation1.getRoom().getOccupiedBy() != null) {
                System.out.println("   - Occupied by: " + 
                                 reservation1.getRoom().getOccupiedBy().getName().firstName());
            }
        } else {
            System.out.println("   - Check-in failed (might be because: not check-in day, no guest assigned, or room already occupied)");
        }
        
        // 11. Demonstrate failed check-in (Jane's reservation - future date)
        System.out.println("\n11. Attempting to check in Jane (future reservation - should fail):");
        boolean checkInFuture = hotelChain.checkInGuest(reservation2.getNumber());
        System.out.println("   - Check-in successful: " + checkInFuture);
        
        // 12. Demonstrate cancellation
        System.out.println("\n12. Cancelling Jane's reservation (before check-in):");
        boolean cancelSuccess = hotelChain.cancelReservation(reservation2.getNumber());
        System.out.println("   - Cancellation successful: " + cancelSuccess);
        System.out.println("   - Remaining reservations: " + hotelChain.getReservations().size());
        
        // 13. Demonstrate All UML Associations
        System.out.println("\n=== UML ASSOCIATIONS DEMONSTRATED ===");
        System.out.println("✓ HotelChain 1 ─── 0..1 Hotel: " + 
                          (hotelChain.getHotel() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Hotel 1 ─── * Room: " + hotel.getRooms().size() + " rooms");
        System.out.println("✓ Room 0..1 ─── Guest: " + 
                          (reservation1.getRoom().getOccupiedBy() != null ? "Occupied" : "Vacant"));
        System.out.println("✓ Reservation ─── Room: " + 
                          (reservation1.getRoom() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Reservation ─── RoomType: " + 
                          (reservation1.getRoomType() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Reservation ─── HowMany: " + 
                          (reservation1.getHowMany() != null ? "Linked" : "Not linked"));
        
        System.out.println("\n=== System Complete (UML Compliant) ===");
    }
}
