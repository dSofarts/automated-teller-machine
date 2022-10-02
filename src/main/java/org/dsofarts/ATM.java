package org.dsofarts;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        // init scanner
        Scanner scanner = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Chester");

        // add a user
        User aUser = theBank.addUser("Dmitry", "Vladimirovich", "Komarov", "1224");

        // add a checking account for our user
        Account newAccountA = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccountA);
        theBank.addAccount(newAccountA);

        User curUser;
        while (true) {

            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, scanner);

            // stay in mein menu until user quits
            ATM.printUserMenu(curUser, scanner);


        }
    }

    /**
     * Print the ATM's login menu
     * @param theBank
     * @param scanner
     * @return
     */
    public static User mainMenuPrompt(Bank theBank, Scanner scanner) {

        // inits
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter PIN: ");
            pin = scanner.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID or PIN. Please try again");
            }

        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scanner) {

        // print a summary of the users accounts
        theUser.printAccountsSummary();

        // init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println("\n" +
                    "\t1) Show account transaction history\n" +
                    "\t2) Withdrawl\n" +
                    "\t3) Deposit\n" +
                    "\t4) Transfer\n" +
                    "\t5) Quit\n");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, scanner);
                break;
            case 2:
                ATM.withdrawFunds(theUser, scanner);
                break;
            case 3:
                ATM.depositFunds(theUser, scanner);
                break;
            case 4:
                ATM.transferFunds(theUser, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, scanner);
        }
    }

    /**
     * Show the transaction history for an account
     * @param theUser
     * @param scanner
     */
    public static void showTransactionHistory(User theUser, Scanner scanner) {
        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) of the account " +
                    "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = scanner.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print transaction history
        theUser.printAccountTransactionHistory(theAcct);
    }

    /**
     * Transfer between your accounts
     * @param theUser
     * @param scanner
     */
    public static void transferFunds(User theUser, Scanner scanner) {

        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double accountBalance;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\nto transfer from: ", theUser.numAccounts());
            fromAcct = scanner.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\nto transfer to: ", theUser.numAccounts());
            toAcct = scanner.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", accountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Amount must not be greater than balance of $%.02f\n", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        // do the transfer
        theUser.addAccountTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to accounts %s",
                theUser.getAccountUUID(toAcct)));
        theUser.addAccountTransaction(toAcct, amount, String.format(
                "Transfer to accounts %s",
                theUser.getAccountUUID(fromAcct)));
    }

    /**
     * Process a find withdraw from an account
     * @param theUser
     * @param scanner
     */
    public static void withdrawFunds(User theUser, Scanner scanner) {

        // inits
        int fromAcct;
        double amount;
        double accountBalance;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\nto withdraw from: ", theUser.numAccounts());
            fromAcct = scanner.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        accountBalance = theUser.getAccountBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", accountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Amount must not be greater than balance of $%.02f\n", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        // get a memo
        scanner.nextLine();
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        // do the withdraw
        theUser.addAccountTransaction(fromAcct, -1 * amount, memo);
    }

    /**
     * Process a fund deposit to an account
     * @param theUser
     * @param scanner
     */
    public static void depositFunds(User theUser, Scanner scanner) {

        // inits
        int toAcct;
        double amount;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\nto withdraw from: ", theUser.numAccounts());
            toAcct = scanner.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.print("Enter the amount to deposit: $");
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0);

        // get a memo
        scanner.nextLine();
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        // do the withdraw
        theUser.addAccountTransaction(toAcct, amount, memo);
    }
}
