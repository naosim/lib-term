package com.naosim.libterm.vo;

import java.util.Optional;

public class TermImpl<S extends LocalDateVO, E extends LocalDateVO> implements Term<S, E> {
    private final S startDate;
    private final Optional<E> endDateOptional;

    public TermImpl(S startDate, Optional<E> endDateOptional) {
        this.startDate = startDate;
        this.endDateOptional = endDateOptional;
    }

    public TermImpl(S startDate) {
        this(startDate, Optional.empty());
    }

    public TermImpl(S startDate, E endDate) {
        this(startDate, Optional.of(endDate));
    }

    @Override
    public S getStartDate() {
        return startDate;
    }

    @Override
    public Optional<E> getEndDateOption() {
        return endDateOptional;
    }
}
