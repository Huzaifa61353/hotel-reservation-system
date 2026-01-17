package com.hotel.domain;

import com.hotel.domain.valueobjects.RoomKind;
import com.hotel.domain.valueobjects.Money;

public class RoomType {
    private final RoomKind kind;
    private final Money cost;
    private final String roomTypeId;
    
    public RoomType(RoomKind kind, Money cost) {
        validateParameters(kind, cost);
        this.kind = kind;
        this.cost = cost;
        this.roomTypeId = generateRoomTypeId();
    }
    
    private void validateParameters(RoomKind kind, Money cost) {
        if (kind == null) {
            throw new IllegalArgumentException("Room kind cannot be null");
        }
        if (cost == null) {
            throw new IllegalArgumentException("Cost cannot be null");
        }
    }
    
    private String generateRoomTypeId() {
        return "ROOM_" + kind.name() + "_" + System.currentTimeMillis();
    }
    
    // Getters
    public RoomKind getKind() { return kind; }
    public Money getCost() { return cost; }
    public String getRoomTypeId() { return roomTypeId; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RoomType other)) return false;
        return this.kind == other.kind && 
               this.cost.amount().equals(other.cost.amount()) &&
               this.cost.currency().equals(other.cost.currency());
    }
    
    @Override
    public int hashCode() {
        return kind.hashCode() + cost.amount().hashCode() + cost.currency().hashCode();
    }
}