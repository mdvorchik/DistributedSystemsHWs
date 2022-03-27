package ru.mipt.hw.bank.sharded;


import ru.mipt.hw.bank.account.Account;
import ru.mipt.hw.bank.transaction.Transaction;
import ru.mipt.hw.bank.transaction.TransactionManager;

public class Bank {
    private final SyncDBClient client;
    private final TransactionManager transactionManager;

    public Bank(SyncDBClient client, TransactionManager transactionManager) {
        this.client = client;
        this.transactionManager = transactionManager;
    }

    public void transfer(long fromId, long toId, double money) {
        Transaction tx = transactionManager.createTransaction();
        Account fromAcc = client.cleanReadAccount(fromId);
        Account toAcc = client.cleanReadAccount(toId);
        if (fromAcc != null && toAcc != null) {
            if (!fromAcc.hasMoney(money)) {
                throw new IllegalStateException(String.format("Acc with id %d doesn't enough money: %f", fromId, money));
            }
            fromAcc = client.updateAccount(tx, fromAcc, -money);
            toAcc = client.updateAccount(tx, toAcc, money);
            client.commitTransaction(tx);
            client.cleanAccount(fromAcc);
            client.cleanAccount(toAcc);
        }
    }
}
