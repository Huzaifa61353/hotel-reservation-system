package com.hotel;

import com.hotel.domain.*;
import com.hotel.valueobjects.*;

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
        
        // 5. Create Guest
        Name guestName = new Name("John", "Doe");
        Address guestAddress = new Address("123 Main St", "New York", "10001", "USA");
        Guest guest = new Guest(guestName, guestAddress);
        System.out.println("\n5. Created Guest: " + guest.getName().firstName() + " " + 
                          guest.getName().lastName());
        
        // 6. Create HowMany (UML required)
        HowMany howMany = new HowMany(1);
        System.out.println("\n6. Created HowMany: " + howMany.getNumber() + " room(s)");
        
        // 7. Create ReservePayer
        CreditCardId creditCard = new CreditCardId("1234567812345678", "12/25", "123");
        ReservePayer payer = hotelChain.createReservePayer(creditCard);
        System.out.println("\n7. Created ReservePayer with Credit Card: **** **** **** " + 
                          creditCard.cardNumber().substring(12));
        
        // 8. Make Reservation
        BookingDate tomorrow = new BookingDate(LocalDate.now().plusDays(1));
        BookingDate dayAfterTomorrow = new BookingDate(LocalDate.now().plusDays(2));
        
        System.out.println("\n8. Making Reservation:");
        Reservation reservation = hotelChain.makeReservation(singleRoomType, tomorrow, 
                                                            dayAfterTomorrow, howMany);
        reservation.assignGuest(guest);
        System.out.println("   - Reservation #: " + reservation.getNumber());
        System.out.println("   - Room: " + reservation.getRoom().getRoomNumber());
        System.out.println("   - Type: " + reservation.getRoomType().getKind());
        System.out.println("   - Dates: " + reservation.getStartDate().localDate() + " to " + 
                          reservation.getEndDate().localDate());
        
        // 9. Check-in
        System.out.println("\n9. Checking in Guest:");
        boolean checkInSuccess = hotelChain.checkInGuest(reservation.getNumber());
        System.out.println("   - Check-in successful: " + checkInSuccess);
        System.out.println("   - Room occupied: " + reservation.getRoom().isOccupied());
        
        // 10. Demonstrate UML Associations
        System.out.println("\n=== UML ASSOCIATIONS DEMONSTRATED ===");
        System.out.println("✓ HotelChain 1 ─── 0..1 Hotel: " + 
                          (hotelChain.getHotel() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Hotel 1 ─── * Room: " + hotel.getRooms().size() + " rooms");
        System.out.println("✓ Room 0..1 ─── Guest: " + 
                          (room101.getOccupiedBy() != null ? "Occupied" : "Vacant"));
        System.out.println("✓ Reservation ─── Room: " + 
                          (reservation.getRoom() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Reservation ─── RoomType: " + 
                          (reservation.getRoomType() != null ? "Linked" : "Not linked"));
        System.out.println("✓ Reservation ─── HowMany: " + 
                          (reservation.getHowMany() != null ? "Linked" : "Not linked"));
        
        System.out.println("\n=== System Complete (UML Compliant) ===");
    }
}
