package no.student.westerdals.vegeiv13.innlevering2.util;

import no.student.westerdals.vegeiv13.innlevering2.models.Column;
import no.student.westerdals.vegeiv13.innlevering2.models.DataType;
import no.student.westerdals.vegeiv13.innlevering2.models.Row;
import no.student.westerdals.vegeiv13.innlevering2.models.Table;
import org.apache.commons.lang.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBHandler {

    public static final String SEPARATOR = "/";
    private Connection connection;

    public DBHandler(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }

    public void copyFile(String fileName, String tableName) throws SQLException, IOException {
        File file = new File(fileName);
        String[] lines = readFile(file);
        List<Column> columns = readColumns(lines);
        lines = (String[]) ArrayUtils.subarray(lines, 3, lines.length);
        List<Row> rows = readValues(columns, lines);
        Table table = new Table(tableName, columns);
        try (Statement statement = connection.createStatement()) {
            statement.execute(table.toCreateTableStatement());
            for (Row row : rows) {
                statement.execute(row.toInsertStatement(table.getName()));
            }
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private String[] readFile(final File f) throws IOException {
        String[] lines = new String[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines = (String[]) ArrayUtils.add(lines, line);
            }
        } catch (IOException e) {
            throw new IOException("Cannot read file " + f.getName());
        }
        return lines;
    }

    private List<Column> readColumns(String[] lines) {
        List<Column> columns = new ArrayList<>();

        String[] columnNames = lines[0].split(SEPARATOR);
        List<DataType> dataTypes = Arrays.asList(lines[1].split(SEPARATOR)).stream().map(DataType::getTypeForString).collect(Collectors.toList());
        List<Integer> columnWidths = new ArrayList<>();
        Arrays.asList(lines[2].split(SEPARATOR)).forEach(width -> columnWidths.add(Integer.parseInt(width)));

        for (int i = 0; i < columnNames.length; i++) {
            DataType type = dataTypes.get(i);
            String columnName = columnNames[i];
            Integer columnWidth = columnWidths.get(i);
            columns.add(new Column(type, i, columnWidth, columnName));
        }

        return columns;
    }

    private List<Row> readValues(List<Column> columns, String[] lines) {
        List<Row> rows = new ArrayList<>(lines.length);
        for (String line : lines) {
            Row row = new Row();
            String[] values = line.split(SEPARATOR);
            for (int i = 0; i < values.length; i++) {
                Column column = columns.get(i);
                String data = values[i];
                row.put(column, data);
            }
            rows.add(row);
        }
        return rows;
    }

    public Table fetchTable(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT * FROM " + tableName);
            try (ResultSet resultSet = stmt.getResultSet()) {
                return Table.mapFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new SQLException("Unable to get table " + tableName + ". Does it exist?");
        }
    }

}
