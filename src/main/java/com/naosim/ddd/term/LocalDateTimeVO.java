package com.naosim.ddd.term;

import java.time.LocalDateTime;

public interface LocalDateTimeVO {
    LocalDateTime getValue();
    default boolean isBefore(LocalDateTime other) {
        return getValue().isBefore(other);
    }

    default boolean isBefore(LocalDateTimeVO other) {
        return isBefore(other.getValue());
    }

    default boolean isAfter(LocalDateTime other) {
        return getValue().isAfter(other);
    }

    default boolean isAfter(LocalDateTimeVO other) {
        return isAfter(other.getValue());
    }
}
