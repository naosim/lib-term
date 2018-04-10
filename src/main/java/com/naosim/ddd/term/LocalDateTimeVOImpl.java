package com.naosim.ddd.term;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeVOImpl implements LocalDateTimeVO {
    private final LocalDateTime value;

    public LocalDateTimeVOImpl(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTimeVOImpl(LocalDate value) {
        this.value = value.atTime(0, 0);
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }
}
