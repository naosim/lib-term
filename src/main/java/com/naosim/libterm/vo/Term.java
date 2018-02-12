package com.naosim.libterm.vo;

import java.time.LocalDate;
import java.util.Optional;

/**
 * 期間
 * @param <S> 開始日
 * @param <E> 終了日
 */
public interface Term<S extends LocalDateVO, E extends LocalDateVO> {
    S getStartDate();
    Optional<E> getEndDateOption();

    default boolean isInTerm(LocalDate date) {
        if(this.getStartDate().isAfter(date)) {
            return false;
        }
        return getEndDateOption().map(v -> !v.isBefore(date)).orElse(true);
    }

    default boolean isOutOfTerm(LocalDate date) {
        return !isInTerm(date);
    }

}
