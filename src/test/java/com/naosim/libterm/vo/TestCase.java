package com.naosim.libterm.vo;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestCase {
    private final String caseName;
    private final Object[] args;

    TestCase(String caseName, Object... args) {
        this.caseName = caseName;
        this.args = args;
    }

    public <T> T get(int index) {
        return (T)args[index];
    }

    public <T> T get(int index, Class<T> clazz) {
        return (T)args[index];
    }

    public String getCaseName() {
        return caseName;
    }

    public static void eachTest(Consumer<TestCase> forEach, TestCase... list) {
        Stream.of(list).forEach(forEach);
    }

    public static TestCase c(String caseName, Object... args) {
        return new TestCase(caseName, args);
    }
}
