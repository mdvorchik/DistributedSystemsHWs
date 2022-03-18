package ru.mipt.hw.bank;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "account")
// @DynamicUpdate - Mean the update sql statement is generated at runtime and contains only those columns whose values have changed.
@DynamicUpdate(value = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // @Version - Hibernate framework will check the version of the record before updating it.
    @Version
    private long version;
    private double balance;

    public long getId() {
        return id;
    }

    public boolean hasMoney(double money) {
        return balance >= money;
    }

    public void changeBalance(double money) {
        balance += money;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
