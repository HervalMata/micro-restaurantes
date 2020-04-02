package com.herval.restaurantservice.domain.repository;

import java.util.Collection;

public interface RestaurantRepository<Booking, String> extends Repository<Booking, String> {

    boolean containsName(String name);

    public Collection<Booking> findByName(String name) throws Exception;
}
