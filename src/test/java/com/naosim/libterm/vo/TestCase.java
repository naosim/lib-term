package com.naosim.libterm.vo;

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

    public static TestCase c(String caseName, Object... args) {
        return new TestCase(caseName, args);
    }

    public String getCaseName() {
        return caseName;
    }
}
