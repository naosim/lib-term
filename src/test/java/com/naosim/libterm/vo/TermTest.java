package com.naosim.libterm.vo;

import com.naosim.ddd.term.LocalDateVOImpl;
import com.naosim.ddd.term.Term;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import java.util.function.Function;

import static com.naosim.ddd.term.Term.termOf;
import static com.naosim.libterm.vo.TestCase.c;
import static com.naosim.libterm.vo.TestCase.eachTest;

public class TermTest {

    @Test
    public void isInTerm() {
        eachTest(
                c -> {
                    LocalDateVOImpl start = date(20180201);
                    LocalDateVOImpl end = date(20180228);
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = termOf(start, end);

                    assert sut.isInTerm(intToLocalDate(c.get(0))) == (boolean)c.get(1) : c.getCaseName();
                },
                c("前" , 20180131, false),
                c("内1", 20180201, true),
                c("内2", 20180228, true),
                c("後" , 20180301, false)
        );
    }

    @Test
    public void isInTerm_endNotExists() {
        eachTest(
                c -> {
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = termOf(date(20180201));
                    assert sut.isInTerm(intToLocalDate(c.get(0))) == (boolean)c.get(1) : c.getCaseName();
                },
                c("前", 20180131, false),
                c("内", 20180201, true)
        );
    }

    @Test
    public void isIncludeFirstDayOfMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = termOf(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeFirstDayOfMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20170101, Optional.of(20171231), false),
                c("2", 20170101, Optional.of(20180101), true),
                c("3", 20180101, Optional.of(20180101), true),
                c("4", 20180101, Optional.of(20180201), true),
                c("5", 20180101, Optional.empty()     , true),
                c("6", 20180102, Optional.empty()     , false),
                c("7", 20180102, Optional.of(20180228), false)
        );
    }

    @Test
    public void isIncludeFullMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = termOf(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeFullMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20180101, Optional.of(20180131), true),
                c("2", 20171201, Optional.empty()     , true),
                c("3", 20171201, Optional.of(20180301), true),
                c("4", 20171201, Optional.of(20171231), false),
                c("5", 20180102, Optional.of(20180131), false),
                c("6", 20180101, Optional.of(20180130), false),
                c("7", 20180201, Optional.empty()     , false)

        );
    }

    @Test
    public void isIncludeAtLeastOneDayOfMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = termOf(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeAtLeastOneDayOfMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20171201, Optional.of(20180101), true),
                c("2", 20180131, Optional.of(20180228), true),
                c("3", 20180115, Optional.of(20180115), true),
                c("4", 20180115, Optional.empty()     , true),
                c("5", 20171201, Optional.of(20171231), false),
                c("6", 20180201, Optional.of(20180228), false),
                c("7", 20180201, Optional.empty()     , false)
        );
    }

    /**
     * Create LocalDate From integer like 20180301
     * @param value
     * @return
     */
    static final LocalDate intToLocalDate(int value) {
        String str = "" + value;
        int year = Integer.valueOf(str.substring(0, 4));
        int month = Integer.valueOf(str.substring(4, 6));
        int dayOfMonth = Integer.valueOf(str.substring(6, 8));
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Create LocalDate From integer like 20180301
     * @param value
     * @return
     */
    static final <T> T intToLocalDate(int value, Function<LocalDate, T> factory) {
        return factory.apply(intToLocalDate(value));
    }

    static LocalDateVOImpl date(int yyyymmdd) {
        return intToLocalDate(yyyymmdd, LocalDateVOImpl::new);
    }
}