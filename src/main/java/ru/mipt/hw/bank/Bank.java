package ru.mipt.hw.bank;


import org.hibernate.Session;

public class Bank {
    private final Session s;

    public Bank(Session session) {
        this.s = session;
    }

    //optimistic lock
    public void transfer(long fromId, long toId, double money) {
        // Hibernate session object to start the db transaction.
        Account fromAcc = s.get(Account.class, fromId);
        Account toAcc = s.get(Account.class, toId);
        if (fromAcc != null && toAcc != null) {
            s.getTransaction().begin();

            if (!fromAcc.hasMoney(money)) {
                throw new IllegalStateException(String.format("Acc with id %d doesn't enough money: %f", fromId, money));
            }

            fromAcc.changeBalance(-money);
            toAcc.changeBalance(money);

            s.update(fromAcc);
            s.update(toAcc);

            s.getTransaction().commit();
        }
    }
}
