package com.naosim.ddd.term;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@EqualsAndHashCode
public class TermOnlyStart<S extends LocalDateTimeVO, E extends LocalDateTimeVO> implements Term<S, E> {
    @Getter
    private final S startDateTime;
    @Getter
    private final Optional<E> endDateTimeOptional = Optional.empty();

    public TermOnlyStart(S startDateTime) {
        this.startDateTime = startDateTime;
    }
}
