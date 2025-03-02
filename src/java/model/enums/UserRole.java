package model.enums;

public enum UserRole {
    MANAGER("Manager"),
    MEMBER("Member");

    private final String displayValue;

    UserRole(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static UserRole fromString(String text) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(text) ||
                    role.getDisplayValue().equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
