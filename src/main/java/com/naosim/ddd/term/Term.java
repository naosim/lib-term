package com.naosim.ddd.term;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 期間
 * 開始日と終了日を持つクラス
 * 終了日がない場合、終了日は無限として処理をする
 * @param <S> 開始日
 * @param <E> 終了日
 */
public interface Term<S extends LocalDateVO, E extends LocalDateVO> {
    /**
     * 開始日の取得
     * @return
     */
    S getStartDate();

    /**
     * 終了日の取得
     * @return
     */
    Optional<E> getEndDateOptional();

    /**
     * dateが期間内かどうか
     *
     * 終了日がある場合: 開始日 <= date < 終了日
     * 終了日がない場合: 開始日 <= date
     *
     * @param date
     * @return
     */
    default boolean isInTerm(LocalDate date) {
        if(this.getStartDate().isAfter(date)) {
            return false;
        }
        return getEndDateOptional().map(v -> !v.isBefore(date)).orElse(true);
    }

    /**
     * dateが期間内かどうか
     *
     * 終了日がある場合: 開始日 <= date < 終了日
     * 終了日がない場合: 開始日 <= date
     *
     * @param date
     * @return
     */
    default boolean isInTerm(LocalDateVO date) {
        return isInTerm(date.getValue());
    }

    /**
     * dateが期間外かどうか
     *
     * 終了日がある場合: date < 開始日 or 終了日 <= date
     * 終了日がない場合: date < 開始日
     *
     * @param date
     * @return
     */
    default boolean isOutOfTerm(LocalDate date) {
        return !isInTerm(date);
    }

    /**
     * dateが期間外かどうか
     *
     * 終了日がある場合: date < 開始日 or 終了日 <= date
     * 終了日がない場合: date < 開始日
     *
     * @param date
     * @return
     */
    default boolean isOutOfTerm(LocalDateVO date) {
        return !isInTerm(date);
    }

    default boolean hasEndDate() {
        return getEndDateOptional().isPresent();
    }

    /**
     * 少なくとも1日は重なる期間がある
     * @param other
     * @return
     */
    default boolean isOverlapAtLeastOneDay(Term<? extends LocalDateVO, ? extends LocalDateVO> other) {
        final LocalDate infinity = LocalDate.of(2999, 12, 31);

        LocalDate start = getStartDate().getValue();
        LocalDate end = getEndDateOptional().map(LocalDateVO::getValue).orElse(infinity);
        LocalDate otherStart = other.getStartDate().getValue();
        LocalDate otherEnd = other.getEndDateOptional().map(LocalDateVO::getValue).orElse(infinity);
        LocalDate maxStart = max(start, otherStart);
        LocalDate minEnd = min(end, otherEnd);

        return maxStart.equals(minEnd) || maxStart.isBefore(minEnd);
    }

    /**
     * 引数の全てを含んでいる
     * @param other
     * @return
     */
    default boolean containsFull(Term<? extends LocalDateVO, ? extends LocalDateVO> other) {
        final LocalDate infinity = LocalDate.of(2999, 12, 31);
        LocalDate otherStart = other.getStartDate().getValue();
        LocalDate otherEnd = other.getEndDateOptional().map(LocalDateVO::getValue).orElse(infinity);
        return isInTerm(otherStart) && isInTerm(otherEnd);
    }

    /**
     * 終了日の有無で分岐する(戻り値あり)
     * @param onlyStart
     * @param startAndEnd
     * @param <T>
     * @return
     */
    default <T> T mapIfOnlyStart(
            Function<TermOnlyStart<S, E>, T> onlyStart,
            Function<TermStartAndEnd<S, E>, T> startAndEnd
    ) {
        return hasEndDate() ? startAndEnd.apply(new TermStartAndEnd<>(getStartDate(), getEndDateOptional().get())) : onlyStart.apply(new TermOnlyStart<>(getStartDate()));
    }

    /**
     * 終了日の有無で分岐する
     * @param onlyStart
     * @param startAndEnd
     */
    default void ifOnlyStart(
            Consumer<TermOnlyStart<S, E>> onlyStart,
            Consumer<TermStartAndEnd<S, E>> startAndEnd
    ) {
        if(hasEndDate()) {
            startAndEnd.accept(new TermStartAndEnd<>(getStartDate(), getEndDateOptional().get()));
        } else {
            onlyStart.accept(new TermOnlyStart<>(getStartDate()));
        }
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

    static <S extends LocalDateVO, E extends LocalDateVO> Term<S, E> termOf(S startDate, Optional<E> endDateOptional) {
        return endDateOptional
                .<Term<S, E>>map(end -> new TermStartAndEnd<>(startDate, end))
                .orElseGet(() -> new TermOnlyStart<>(startDate));
    }

    static <S extends LocalDateVO, E extends LocalDateVO> Term<S, E> termOf(S startDate) {
        return new TermOnlyStart<>(startDate);
    }

    static <S extends LocalDateVO, E extends LocalDateVO> Term<S, E> termOf(S startDate, E endDate) {
        return new TermStartAndEnd<>(startDate, endDate);
    }
}
