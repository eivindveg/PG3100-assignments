package no.student.westerdals.vegeiv13.innlevering2.models;

import java.util.TreeMap;

public class Row extends TreeMap<Column, Object> {

    public String toInsertStatement(final String tableName, Object[] dataValues) {
        String baseStatement = "INSERT INTO " + tableName + " (!) VALUES (#);";
        final Column lastColumn = lastKey();
        final StringBuilder namesBuilder = new StringBuilder();
        final StringBuilder valuesBuilder = new StringBuilder();

        int i = 0;
        for(Column key : keySet()) {
            dataValues[i] = get(key);
            valuesBuilder.append("?");
            if(!key.equals(lastColumn)) {
                namesBuilder.append(", ");
                valuesBuilder.append(", ");
            }
        }

        baseStatement = baseStatement.replace("!", namesBuilder.toString());
        baseStatement = baseStatement.replace("#", valuesBuilder.toString());

        return baseStatement;
    }

}
