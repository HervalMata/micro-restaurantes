package com.herval.userservice.domain.service;

import com.herval.userservice.domain.repository.ReadOnlyRepository;

public abstract class ReadOnlyBaseService<TE, T> {

    private ReadOnlyRepository<TE, T> repository;

    public ReadOnlyBaseService(ReadOnlyRepository<TE, T> repository) {
        this.repository = repository;
    }
}
