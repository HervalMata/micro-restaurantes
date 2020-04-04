package com.herval.booking.domain.service;

import com.herval.booking.domain.repository.ReadOnlyRepository;

public abstract class ReadOnlyBaseService<TE, T> {

    private ReadOnlyRepository<TE, T> repository;

    public ReadOnlyBaseService(ReadOnlyRepository<TE, T> repository) {
        this.repository = repository;
    }
}
