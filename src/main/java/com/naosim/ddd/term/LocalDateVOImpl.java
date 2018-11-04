package com.naosim.ddd.term;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
public class LocalDateVOImpl implements LocalDateVO {
    @Getter
    private final LocalDate value;
    public LocalDateVOImpl(LocalDateTime value) {
        this.value = value.toLocalDate();
    }
}
