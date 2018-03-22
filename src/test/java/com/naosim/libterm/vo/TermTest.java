package com.naosim.libterm.vo;

import com.naosim.ddd.term.LocalDateVOImpl;
import com.naosim.ddd.term.Term;
import com.naosim.ddd.term.TermImpl;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.naosim.libterm.vo.TestCase.c;

public class TermTest {

    @Test
    public void isInTerm() {
        LocalDateVOImpl start = date(2018, 2, 1);
        LocalDateVOImpl end = date(2018, 2, 28);
        Term<LocalDateVOImpl, LocalDateVOImpl> sut = new TermImpl<>(start, end);

        assert sut.isInTerm(LocalDate.of(2018, 1, 31)) == false : "前";
        assert sut.isInTerm(LocalDate.of(2018, 2, 1)) == true : "内";
        assert sut.isInTerm(LocalDate.of(2018, 2, 28)) == true : "内";
        assert sut.isInTerm(LocalDate.of(2018, 3, 1)) == false : "後";
    }

    @Test
    public void isInTerm_endNotExists() {
        LocalDateVOImpl start = date(2018, 2, 1);
        Term<LocalDateVOImpl, LocalDateVOImpl> sut = new TermImpl<>(start);

        assert sut.isInTerm(LocalDate.of(2018, 1, 31)) == false : "前";
        assert sut.isInTerm(LocalDate.of(2018, 2, 1)) == true : "内";
    }

    @Test
    public void isIncludeFirstDayOfMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = new TermImpl<>(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeFirstDayOfMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20170101, Optional.of(20171231), false),
                c( "2", 20170101, Optional.of(20180101), true),
                c( "3", 20180101, Optional.of(20180101), true),
                c( "4", 20180101, Optional.of(20180201), true),
                c( "5", 20180101, Optional.empty()     , true),
                c( "6", 20180102, Optional.empty()     , false),
                c( "7", 20180102, Optional.of(20180228), false)
        );
    }

    @Test
    public void isIncludeFullMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = new TermImpl<>(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeFullMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20180101, Optional.of(20180131), true),
                c("2", 20171201, Optional.empty(), true),
                c( "3", 20171201, Optional.of(20180301), true),
                c( "4", 20171201, Optional.of(20171231), false),
                c( "5", 20180102, Optional.of(20180131), false),
                c( "6", 20180101, Optional.of(20180130), false),
                c( "7", 20180201, Optional.empty(), false)

        );
    }

    @Test
    public void isIncludeAtLeastOneDayOfMonth() {
        eachTest(
                c -> {
                    YearMonth targetYearMonth = YearMonth.of(2018, 1);
                    LocalDate start = intToLocalDate(c.get(0));
                    Optional<LocalDate> end = c.get(1, Optional.class).map(v -> intToLocalDate((int)v));
                    Term<LocalDateVOImpl, LocalDateVOImpl> sut = new TermImpl<>(
                            new LocalDateVOImpl(start),
                            end.map(LocalDateVOImpl::new)
                    );
                    assert sut.getTermIncludeYearMonthJudge(targetYearMonth).isIncludeAtLeastOneDayOfMonth() == c.get(2, Boolean.class) : c.getCaseName();
                },
                c("1", 20171201, Optional.of(20180101), true),
                c("2", 20180131, Optional.of(20180228), true),
                c( "3", 20180115, Optional.of(20180115), true),
                c( "4", 20180115, Optional.empty(), true),
                c( "5", 20171201, Optional.of(20171231), false),
                c( "6", 20180201, Optional.of(20180228), false),
                c( "7", 20180201, Optional.empty(), false)
        );
    }

    public static void eachTest(Consumer<TestCase> forEach, TestCase... list) {
        Stream.of(list).forEach(forEach);
    }

    static final LocalDate intToLocalDate(int value /* like 20180301 */) {
        int year = (int)Math.floor(value / 10000);
        value = value % 10000;
        int month = (int)Math.floor(value / 100);
        int dayOfMonth = value % 100;
        return LocalDate.of(year, month, dayOfMonth);
    }



    static LocalDateVOImpl date(int year, int month, int dayOfMonth) {
        return new LocalDateVOImpl(LocalDate.of(year, month, dayOfMonth));
    }
}