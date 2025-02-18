package utils;

import java.text.SimpleDateFormat;

public class Validation {

    // Common validations

    public static boolean isValidName(String firstName, String lastName) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }

        if (firstName.length() < 2 || lastName.length() < 2) {
            System.out.println("Name must be at least 2 characters long.");
            return false;
        }

        if (firstName.matches(".*[0-9!@#$%^&*(){}_+\\-=*/.<>?|\\s].*")
                || lastName.matches(".*[0-9!@#$%^&*(){}_+\\-=*/.<>?|\\s].*")) {
            System.out.println("Name cannot contain numbers, special characters, or spaces.");
            return false;
        }

        return true;
    }

    public static boolean isValidUsername(String username) {
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }

        if (checkSpace(username)) {
            System.out.println("Username cannot contain spaces.");
            return false;
        }

        if (username.length() < 6 || username.length() > 20) {
            System.out.println("Username must be between 6 and 20 characters.");
            return false;
        }
        if (username.matches(".*[0-9!@#$%^&*(){}_+\\-=*/.<>?|\\s].*")) {
            System.out.println("Username cannot contain numbers, special characters, or spaces.");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            System.out.println("Email cannot be empty.");
            return false;
        }

        if (checkSpace(email)) {
            System.out.println("Email cannot contain spaces.");
            return false;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("Invalid email format.");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password) {
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }
        if (password.length() < 8 || password.length() > 20) {
            System.out.println("Password must be between 8 and 20 characters.");
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$")) {
            System.out.println(
                    "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.");
            return false;
        }
        return true;
    }

    public static boolean isValidId(int id) {
        try {
            if (String.valueOf(id).isEmpty()) {
                System.out.println("Product ID cannot be empty.");
                return false;
            }
            if (id <= 0) {
                System.out.println("Product ID must be greater than 0.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Product ID must be a positive integer.");
            return false;
        }
    }

    public static boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid date format. Use YYY-MM-DD");
            return false;
        }
    }

    public static boolean isValidPhone(String phone) {
        if (phone.isEmpty()) {
            System.out.println("Phone cannot be empty.");
            return false;
        }

        if (checkSpace(phone)) {
            System.out.println("Phone number cannot contain spaces.");
            return false;
        }

        if (!phone.matches("^\\d{10}$")) {
            System.out.println("Phone number must be 10 digits long.");
            return false;
        }

        return true;
    }

    public static boolean isValidRole(String role) {
        if (role.isEmpty()) {
            System.out.println("Role cannot be empty.");
            return false;
        }

        if (!role.equals("Manager") && !role.equals("Member")) {
            System.out.println("Invalid role. Must be either 'Manager' or 'Member'.");
            return false;
        }

        return true;
    }

    public static boolean checkSpace(String str) {
        if (str.contains(" ")) {
            System.out.println("String cannot contain spaces.");
            return false;
        }
        return true;
    }
}