package com.naosim.ddd.term;

import java.util.Optional;

public class TermImpl<S extends LocalDateTimeVO, E extends LocalDateTimeVO> implements Term<S, E> {
    private final S startDateTime;
    private final Optional<E> endDateTimeOptional;

    public TermImpl(S startDateTime, Optional<E> endDateTimeOptional) {
        this.startDateTime = startDateTime;
        this.endDateTimeOptional = endDateTimeOptional;
    }

    public TermImpl(S startDateTime) {
        this(startDateTime, Optional.empty());
    }

    public TermImpl(S startDateTime, E endDate) {
        this(startDateTime, Optional.of(endDate));
    }

    @Override
    public S getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Optional<E> getEndDateOption() {
        return endDateTimeOptional;
    }
}
