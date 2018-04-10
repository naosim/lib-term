package com.naosim.ddd.term;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
public class LocalDateTimeVOImpl implements LocalDateTimeVO {
    @Getter
    private final LocalDateTime value;
    public LocalDateTimeVOImpl(LocalDate value) {
        this.value = value.atTime(0, 0);
    }
}
