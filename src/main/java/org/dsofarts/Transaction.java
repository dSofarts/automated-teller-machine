package org.dsofarts;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    /**
     * Create a new Transaction
     * @param amount
     * @param inAccount
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * Create a new transaction
     * @param amount
     * @param memo
     * @param inAccount
     */
    public Transaction(double amount, String memo, Account inAccount) {

        // call the two-arg constructor first
        this(amount, inAccount);

        // set the memo
        this.memo = memo;
    }

    /**
     * Get the amount of the transaction
     * @return
     */
    public double getAmount() {
        return this.amount;
    }

}
