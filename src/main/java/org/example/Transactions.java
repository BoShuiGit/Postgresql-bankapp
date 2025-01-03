
package org.example;

import java.sql.*;
import java.util.Scanner;

public class Transactions {

    public static void addTransaction() {

        int userId = UserManagement.getLoggedInUserId();

        if (userId == -1) {
            System.out.println("You need to log in first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter transaction type (Deposit/Expense):");
        String transactionType = scanner.nextLine().trim();


        if (!transactionType.equalsIgnoreCase("Deposit") && !transactionType.equalsIgnoreCase("Expense")) {
            System.out.println("Invalid transaction type. Only 'Deposit' or 'Expense' are allowed.");
            return;
        }

        System.out.println("Enter transaction amount:");
        int transactionAmount = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter transaction description:");
        String description = scanner.nextLine();


        String insertQuery = "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_description) VALUES (?, ?, ?, ?)";

        try (Connection connection = UserManagement.connectDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, userId);  // Use logged-in user_id
            preparedStatement.setString(2, transactionType);
            preparedStatement.setInt(3, transactionAmount);
            preparedStatement.setString(4, description);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Transaction added successfully.");
            } else {
                System.out.println("Failed to add transaction.");
            }

        } catch (SQLException e) {
            System.out.println("Error while adding transaction: " + e.getMessage());
        }


    }


    public static boolean deletTransaction () {

        return true;
    }
}







/*
package org.example;

import java.sql.*;
import java.util.Scanner;

public class Transactions {

    public static void addTransaction (){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your user ID:");
            int userId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.println("Enter transaction type (Deposit/Expense):");
            String transactionType = scanner.nextLine().trim();


            if (!transactionType.equalsIgnoreCase("Deposit") && !transactionType.equalsIgnoreCase("Expense")) {
                System.out.println("Invalid transaction type. Only 'Deposit' or 'Expense' are allowed.");
                return;
            }

            System.out.println("Enter transaction amount:");
            int transactionAmount = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter transaction description:");
            String description = scanner.nextLine();


            String insertQuery = "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_description) VALUES (?, ?, ?, ?)";

            try (Connection connection = UserManagement.connectDataBase();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, transactionType);
                preparedStatement.setInt(3, transactionAmount);
                preparedStatement.setString(4, description);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Transaction added successfully.");
                } else {
                    System.out.println("Failed to add transaction.");
                }

            } catch (SQLException e) {
                System.out.println("Error while adding transaction: " + e.getMessage());
            }


    }
}
*/