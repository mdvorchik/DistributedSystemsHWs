package ru.mipt.hw.bank;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Accounts {
    private final Map<Long, Account> accById = new ConcurrentHashMap<>();

    public Account findById(long fromId) {
        return accById.computeIfAbsent(fromId, (k) -> new Account(k, 100));
    }
}
