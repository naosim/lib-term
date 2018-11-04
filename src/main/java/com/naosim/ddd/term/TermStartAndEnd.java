package com.naosim.ddd.term;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@EqualsAndHashCode
public class TermStartAndEnd<S extends LocalDateVO, E extends LocalDateVO> implements Term<S, E> {
    @Getter
    private final S startDate;
    @Getter
    private final Optional<E> endDateOptional;

    public TermStartAndEnd(S startDate, E endDate) {
        this.startDate = startDate;
        this.endDateOptional = Optional.of(endDate);
    }

    public E getEndDate() {
        return endDateOptional.get();
    }
}
