package ru.mipt.hw.bank.transaction;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
// @DynamicUpdate - Mean the update sql statement is generated at runtime and contains only those columns whose values have changed.
@DynamicUpdate(value = true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // @Version - Hibernate framework will check the version of the record before updating it.
    @Version
    private long version;
    private String status;

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
