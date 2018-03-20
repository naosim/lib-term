package com.naosim.ddd.term;

import java.time.LocalDate;

public interface LocalDateVO {
    LocalDate getValue();
    default boolean isBefore(LocalDate other) {
        return getValue().isBefore(other);
    }

    default boolean isAfter(LocalDate other) {
        return getValue().isAfter(other);
    }
}
