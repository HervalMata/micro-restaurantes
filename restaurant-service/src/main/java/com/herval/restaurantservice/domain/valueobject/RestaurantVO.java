package com.herval.restaurantservice.domain.valueobject;

import com.herval.restaurantservice.domain.model.entity.Table;

import java.util.List;
import java.util.Optional;

public class RestaurantVO {

    private String id;
    private String name;
    private String address;
    private Optional<List<Table>> tables;

    public RestaurantVO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Optional<List<Table>> getTables() {
        return tables;
    }

    public void setTables(Optional<List<Table>> tables) {
        this.tables = tables;
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
        return String.format("{id: %s, name: %s, address: %s, tables: %s}", this.getId(), this.getName(), this.getAddress(), this.getTables());
    }
}
