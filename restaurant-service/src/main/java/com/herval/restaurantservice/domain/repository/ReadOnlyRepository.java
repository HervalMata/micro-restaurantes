package com.herval.restaurantservice.domain.repository;

import java.util.Collection;

public interface ReadOnlyRepository<TE, T> {

    boolean contains(T id);

    TE get(T id);

    Collection<TE> getAll();
}