package com.hotel.domain;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final String name;
    private final List<Room> rooms;
    
    public Hotel(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be null or empty");
        }
        this.name = name;
        this.rooms = new ArrayList<>();
    }
    
    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        rooms.add(room);
    }
    
    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isOccupied()) {
                available.add(room);
            }
        }
        return available;
    }
    
    public Room findRoomByNumber(String roomNumber) {
        return rooms.stream()
            .filter(room -> room.getRoomNumber().equals(roomNumber))
            .findFirst()
            .orElse(null);
    }
    
    // Getters
    public String getName() { return name; }
    public List<Room> getRooms() { return new ArrayList<>(rooms); }
}