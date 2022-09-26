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
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

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
            System.out.println("" +
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
                ATM.showTransHistory(theUser, scanner);
                break;
            case 2:
                ATM.widthdrawlFunds(theUser, scanner);
                break;
            case 3:
                ATM.depositFunds(theUser, scanner);
            case 4:

        }
    }
}
