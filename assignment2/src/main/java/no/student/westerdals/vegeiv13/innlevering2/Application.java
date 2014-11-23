package no.student.westerdals.vegeiv13.innlevering2;

import no.student.westerdals.vegeiv13.innlevering2.models.Table;
import no.student.westerdals.vegeiv13.innlevering2.util.ConnectionHandler;
import no.student.westerdals.vegeiv13.innlevering2.util.DBHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try(ConnectionHandler connectionHandler = new ConnectionHandler()) {
            try(Connection connection = connectionHandler.getConnection()) {
                DBHandler dbHandler = new DBHandler(connection);
                dbHandler.copyFile("tekstfil.txt", "vegeiv13");
                Table table = dbHandler.fetchTable("vegeiv13");
                System.out.println(table.describe());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Could not close connection");
        }
    }
}
