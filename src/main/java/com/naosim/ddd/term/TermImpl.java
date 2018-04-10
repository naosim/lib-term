package com.naosim.ddd.term;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@EqualsAndHashCode
public class TermImpl<S extends LocalDateTimeVO, E extends LocalDateTimeVO> implements Term<S, E> {
    @Getter
    private final S startDateTime;
    @Getter
    private final Optional<E> endDateTimeOptional;

    public TermImpl(S startDateTime) {
        this(startDateTime, Optional.empty());
    }

    public TermImpl(S startDateTime, E endDate) {
        this(startDateTime, Optional.of(endDate));
    }
}
