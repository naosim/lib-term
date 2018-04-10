package com.naosim.ddd.term;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

/**
 * 期間
 * 開始日時と終了日時を持つクラス
 * 終了日時がない場合、終了日時は無限として処理をする
 * @param <S> 開始日時
 * @param <E> 終了日時
 */
public interface Term<S extends LocalDateTimeVO, E extends LocalDateTimeVO> {
    /**
     * 開始日時の取得
     * @return
     */
    S getStartDateTime();

    /**
     * 終了日時の取得
     * @return
     */
    Optional<E> getEndDateTimeOptional();

    /**
     * dateが期間内かどうか
     *
     * 終了日時がある場合: 開始日時 <= date < 終了日時
     * 終了日時がない場合: 開始日時 <= date
     *
     * @param date
     * @return
     */
    default boolean isInTerm(LocalDateTime date) {
        if(this.getStartDateTime().isAfter(date)) {
            return false;
        }
        return getEndDateTimeOptional().map(v -> !v.isBefore(date)).orElse(true);
    }

    /**
     * dateが期間内かどうか
     *
     * 終了日時がある場合: 開始日時 <= date < 終了日時
     * 終了日時がない場合: 開始日時 <= date
     *
     * @param date
     * @return
     */
    default boolean isInTerm(LocalDateTimeVO date) {
        return isInTerm(date.getValue());
    }

    /**
     * dateが期間外かどうか
     *
     * 終了日時がある場合: date < 開始日時 or 終了日時 <= date
     * 終了日時がない場合: date < 開始日時
     *
     * @param date
     * @return
     */
    default boolean isOutOfTerm(LocalDateTime date) {
        return !isInTerm(date);
    }

    /**
     * dateが期間外かどうか
     *
     * 終了日時がある場合: date < 開始日時 or 終了日時 <= date
     * 終了日時がない場合: date < 開始日時
     *
     * @param date
     * @return
     */
    default boolean isOutOfTerm(LocalDateTimeVO date) {
        return !isInTerm(date);
    }

    default boolean hasEndDate() {
        return getEndDateTimeOptional().isPresent();
    }

    /**
     * 少なくとも1日は重なる期間がある
     * @param other
     * @return
     */
    default boolean isOverlapAtLeastOneDay(Term<? extends LocalDateTimeVO, ? extends LocalDateTimeVO> other) {
        final LocalDateTime infinity = LocalDateTime.of(2999, 12, 31, 23, 59, 59);

        LocalDateTime start = getStartDateTime().getValue();
        LocalDateTime end = getEndDateTimeOptional().map(LocalDateTimeVO::getValue).orElse(infinity);
        LocalDateTime otherStart = other.getStartDateTime().getValue();
        LocalDateTime otherEnd = other.getEndDateTimeOptional().map(LocalDateTimeVO::getValue).orElse(infinity);
        LocalDateTime maxStart = max(start, otherStart);
        LocalDateTime minEnd = min(end, otherEnd);

        return maxStart.equals(minEnd) || maxStart.isBefore(minEnd);
    }

    /**
     * 引数の全てを含んでいる
     * @param other
     * @return
     */
    default boolean containsFull(Term<? extends LocalDateTimeVO, ? extends LocalDateTimeVO> other) {
        final LocalDateTime infinity = LocalDateTime.of(2999, 12, 31, 23, 59, 59);
        LocalDateTime otherStart = other.getStartDateTime().getValue();
        LocalDateTime otherEnd = other.getEndDateTimeOptional().map(LocalDateTimeVO::getValue).orElse(infinity);
        return isInTerm(otherStart) && isInTerm(otherEnd);
    }

    default TermIncludeYearMonthJudge getTermIncludeYearMonthJudge(YearMonth targetYearMonth) {
        return new TermIncludeYearMonthJudge(this, targetYearMonth);
    }

    static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        return a.isAfter(b) ? a : b;
    }

    static LocalDateTime min(LocalDateTime a, LocalDateTime b) {
        return a.isBefore(b) ? a : b;
    }
}
