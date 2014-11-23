package no.student.westerdals.vegeiv13.innlevering2.models;

public enum DataType {
    VARCHAR, INT, DATETIME, BIGINT;

    public static DataType getTypeForString(final String type) {
        switch (type.toUpperCase()) {
            case "STRING":
            case "VARCHAR":
                return VARCHAR;
            case "INT":
            case "INTEGER":
                return INT;
            case "DATETIME":
                return DATETIME;
            case "LONG":
            case "BIGINT":
                return BIGINT;
        }
        throw new UnsupportedOperationException("Unable to find data type for: " + type);
    }
}
