package com.naosim.ddd.term;

import java.time.LocalDate;
import java.time.YearMonth;
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

    default boolean hasEndDate() {
        return getEndDateOption().isPresent();
    }

    default boolean isOverlapAtLeastOneDay(Term<? extends LocalDateVO, ? extends LocalDateVO> other) {
        LocalDate start = getStartDate().getValue();
        LocalDate end = getEndDateOption().map(LocalDateVO::getValue).orElse(LocalDate.of(2999, 12, 31));
        LocalDate otherStart = other.getStartDate().getValue();
        LocalDate otherEnd = other.getEndDateOption().map(LocalDateVO::getValue).orElse(LocalDate.of(2999, 12, 31));

        LocalDate maxStart = max(start, otherStart);
        LocalDate minEnd = min(end, otherEnd);

        return maxStart.equals(minEnd) || maxStart.isBefore(minEnd);
    }

    default TermIncludeYearMonthJudge getTermIncludeYearMonthJudge(YearMonth targetYearMonth) {
        return new TermIncludeYearMonthJudge(this, targetYearMonth);
    }

    static LocalDate max(LocalDate a, LocalDate b) {
        return a.isAfter(b) ? a : b;
    }

    static LocalDate min(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? a : b;
    }
}
