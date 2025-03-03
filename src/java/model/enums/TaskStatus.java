package model.enums;

public enum TaskStatus {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TaskStatus fromString(String text) {
        if (text == null) {
            return null;
        }

        String normalizedText = text.toUpperCase().replace(" ", "_");

        for (TaskStatus status : TaskStatus.values()) {
            if (status.name().equals(normalizedText) ||
                    status.getValue().equals(normalizedText)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    @Override
    public String toString() {
        return this.value;
    }
}
