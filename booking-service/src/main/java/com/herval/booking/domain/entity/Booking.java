package com.herval.booking.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking extends BaseEntity<String> {

    private String restaurantId;
    private String userId;
    private String tableId;
    private LocalDate date;
    private LocalTime time;

    public Booking(String id, String name, String restaurantId, String userId, String tableId, LocalDate date, LocalTime time) {
        super(id, name);
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.tableId = tableId;
        this.date = date;
        this.time = time;
    }

    private Booking(String id, String name) {
        super(id, name);
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, userId: %s, restaurantId: %s" + ", tableId: %s, date: %s, time: %s}",
                            id, name, userId, restaurantId, tableId, date, time);
    }
}
