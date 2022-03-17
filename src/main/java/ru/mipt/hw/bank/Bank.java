package ru.mipt.hw.bank;

public class Bank {
    private final Accounts accounts;

    public Bank(Accounts accounts) {
        this.accounts = accounts;
    }

    //optimistic lock, pessimistic lock
    public void transfer(long fromId, long toId, double money) {
        Account fromAcc = accounts.findById(fromId);
        Account toAcc = accounts.findById(toId);

        var min = fromAcc.getId() < toAcc.getId() ? fromAcc : toAcc;
        var max = fromAcc.getId() >= toAcc.getId() ? fromAcc : toAcc;

        synchronized (min) {
            synchronized (max) {
                if (!fromAcc.hasMoney(money)) {
                    throw new IllegalStateException(String.format("Acc with id %d doesn't enough money: %f", fromId, money));
                }

                fromAcc.changeBalance(-money);
                toAcc.changeBalance(money);
            }
        }
    }
}
