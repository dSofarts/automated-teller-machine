package org.dsofarts;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

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
        System.out.printf("New user %s, %s, %s width ID %s created. \n", lastName, middleName, firstName, this.uuid);
    }

    /**
     * Add an account for the user
     * @param anAcct
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

}
