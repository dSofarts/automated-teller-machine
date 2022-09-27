package org.dsofarts;

import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    /**
     * Create a new Account
     * @param name
     * @param holder
     * @param theBank
     */
    public Account(String name, User holder, Bank theBank) {

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * Get the account ID
     * @return the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get summary line for the account
     * @return
     */
    public String getSummaryLine() {

        // get the account's balance
        double balance = this.getBalance();

        // format the summary line
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    /**
     * Get the balance of the Account
     * @return
     */
    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }
}
