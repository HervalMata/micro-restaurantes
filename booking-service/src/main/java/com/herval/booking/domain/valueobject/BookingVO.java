package com.herval.booking.domain.valueobject;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingVO {

    private String id;
    private String name;
    private String restaurantId;
    private String userId;
    private String tableId;
    private LocalDate date;
    private LocalTime time;

    public BookingVO(String id, String name, String restaurantId, String userId, String tableId, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.tableId = tableId;
        this.date = date;
        this.time = time;
    }

    public BookingVO() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, userId: %s, restaurantId: %s" + ", tableId: %s, date: %s, time: %s}", id, name, userId, restaurantId, tableId, date, time);
    }
}
