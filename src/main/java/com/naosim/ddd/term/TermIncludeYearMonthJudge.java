package com.naosim.ddd.term;

import java.time.LocalDate;
import java.time.YearMonth;

public class TermIncludeYearMonthJudge {
    private final Term term;
    private final YearMonth targetYearMonth;

    public TermIncludeYearMonthJudge(Term term, YearMonth targetYearMonth) {
        this.term = term;
        this.targetYearMonth = targetYearMonth;
    }

    public boolean isIncludeFirstDayOfMonth() {
        LocalDate firstDayOfMonth = targetYearMonth.atDay(1);
        return term.isInTerm(firstDayOfMonth);
    }

    public boolean isIncludeEndOfMonth() {
        LocalDate endOfMonth = targetYearMonth.atEndOfMonth();
        return term.isInTerm(endOfMonth);
    }

    public boolean isIncludeFullMonth() {
        return isIncludeFirstDayOfMonth () && isIncludeEndOfMonth();
    }

    public boolean isIncludeAtLeastOneDayOfMonth() {
        TermImpl targetTerm = new TermImpl<LocalDateVO, LocalDateVO>(
                new LocalDateVOImpl(targetYearMonth.atDay(1)),
                new LocalDateVOImpl(targetYearMonth.atEndOfMonth())
        );
        return term.isOverlapAtLeastOneDay(targetTerm);
    }


}
