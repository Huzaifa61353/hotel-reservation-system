package com.hotel.domain;

import com.hotel.domain.valueobjects.BookingDate;

public class Reservation {
    private final BookingDate reservationDate;
    private final BookingDate startDate;
    private final BookingDate endDate;
    private final String number;
    private final Room room;
    private final RoomType roomType;
    private final HowMany howMany;
    private Guest guest;
    
    public Reservation(BookingDate reservationDate, BookingDate startDate, 
                      BookingDate endDate, String number, Room room, 
                      RoomType roomType, HowMany howMany) {
        validateParameters(reservationDate, startDate, endDate, number, room, roomType, howMany);
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.number = number;
        this.room = room;
        this.roomType = roomType;
        this.howMany = howMany;
    }
    
    private void validateParameters(BookingDate reservationDate, BookingDate startDate, 
                                   BookingDate endDate, String number, Room room, 
                                   RoomType roomType, HowMany howMany) {
        if (reservationDate == null) throw new IllegalArgumentException("Reservation date cannot be null");
        if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
        if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation number cannot be null or empty");
        }
        if (room == null) throw new IllegalArgumentException("Room cannot be null");
        if (roomType == null) throw new IllegalArgumentException("Room type cannot be null");
        if (howMany == null) throw new IllegalArgumentException("HowMany cannot be null");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
    
    public void assignGuest(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
        this.guest = guest;
    }
    
    // Getters only - Reservation is a state holder (no business logic)
    public BookingDate getReservationDate() { return reservationDate; }
    public BookingDate getStartDate() { return startDate; }
    public BookingDate getEndDate() { return endDate; }
    public String getNumber() { return number; }
    public Room getRoom() { return room; }
    public RoomType getRoomType() { return roomType; }
    public HowMany getHowMany() { return howMany; }
    public Guest getGuest() { return guest; }
}