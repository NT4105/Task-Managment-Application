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
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Basic email validation pattern
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailPattern);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // Require at least one digit and one special character
        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasDigit && hasSpecial;
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