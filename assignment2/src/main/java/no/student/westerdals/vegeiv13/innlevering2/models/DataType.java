package no.student.westerdals.vegeiv13.innlevering2.models;

import java.sql.Types;

public enum DataType {
    VARCHAR, INT, DATETIME, BIGINT;

    public int asInt() {
        switch(this) {
            case VARCHAR: return Types.VARCHAR;
            case INT: return Types.INTEGER;
            case DATETIME: return Types.TIMESTAMP;
            case BIGINT: return Types.BIGINT;
        }
        throw new UnsupportedOperationException("Unsupported data type");
    }

    public static DataType getTypeForString(final String type) {
        switch(type.toUpperCase()) {
            case "STRING":
            case "VARCHAR": return VARCHAR;
            case "INT":
            case "INTEGER": return INT;
            case "DATETIME": return DATETIME;
            case "LONG":
            case "BIGINT": return BIGINT;
        }
        throw new UnsupportedOperationException("Unable to find data type for: " + type);
    }
}
