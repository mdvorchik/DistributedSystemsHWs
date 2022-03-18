package ru.mipt.hw.bank;


import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        Session session = configuration.buildSessionFactory().openSession();

        AccountManager accountManager = new AccountManager(session);
        Bank bank = new Bank(session);

        accountManager.createAccount();
        accountManager.createAccount();
        accountManager.createAccount();

        bank.transfer(1, 2, 50);
        bank.transfer(3, 1, 50);
        bank.transfer(2, 1, 25);
    }
}
