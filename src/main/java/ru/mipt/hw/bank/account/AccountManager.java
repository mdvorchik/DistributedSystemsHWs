package ru.mipt.hw.bank.account;

import org.hibernate.Session;

public class AccountManager {
    private final Session s;

    public AccountManager(Session session) {
        this.s = session;
    }

    public void createAccount() {
        Account account = new Account();
        account.setBalance(100);
        s.save(account);
    }
}
