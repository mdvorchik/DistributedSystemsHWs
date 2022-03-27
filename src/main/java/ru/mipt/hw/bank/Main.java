package ru.mipt.hw.bank;


import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import ru.mipt.hw.bank.account.AccountManager;
import ru.mipt.hw.bank.sharded.Bank;
import ru.mipt.hw.bank.sharded.SyncDBClient;
import ru.mipt.hw.bank.transaction.TransactionManager;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        Session session = configuration.buildSessionFactory().openSession();

        AccountManager accountManager = new AccountManager(session);
        TransactionManager transactionManager = new TransactionManager(session);
        SyncDBClient syncDBClient = new SyncDBClient(session);

        Bank bank = new Bank(syncDBClient, transactionManager);

        accountManager.createAccount();
        accountManager.createAccount();
        accountManager.createAccount();


        bank.transfer(1, 2, 50);
        bank.transfer(3, 1, 50);
        bank.transfer(2, 1, 25);
    }
}
