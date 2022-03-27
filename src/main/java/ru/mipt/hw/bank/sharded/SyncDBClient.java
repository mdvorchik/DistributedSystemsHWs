package ru.mipt.hw.bank.sharded;

import org.hibernate.Session;
import ru.mipt.hw.bank.account.Account;
import ru.mipt.hw.bank.transaction.Transaction;

import static ru.mipt.hw.bank.transaction.TXState.*;

public class SyncDBClient {
    private final Session s;

    public SyncDBClient(Session session) {
        this.s = session;
    }

    public Account cleanReadAccount(long id) {
        Account acc = s.get(Account.class, id);
        if (acc.getTransactionId() != null) {
            Transaction tx = s.get(Transaction.class, acc.getTransactionId());
            if (PENDING.getName().equalsIgnoreCase(tx.getStatus())) {
                tx.setStatus(ABORTED.getName());
                s.update(tx);
            }
            if (ABORTED.getName().equalsIgnoreCase(tx.getStatus())) {
                acc.setFutureBalance(null);
                acc.setTransactionId(null);
                return acc;
            }
            if (COMMITTED.getName().equalsIgnoreCase(tx.getStatus())) {
                acc.setBalance(acc.getFutureBalance());
                acc.setTransactionId(null);
                s.update(acc);
            }
        }
        return acc;
    }

    public Account updateAccount(Transaction tx, Account acc, Double money) {
        acc.setTransactionId(tx.getId());
        acc.setFutureBalance(acc.getBalance() + money);
        s.update(acc);
        return acc;
    }

    public void commitTransaction(Transaction tx) {
        s.getTransaction().begin();
        tx.setStatus(COMMITTED.getName());
        s.update(tx);
        s.getTransaction().commit();
    }

    public void cleanAccount(Account acc) {
        s.getTransaction().begin();
        acc.setBalance(acc.getFutureBalance());
        acc.setFutureBalance(null);
        acc.setTransactionId(null);
        s.update(acc);
        s.getTransaction().commit();
    }
}
