
package org.example;

import java.sql.*;
import java.util.Scanner;

public class UserManagement {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;


    private static int loggedInUserId = -1;

    // METOD LOGIN
    public static boolean login() {

        connectDataBase();

        Scanner sharp = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = sharp.nextLine();
        System.out.println("Enter your password:");
        String password = sharp.nextLine();


        String queryString = "SELECT * FROM users WHERE user_name = ?";

        try {
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");  // Get the plain text password from DB
                if (password.equals(storedPassword)) {  // Compare the entered password with the stored password
                    System.out.println("Welcome to your personal account.");

                    // loggedInUserId
                    loggedInUserId = resultSet.getInt("user_id");

                    return true;
                } else {
                    System.out.println("Invalid password.");
                    return false;
                }
            } else {
                System.out.println("Invalid username.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during login.");
            throw new RuntimeException(e);
        }
    }

    //METOD REGISTER
    public static boolean registerUser() {

        connectDataBase();
        Scanner sharp = new Scanner(System.in);

        System.out.println("Please enter your login name.");
        String loginName = sharp.nextLine();
        System.out.println("please enter your password.(max 5 letter.");
        String passwordCreation = sharp.nextLine();
        System.out.println("Enter your email.");
        String email = sharp.nextLine();

        String insertQuery = "INSERT INTO users (user_name, user_email,password) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, loginName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, passwordCreation);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
                return true;
            } else {
                System.out.println("Registration failed.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during registration.");
            throw new RuntimeException(e);
        }
    }

    // METOD GETLOGGEDIN USERID FOR FUTURE
    public static int getLoggedInUserId() {
        return loggedInUserId;
    }


    // METOD CONNECTDATABASE (MOVE OUT AS A INTERFACE)
    public static Connection connectDataBase() {
        String connectionString = "jdbc:postgresql://ep-misty-mountain-a2495khy.eu-central-1.aws.neon.tech/BoNeondb?user=BoNeondb_owner&password=bWHD4V2LiotY&sslmode=require";
        try {
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Connection to database successful.");
        } catch (SQLException e) {
            System.out.println("Connection error. Contact system admin.");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }
}
























