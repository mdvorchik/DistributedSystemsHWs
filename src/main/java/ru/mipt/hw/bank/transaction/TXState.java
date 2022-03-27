package ru.mipt.hw.bank.transaction;

public enum TXState {
    COMMITTED("committed"),
    ABORTED("aborted"),
    PENDING("pending");

    private String name;

    TXState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
