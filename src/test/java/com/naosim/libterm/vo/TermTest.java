package com.naosim.libterm.vo;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

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

    static LocalDateVOImpl date(int year, int month, int dayOfMonth) {
        return new LocalDateVOImpl(LocalDate.of(year, month, dayOfMonth));
    }
}