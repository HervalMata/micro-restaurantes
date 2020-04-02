package com.herval.restaurantservice.domain.model.entity;

import java.util.List;
import java.util.Optional;

public class Restaurant extends BaseEntity<String> {

    private Optional<List<Table>> tables;
    private String address;

    public Restaurant(String id, String name, String address, Optional<List<Table>> tables) {
        super(id, name);
        this.address = address;
        this.tables = tables;
    }

    private Restaurant(String id, String name) {
        super(id, name);
        this.tables = Optional.empty();
    }

    public static Restaurant getDummyRestaurant() {
        return new Restaurant(null, null);
    }

    public void setTables(Optional<List<Table>> tables) {
        this.tables = tables;
    }

    public Optional<List<Table>> getTables() {
        return tables;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, name: %s, address: %s, tables: %s}", this.getId(), this.getName(), this.getAddress(), this.getTables());
    }
}
