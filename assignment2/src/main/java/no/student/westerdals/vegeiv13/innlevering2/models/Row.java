package no.student.westerdals.vegeiv13.innlevering2.models;

import java.util.TreeMap;

public class Row extends TreeMap<Column, Object> {

    public String toInsertStatement(String tableName) {
        String baseStatement = "INSERT INTO " + tableName + " (?) VALUES (!);";
        Column lastColumn = lastKey();
        StringBuilder namesBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        keySet().forEach(column -> {
            namesBuilder.append(column.getName());
            valuesBuilder.append("'").append(this.get(column).toString()).append("'");
            if(!column.equals(lastColumn)) {
                namesBuilder.append(", ");
                valuesBuilder.append(", ");
            }
        });
        baseStatement = baseStatement.replace("?", namesBuilder.toString());
        baseStatement = baseStatement.replace("!", valuesBuilder.toString());

        return baseStatement;
    }

}
