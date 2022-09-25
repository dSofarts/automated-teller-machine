package org.dsofarts;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    /**
     * Create a new Bank object width empty lists of users and accounts
     * @param name
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate a new universally unique ID for a user
     * @return the uuid
     */
    public String getNewUserUUID() {

        // inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // check to make sure it's unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Generate a new universally unique ID for an account
     * @return the uuid
     */
    public String getNewAccountUUID() {

        // inits
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // check to make sure it's unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Add an account
     * @param anAcct
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Create a new user of the bank
     * @param firstName
     * @param middleName
     * @param lastName
     * @param pin
     * @return
     */
    public User addUser(String firstName, String middleName, String lastName, String pin) {

        // create a new User object and add it to our list
        User newUser = new User(firstName, middleName, lastName, pin, this);
        this.users.add(newUser);

        // create a saving account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Get the User object associated width a particular userID and pin, if they are valid
     * @param userID
     * @param pin
     * @return
     */
    public User userLogin(String userID, String pin) {

        // search through list of users
        for (User u : this.users) {

            // check user ID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        // if we haven't found he user or have an incorrect pin
        return null;
    }
}
