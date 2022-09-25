package org.dsofarts;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public String getNewUserUUID() {

    }

    public String getNewAccountUUID() {

    }

    /**
     * Add an account
     * @param anAcct
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

}
