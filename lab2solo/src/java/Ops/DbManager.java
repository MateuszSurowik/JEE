/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sewerlet.wypisywanie;

/**
 *
 * @author student
 */
public final class DbManager {
public static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
public static final String JDBC_URL = "jdbc:derby://localhost:1527//home/student/NetBeansProjects/Lab2solo/db/database";
public static final String QUERY = "select * from app.Data";
public static final String QUERY2 = "select * from app.log";
private static java.sql.Connection conn;
private DbManager() {
}
public static boolean Connect() throws ClassNotFoundException, SQLException {
conn = DriverManager.getConnection(JDBC_URL);
if (conn == null) {
return false;
} else {
return true;
} }
public static boolean Disconnect() throws SQLException {
if (conn == null) {
return false;
} else {
conn.close();
return true;
} }
public static String getData() throws SQLException {
Statement stat = conn.createStatement();
ResultSet rs = stat.executeQuery(QUERY);
ResultSetMetaData rsmd = rs.getMetaData();
String wiersz = new String();
int colCount = rsmd.getColumnCount();
wiersz = wiersz.concat("<table><tr>");
for (int i = 1; i <= colCount; i++) {
wiersz = wiersz.concat(" <td><b> " + rsmd.getColumnName(i) + "</b></td> ");
}
wiersz = wiersz.concat("</tr>");
while (rs.next()) {
wiersz = wiersz.concat("<tr>");
for (int i = 1; i <= colCount; i++) {
wiersz = wiersz.concat(" <td> " + rs.getString(i) + " </td> ");
}
wiersz = wiersz.concat("</tr>");
}
wiersz = wiersz.concat("</table>");
return wiersz;
} 
public static String getLogData() throws SQLException {
Statement stat = conn.createStatement();
ResultSet rs = stat.executeQuery(QUERY2);
ResultSetMetaData rsmd = rs.getMetaData();
String wiersz = new String();
int colCount = rsmd.getColumnCount();
wiersz = wiersz.concat("<table><tr>");
for (int i = 1; i <= colCount; i++) {
wiersz = wiersz.concat(" <td><b> " + rsmd.getColumnName(i) + "</b></td> ");
}
wiersz = wiersz.concat("</tr>");
while (rs.next()) {
wiersz = wiersz.concat("<tr>");
for (int i = 1; i <= colCount; i++) {
wiersz = wiersz.concat(" <td> " + rs.getString(i) + " </td> ");
}
wiersz = wiersz.concat("</tr>");
}
wiersz = wiersz.concat("</table>");
return wiersz;
} 
public static boolean addData(String name, String surname) throws SQLException {
    String insertQuery = "INSERT INTO app.Data (id, name, surname) VALUES (?, ?, ?)";
    String logInsertQuery = "INSERT INTO app.log (actionType, actionDescription) VALUES (?, ?)";
    boolean addedSuccessfully = false;

    try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
        conn.setAutoCommit(false);

        // Pobieranie najwyższego ID z bazy danych
        String getMaxIdQuery = "SELECT MAX(id) FROM app.Data";
        int nextId = 1; // Domyślne ID, jeśli tabela jest pusta

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next()) {
                // Pobieranie najwyższego ID i zwiększenie o 1
                nextId = rs.getInt(1) + 1;
            }
        }

        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery);
             PreparedStatement logStmt = conn.prepareStatement(logInsertQuery)) {
            pstmt.setInt(1, nextId); // Ustawienie kolejnego ID
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            int rowsAffected = pstmt.executeUpdate();

            // Dodanie informacji do logów
            logStmt.setString(1, "Insert");
            logStmt.setString(2, "Dane zostały dodane: " + name + " " + surname);
            logStmt.executeUpdate();

            conn.commit();
            addedSuccessfully = rowsAffected > 0;
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
    }

    return addedSuccessfully;
}

public static boolean editData(int id, String newName, String newSurname) throws SQLException {
    String updateQuery = "UPDATE app.Data SET name = ?, surname = ? WHERE id = ?";
    String logUpdateQuery = "INSERT INTO app.log (actionType, actionDescription) VALUES (?, ?)";
    boolean updatedSuccessfully = false;

    try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
        conn.setAutoCommit(false);

        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery);
             PreparedStatement logStmt = conn.prepareStatement(logUpdateQuery)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newSurname);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();

            // Dodanie informacji do logów
            logStmt.setString(1, "Update");
            logStmt.setString(2, "Dane o ID " + id + " zostały zaktualizowane na: " + newName + " " + newSurname);
            logStmt.executeUpdate();

            conn.commit();
            updatedSuccessfully = rowsAffected > 0;
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
    }

    return updatedSuccessfully;
}

public static boolean deleteData(int id) throws SQLException {
    String deleteQuery = "DELETE FROM app.Data WHERE id = ?";
    String logDeleteQuery = "INSERT INTO app.log (actionType, actionDescription) VALUES (?, ?)";
    boolean deletedSuccessfully = false;

    try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
        conn.setAutoCommit(false);

        try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
             PreparedStatement logStmt = conn.prepareStatement(logDeleteQuery)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            // Dodanie informacji do logów
            logStmt.setString(1, "Delete");
            logStmt.setString(2, "Dane o ID " + id + " zostały usunięte");
            logStmt.executeUpdate();

            conn.commit();
            deletedSuccessfully = rowsAffected > 0;
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
    }

    return deletedSuccessfully;
}
}