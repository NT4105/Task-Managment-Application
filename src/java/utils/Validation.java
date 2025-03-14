package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        // Allow letters, numbers, underscore, minimum 4 characters
        return username.matches("^[a-zA-Z0-9_]{4,20}$");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Basic email validation pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c))
                hasLetter = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                hasSpecial = true;

            if (hasLetter && hasDigit && hasSpecial)
                return true;
        }

        return false;
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
        if (date == null || date.isEmpty()) {
            return false;
        }

        try {
            java.sql.Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidPhone(String phone) {
        // Skip validation if phone is unchanged (null or empty)
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        // Remove any whitespace
        phone = phone.trim();

        // Check for exactly 10 digits
        if (!phone.matches("^\\d{10}$")) {
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

    public static boolean isValidTaskDate(Date taskDate) {
        if (taskDate == null) {
            System.out.println("Task date cannot be null");
            return false;
        }

        // Check if date is not in the past
        Date today = new Date(System.currentTimeMillis());
        if (taskDate.before(today)) {
            System.out.println("Task date cannot be in the past");
            return false;
        }

        // Check if date is within 7 days
        long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000;
        Date sevenDaysFromNow = new Date(today.getTime() + sevenDaysInMillis);
        if (taskDate.after(sevenDaysFromNow)) {
            System.out.println("Task due date must be within 7 days");
            return false;
        }

        return true;
    }

    public static boolean isValidAssignees(List<String> assignees) {
        if (assignees == null || assignees.isEmpty()) {
            System.out.println("Task must have at least one assignee");
            return false;
        }
        return true;
    }

    public static boolean isValidTaskName(String taskName) {
        if (taskName == null || taskName.isEmpty()) {
            System.out.println("Task name cannot be empty");
            return false;
        }

        if (taskName.length() < 2 || taskName.length() > 100) {
            System.out.println("Task name must be between 2 and 100 characters");
            return false;
        }
        return true;
    }

    public static boolean isValidTaskDueDate(String dueDate) {
        if (dueDate == null || dueDate.isEmpty()) {
            System.out.println("Due date cannot be empty");
            return false;
        }

        if (!isValidDate(dueDate)) {
            System.out.println("Invalid date format. Use YYYY-MM-DD");
            return false;
        }

        if (!isValidTaskDate(new Date(dueDate))) {
            System.out.println("Due date must be within 7 days");
            return false;
        }

        return true;
    }
}