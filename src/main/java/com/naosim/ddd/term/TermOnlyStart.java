package com.naosim.ddd.term;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@EqualsAndHashCode
public class TermOnlyStart<S extends LocalDateVO, E extends LocalDateVO> implements Term<S, E> {
    @Getter
    private final S startDate;
    @Getter
    private final Optional<E> endDateOptional = Optional.empty();

    public TermOnlyStart(S startDate) {
        this.startDate = startDate;
    }
}
