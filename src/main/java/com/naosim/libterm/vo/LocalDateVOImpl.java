package com.naosim.libterm.vo;

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
