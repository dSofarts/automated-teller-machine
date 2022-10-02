package org.dsofarts;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.util.Scanner;

public class User {
    private String firstName;
    private String middleName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName
     * @param middleName
     * @param lastName
     * @param pin
     * @param theBank
     */
    public User(String firstName, String middleName, String lastName, String pin, Bank theBank) {

        // set user's name
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than the original value for security reason
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique universal ID for the user
        this.uuid= theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s, %s width ID %s created. \n", lastName, firstName, middleName, this.uuid);
    }

    /**
     * Add an account for the user
     * @param anAcct
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Return the user's UUID
     * @return the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get the users firstname
     * @return
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPin
     * @return
     */
    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Print summary for the account of the users
     */
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("\t%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of accounts of the user
     * @return
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account
     * @param accountIndex
     */
    public void printAccountTransactionHistory(int accountIndex) {
        this.accounts.get(accountIndex).printTransactionHistory();

    }

    /**
     * Get the balance of a particular account
     * @param accountIndex
     * @return
     */
    public double getAccountBalance(int accountIndex) {
        return this.accounts.get(accountIndex).getBalance();
    }

    /**
     * Get the UUID of the particular account
     * @param accountIndex
     * @return
     */
    public String getAccountUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    /**
     * Add a transaction of the particular account
     * @param accountIndex
     * @param amount
     * @param memo
     */
    public void addAccountTransaction(int accountIndex, double amount, String memo) {
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }

}
