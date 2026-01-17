package com.hotel.domain;

public class Room {
    private final String roomNumber;
    private final RoomType roomType;
    private Guest occupiedBy;
    private boolean isOccupied;
    
    public Room(String roomNumber, RoomType roomType) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be null or empty");
        }
        if (roomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isOccupied = false;
    }
    
    public void occupy(Guest guest) {
        if (isOccupied) {
            throw new IllegalStateException("Room is already occupied");
        }
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
        this.occupiedBy = guest;
        this.isOccupied = true;
    }
    
    public void vacate() {
        this.occupiedBy = null;
        this.isOccupied = false;
    }
    
    // Getters
    public String getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public Guest getOccupiedBy() { return occupiedBy; }
    public boolean isOccupied() { return isOccupied; }
}