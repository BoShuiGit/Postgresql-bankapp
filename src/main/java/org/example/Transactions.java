


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

        // Correct column name: transaction_timestamp
        String insertQuery = "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_description, transaction_timestamp) " +
                "VALUES (?, ?, ?, ?, NOW())";

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

    /*
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

        String insertQuery = "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_description, transaction_date) VALUES (?, ?, ?, ?, CURRENT_DATE)";

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
 */

    public static void searchBalanceByDateRange() {
        int userId = UserManagement.getLoggedInUserId();

        if (userId == -1) {
            System.out.println("You need to log in first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start date (YYYY-MM-DD):");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date (YYYY-MM-DD):");
        String endDate = scanner.nextLine();

        String query = "SELECT transaction_type, transaction_amount, transaction_timestamp, transaction_description " +
                "FROM transactions " +
                "WHERE user_id = ? AND transaction_timestamp BETWEEN ? AND ? " +
                "ORDER BY transaction_timestamp ASC";

        try (Connection connection = UserManagement.connectDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, Date.valueOf(startDate));
            preparedStatement.setDate(3, Date.valueOf(endDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Transactions from " + startDate + " to " + endDate + ":");
            System.out.printf("%-15s %-10s %-25s %-30s%n", "Type", "Amount", "Timestamp", "Description");
            System.out.println("-------------------------------------------------------------------------------");

            boolean hasTransactions = false;

            while (resultSet.next()) {
                hasTransactions = true;
                String type = resultSet.getString("transaction_type");
                int amount = resultSet.getInt("transaction_amount");
                Timestamp timestamp = resultSet.getTimestamp("transaction_timestamp");
                String description = resultSet.getString("transaction_description");

                System.out.printf("%-15s %-10d %-25s %-30s%n",
                        type, amount, timestamp.toString(), description);
            }

            if (!hasTransactions) {
                System.out.println("No transactions found in the given period.");
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching transactions: " + e.getMessage());
        }
    }





    public static void deleteTransaction() {
        int userId = UserManagement.getLoggedInUserId();

        if (userId == -1) {
            System.out.println("You need to log in first.");
            return;
        }

        // Step 1: Display all transactions for the user
        String fetchQuery = "SELECT transaction_id, transaction_type, transaction_amount, transaction_timestamp, transaction_description " +
                "FROM transactions " +
                "WHERE user_id = ? " +
                "ORDER BY transaction_timestamp ASC";

        try (Connection connection = UserManagement.connectDataBase();
             PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery)) {

            fetchStatement.setInt(1, userId);
            ResultSet resultSet = fetchStatement.executeQuery();

            System.out.println("Your Transactions:");
            System.out.printf("%-5s %-15s %-10s %-25s %-30s%n",
                    "ID", "Type", "Amount", "Timestamp", "Description");
            System.out.println("--------------------------------------------------------------------------------------");

            boolean hasTransactions = false;

            while (resultSet.next()) {
                hasTransactions = true;
                int id = resultSet.getInt("transaction_id");
                String type = resultSet.getString("transaction_type");
                int amount = resultSet.getInt("transaction_amount");
                Timestamp timestamp = resultSet.getTimestamp("transaction_timestamp");
                String description = resultSet.getString("transaction_description");

                System.out.printf("%-5d %-15s %-10d %-25s %-30s%n",
                        id, type, amount, timestamp.toString(), description);
            }

            if (!hasTransactions) {
                System.out.println("No transactions found.");
                return;
            }

            // Step 2: User selects a transaction to delete
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the ID of the transaction you want to delete:");
            int transactionId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Step 3: Confirm deletion
            System.out.println("Are you sure you want to delete transaction ID " + transactionId + "? (yes/no)");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Transaction deletion cancelled.");
                return;
            }

            // Step 4: Delete the selected transaction
            String deleteQuery = "DELETE FROM transactions WHERE transaction_id = ? AND user_id = ?";

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, transactionId);
                deleteStatement.setInt(2, userId);

                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Transaction deleted successfully.");
                } else {
                    System.out.println("Failed to delete transaction. Please check the transaction ID.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while deleting transaction: " + e.getMessage());
        }
    }

}










