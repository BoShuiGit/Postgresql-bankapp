

package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserManagement userManagement = new UserManagement();


        Scanner scanner = new Scanner(System.in);
        boolean isUserLoggedIn = false;

        //METOD LOGIN
        while (!isUserLoggedIn) {

            System.out.println("======= Welcome to the Bank System =======");
            System.out.println("1. Login");
            System.out.println("2. Register a new account");
            System.out.println("Please select an option (1-2):");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    if (userManagement.login()) {
                        isUserLoggedIn = true;
                        System.out.println("Login successful. Welcome back!");
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;

                case 2:

                    if (userManagement.registerUser()) {
                        isUserLoggedIn = true;
                        System.out.println("Registration successful. Welcome aboard!");
                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please select either 1 or 2.");
            }
        }


        boolean running = true;
        while (running) {

            System.out.println("======= Bank System Menu =======");
            System.out.println("1. Add a Transaction");
            System.out.println("2. Delete a Transaction");
            System.out.println("3. Search Balance");
            System.out.println("4. Exit");
            System.out.println("Please select an option (1-4):");


            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    Transactions.addTransaction();
                    break;

                case 2:

                    Transactions.deleteTransaction();
                    break;

                case 3:

                    Transactions.searchBalanceByDateRange();
                    break;

                case 4:
                    System.out.println("Exiting the program. Thank you!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 4.");
            }
        }
        scanner.close();
    }
}



