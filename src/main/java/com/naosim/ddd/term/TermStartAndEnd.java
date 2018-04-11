package com.naosim.ddd.term;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@EqualsAndHashCode
public class TermStartAndEnd<S extends LocalDateTimeVO, E extends LocalDateTimeVO> implements Term<S, E> {
    @Getter
    private final S startDateTime;
    @Getter
    private final Optional<E> endDateTimeOptional;

    public TermStartAndEnd(S startDateTime, E endDate) {
        this.startDateTime = startDateTime;
        this.endDateTimeOptional = Optional.of(endDate);
    }

    public E getEndDateTime() {
        return endDateTimeOptional.get();
    }
}
