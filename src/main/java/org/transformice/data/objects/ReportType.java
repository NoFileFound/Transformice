package org.transformice.data.objects;

public enum ReportType {
    HACK(0),
    SPAM(1),
    INSULTS(2),
    PHISHING(3),
    OTHER(4);

    private final int value;
    ReportType(int value) {
        this.value = value;
    }

    public int getId() {
        return this.value;
    }

    public static ReportType fromValue(int x) {
        for (ReportType type : values()) {
            if (type.value == x)
                return type;
        }

        return OTHER;
    }
}