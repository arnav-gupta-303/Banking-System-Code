package domain;

import java.security.PrivateKey;
import java.time.LocalDateTime;

public class Transaction {
    private String ID;// ID of that transaction
    private Type type;
    private Double amount;// Amountin process at that transaction
    private LocalDateTime time ;// Time of transaction
    private String note;

    public String getID() {
        return ID;
    }

    public Type getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public Transaction(String ID, Type type, Double amount, LocalDateTime time, String note) {
        this.ID = ID;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.note = note;
    }
}
