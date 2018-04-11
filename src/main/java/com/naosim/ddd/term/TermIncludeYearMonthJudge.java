package com.naosim.ddd.term;

import java.time.YearMonth;

/**
 * 期間に指定月を含んでいるかどうかを判定する
 */
public class TermIncludeYearMonthJudge {
    private final Term term;
    private final YearMonth targetYearMonth;
    private final TermStartAndEnd<LocalDateTimeVO, LocalDateTimeVO> targetYearMonthTerm;

    public TermIncludeYearMonthJudge(Term term, YearMonth targetYearMonth) {
        this.term = term;
        this.targetYearMonth = targetYearMonth;
        this.targetYearMonthTerm = new TermStartAndEnd<>(
                new LocalDateTimeVOImpl(targetYearMonth.atDay(1)),
                new LocalDateTimeVOImpl(targetYearMonth.atEndOfMonth())
        );
    }

    /**
     * 指定月の初日を含んでいる
     * @return
     */
    public boolean isIncludeFirstDayOfMonth() {
        return term.isInTerm(targetYearMonthTerm.getStartDateTime());
    }

    /**
     * 指定月の末日を含んでいる
     * @return
     */
    public boolean isIncludeEndOfMonth() {
        return term.isInTerm(targetYearMonthTerm.getEndDateTime());
    }

    /**
     * 指定月を全て含んでいる
     * @return
     */
    public boolean isIncludeFullMonth() {
        return term.containsFull(targetYearMonthTerm);
    }

    /**
     * 指定月の少なくとも1日は含んでいる
     * @return
     */
    public boolean isIncludeAtLeastOneDayOfMonth() {
        return term.isOverlapAtLeastOneDay(targetYearMonthTerm);
    }
}
