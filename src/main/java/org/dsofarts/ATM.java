package org.dsofarts;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        // init scanner
        Scanner scanner = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Chester");

        // add a user
        User aUser = theBank.addUser("Дмитрий", "Владимирович", "Комаров", "1224");

        // add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

    }
}
