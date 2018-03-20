package com.naosim.ddd.term;

import java.time.LocalDate;

public class LocalDateVOImpl implements LocalDateVO {
    private final LocalDate value;

    public LocalDateVOImpl(LocalDate value) {
        this.value = value;

    }
    @Override
    public LocalDate getValue() {
        return value;
    }
}
