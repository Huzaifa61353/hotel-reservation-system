package com.hotel.domain;

import com.hotel.valueobjects.Name;
import com.hotel.valueobjects.BookingDate;
import com.hotel.valueobjects.CreditCardId;

import java.util.ArrayList;
import java.util.List;

public class HotelChain {
    private final Name name;
    private Hotel hotel;
    private final List<Reservation> reservations;
    private ReservePayer reservePayer;
    
    public HotelChain(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Hotel chain name cannot be null");
        }
        this.name = name;
        this.reservations = new ArrayList<>();
    }
    
    // UML Methods
    public Reservation makeReservation(RoomType roomType, BookingDate startDate, 
                                      BookingDate endDate, HowMany howMany) {
        validateReservationParameters(roomType, startDate, endDate, howMany);
        
        if (!canMakeReservation(roomType, startDate, endDate)) {
            throw new IllegalStateException("Cannot make reservation - conflicts detected");
        }
        
        Room availableRoom = findAvailableRoom(roomType);
        if (availableRoom == null) {
            throw new IllegalStateException("No available room of type: " + roomType.getKind());
        }
        
        Reservation reservation = new Reservation(
            new BookingDate(java.time.LocalDate.now()),
            startDate,
            endDate,
            generateReservationNumber(),
            availableRoom,
            roomType,
            howMany
        );
        
        reservations.add(reservation);
        return reservation;
    }
    
    public boolean cancelReservation(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCancelReservation(reservation)) {
            return false;
        }
        reservations.remove(reservation);
        return true;
    }
    
    public boolean checkInGuest(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCheckInGuest(reservation)) {
            return false;
        }
        reservation.getRoom().occupy(reservation.getGuest());
        return true;
    }
    
    public boolean checkOutGuest(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCheckOutGuest(reservation)) {
            return false;
        }
        reservation.getRoom().vacate();
        return true;
    }
    
    public ReservePayer createReservePayer(CreditCardId creditCardDetails) {
        if (creditCardDetails == null) {
            throw new IllegalArgumentException("Credit card details cannot be null");
        }
        this.reservePayer = new ReservePayer(creditCardDetails);
        return this.reservePayer;
    }
    
    // Private validation methods (UML)
    private boolean canMakeReservation(RoomType roomType, BookingDate startDate, BookingDate endDate) {
        if (hotel == null) return false;
        
        List<Room> availableRooms = hotel.getAvailableRooms();
        long availableOfType = availableRooms.stream()
            .filter(room -> room.getRoomType().equals(roomType))
            .count();
            
        long bookedOfType = reservations.stream()
            .filter(r -> r.getRoomType().equals(roomType))
            .filter(r -> datesOverlap(r.getStartDate(), r.getEndDate(), startDate, endDate))
            .count();
            
        return availableOfType > bookedOfType;
    }
    
    private boolean canCancelReservation(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        return today.isBefore(reservation.getStartDate());
    }
    
    private boolean canCheckInGuest(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        boolean isCheckInDay = today.equals(reservation.getStartDate()) || 
                              today.isAfter(reservation.getStartDate());
        return isCheckInDay && reservation.getGuest() != null && 
               !reservation.getRoom().isOccupied();
    }
    
    private boolean canCheckOutGuest(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        boolean isCheckOutDay = today.equals(reservation.getEndDate()) || 
                               today.isAfter(reservation.getEndDate());
        return isCheckOutDay && reservation.getRoom().isOccupied();
    }
    
    private boolean datesOverlap(BookingDate start1, BookingDate end1, 
                                BookingDate start2, BookingDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
    
    private Room findAvailableRoom(RoomType roomType) {
        if (hotel == null) return null;
        
        return hotel.getAvailableRooms().stream()
            .filter(room -> room.getRoomType().equals(roomType))
            .findFirst()
            .orElse(null);
    }
    
    private Reservation findReservation(String reservationNumber) {
        return reservations.stream()
            .filter(r -> r.getNumber().equals(reservationNumber))
            .findFirst()
            .orElse(null);
    }
    
    private String generateReservationNumber() {
        return "RES_" + System.currentTimeMillis() + "_" + name.hashCode();
    }
    
    private void validateReservationParameters(RoomType roomType, BookingDate startDate, 
                                              BookingDate endDate, HowMany howMany) {
        if (roomType == null) throw new IllegalArgumentException("Room type cannot be null");
        if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
        if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
        if (howMany == null) throw new IllegalArgumentException("HowMany cannot be null");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (howMany.getNumber() != 1) {
            throw new IllegalArgumentException("Currently only single room reservations supported");
        }
    }
    
    // Setters and getters
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    public Name getName() { return name; }
    public Hotel getHotel() { return hotel; }
    public List<Reservation> getReservations() { return new ArrayList<>(reservations); }
    public ReservePayer getReservePayer() { return reservePayer; }
}
EOFcat > src/main/java/com/hotel/domain/HotelChain.java << 'EOF'
package com.hotel.domain;

import com.hotel.valueobjects.Name;
import com.hotel.valueobjects.BookingDate;
import com.hotel.valueobjects.CreditCardId;

import java.util.ArrayList;
import java.util.List;

public class HotelChain {
    private final Name name;
    private Hotel hotel;
    private final List<Reservation> reservations;
    private ReservePayer reservePayer;
    
    public HotelChain(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Hotel chain name cannot be null");
        }
        this.name = name;
        this.reservations = new ArrayList<>();
    }
    
    // UML Methods
    public Reservation makeReservation(RoomType roomType, BookingDate startDate, 
                                      BookingDate endDate, HowMany howMany) {
        validateReservationParameters(roomType, startDate, endDate, howMany);
        
        if (!canMakeReservation(roomType, startDate, endDate)) {
            throw new IllegalStateException("Cannot make reservation - conflicts detected");
        }
        
        Room availableRoom = findAvailableRoom(roomType);
        if (availableRoom == null) {
            throw new IllegalStateException("No available room of type: " + roomType.getKind());
        }
        
        Reservation reservation = new Reservation(
            new BookingDate(java.time.LocalDate.now()),
            startDate,
            endDate,
            generateReservationNumber(),
            availableRoom,
            roomType,
            howMany
        );
        
        reservations.add(reservation);
        return reservation;
    }
    
    public boolean cancelReservation(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCancelReservation(reservation)) {
            return false;
        }
        reservations.remove(reservation);
        return true;
    }
    
    public boolean checkInGuest(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCheckInGuest(reservation)) {
            return false;
        }
        reservation.getRoom().occupy(reservation.getGuest());
        return true;
    }
    
    public boolean checkOutGuest(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation == null || !canCheckOutGuest(reservation)) {
            return false;
        }
        reservation.getRoom().vacate();
        return true;
    }
    
    public ReservePayer createReservePayer(CreditCardId creditCardDetails) {
        if (creditCardDetails == null) {
            throw new IllegalArgumentException("Credit card details cannot be null");
        }
        this.reservePayer = new ReservePayer(creditCardDetails);
        return this.reservePayer;
    }
    
    // Private validation methods (UML)
    private boolean canMakeReservation(RoomType roomType, BookingDate startDate, BookingDate endDate) {
        if (hotel == null) return false;
        
        List<Room> availableRooms = hotel.getAvailableRooms();
        long availableOfType = availableRooms.stream()
            .filter(room -> room.getRoomType().equals(roomType))
            .count();
            
        long bookedOfType = reservations.stream()
            .filter(r -> r.getRoomType().equals(roomType))
            .filter(r -> datesOverlap(r.getStartDate(), r.getEndDate(), startDate, endDate))
            .count();
            
        return availableOfType > bookedOfType;
    }
    
    private boolean canCancelReservation(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        return today.isBefore(reservation.getStartDate());
    }
    
    private boolean canCheckInGuest(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        boolean isCheckInDay = today.equals(reservation.getStartDate()) || 
                              today.isAfter(reservation.getStartDate());
        return isCheckInDay && reservation.getGuest() != null && 
               !reservation.getRoom().isOccupied();
    }
    
    private boolean canCheckOutGuest(Reservation reservation) {
        BookingDate today = new BookingDate(java.time.LocalDate.now());
        boolean isCheckOutDay = today.equals(reservation.getEndDate()) || 
                               today.isAfter(reservation.getEndDate());
        return isCheckOutDay && reservation.getRoom().isOccupied();
    }
    
    private boolean datesOverlap(BookingDate start1, BookingDate end1, 
                                BookingDate start2, BookingDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
    
    private Room findAvailableRoom(RoomType roomType) {
        if (hotel == null) return null;
        
        return hotel.getAvailableRooms().stream()
            .filter(room -> room.getRoomType().equals(roomType))
            .findFirst()
            .orElse(null);
    }
    
    private Reservation findReservation(String reservationNumber) {
        return reservations.stream()
            .filter(r -> r.getNumber().equals(reservationNumber))
            .findFirst()
            .orElse(null);
    }
    
    private String generateReservationNumber() {
        return "RES_" + System.currentTimeMillis() + "_" + name.hashCode();
    }
    
    private void validateReservationParameters(RoomType roomType, BookingDate startDate, 
                                              BookingDate endDate, HowMany howMany) {
        if (roomType == null) throw new IllegalArgumentException("Room type cannot be null");
        if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
        if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
        if (howMany == null) throw new IllegalArgumentException("HowMany cannot be null");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (howMany.getNumber() != 1) {
            throw new IllegalArgumentException("Currently only single room reservations supported");
        }
    }
    
    // Setters and getters
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    public Name getName() { return name; }
    public Hotel getHotel() { return hotel; }
    public List<Reservation> getReservations() { return new ArrayList<>(reservations); }
    public ReservePayer getReservePayer() { return reservePayer; }
}
