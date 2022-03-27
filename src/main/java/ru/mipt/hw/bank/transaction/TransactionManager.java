package ru.mipt.hw.bank.transaction;

import org.hibernate.Session;

import static ru.mipt.hw.bank.transaction.TXState.PENDING;

public class TransactionManager {
    private final Session s;

    public TransactionManager(Session session) {
        this.s = session;
    }

    public Transaction createTransaction() {
        Transaction tx = new Transaction();
        tx.setStatus(PENDING.getName());
        s.save(tx);
        return tx;
    }
}
