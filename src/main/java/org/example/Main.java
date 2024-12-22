package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) {

        UserManagement userManagement = new UserManagement();

        userManagement.login();
        userManagement.registerUser();


        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            // Display menu options
            System.out.println("======= Bank System Menu =======");
            System.out.println("1. Check Balance");
            System.out.println("2. Add a Transaction");
            System.out.println("3. Delete a Transaction");
            System.out.println("4. Search Balance");
            System.out.println("5. Exit");
            System.out.println("Please select an option (1-5):");

            // Read user input
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Handle menu options
            switch (choice) {
                case 1:
                    //checkBalance();
                    break;
                case 2:
                    //addTransaction();
                    break;
                case 3:
                    //deleteTransaction();
                    break;
                case 4:
                    //searchBalance();
                    break;
                case 5:
                    System.out.println("Exiting the program. Thank you!");
                    running = false; // Exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 5.");
            }
        }
        scanner.close();
    }


}