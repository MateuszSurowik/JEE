/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1indywidual;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author student
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class IND_DbManager {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:./mateuszsurowik_database/mateuszsurowik_db";
    public static final String QUERY = "select * from app.Lista";

    private static java.sql.Connection conn;



 public static boolean insertData(String Name, int AlbumNumber) {
    try {
        // Check if the connection is initialized
        if (conn == null) {
            System.err.println("Connection is not initialized. Please call Connect() before inserting data.");
            return false;
        }

        // Check if AlbumNumber already exists in the database
        if (isAlbumNumberExists(AlbumNumber)) {
            System.out.println("Album number already exists in the database.");
            return false;
        }

        Statement stat = conn.createStatement();

        // Get the next available ID
        int nextId = getNextId(stat);

        // Insert data with the next available ID
        String insertQuery = "INSERT INTO app.Lista (id, Name, Album_Number) VALUES (" + nextId + ", '" + Name + "', " + AlbumNumber + ")";

        int rowsAffected = stat.executeUpdate(insertQuery);

        if (stat != null) {
            stat.close();
        }

        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Method to check if AlbumNumber exists in the database
private static boolean isAlbumNumberExists(int AlbumNumber) {
    try {
        Statement stat = conn.createStatement();
        String query = "SELECT Album_Number FROM app.Lista WHERE Album_Number = " + AlbumNumber;
        ResultSet resultSet = stat.executeQuery(query);

        // If any rows are returned, the AlbumNumber already exists
        boolean exists = resultSet.next();

        resultSet.close();
        stat.close();

        return exists;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

 private static int getNextId(Statement stat) throws SQLException {
    String getIdQuery = "SELECT MAX(id) + 1 FROM app.Lista";
    ResultSet idResultSet = stat.executeQuery(getIdQuery);
    int nextId = 1; // Default to 1 if no records exist yet

    if (idResultSet.next()) {
        nextId = idResultSet.getInt(1);
    }

    return nextId;
}

     // Method to delete a record by ID
    public static boolean deleteRecordByID(int recordID) {
        // SQL query to delete record by ID
        String deleteQuery = "DELETE FROM app.Lista WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery)) {

            // Set the ID parameter in the query
            preparedStatement.setInt(1, recordID);

            // Execute the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the deletion was successful based on the number of affected rows
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
            return false; // Return false to indicate failure
        }
    }
    public static boolean updateRecord(int recordID, String newName, int newAlbumNumber) {
        // SQL query to update record by ID
        String updateQuery = "UPDATE app.Lista SET name = ?, album_Number = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {

            // Set new values for name and albumNumber
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAlbumNumber);

            // Set the ID of the record to update
            preparedStatement.setInt(3, recordID);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful based on the number of affected rows
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
            return false; // Return false to indicate failure
        }
    }

    private IND_DbManager() {
    }

    public static boolean Connect() throws ClassNotFoundException, SQLException {
        conn = java.sql.DriverManager.getConnection(JDBC_URL);
        return conn != null;
    }

   public static boolean Disconnect() {
    try {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            return true;
        }
    } catch (SQLException e) {
    }
    return false;
}

    public static String getData() throws SQLException {
        java.sql.Statement stat = conn.createStatement();
        java.sql.ResultSet rs = stat.executeQuery(QUERY);
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        StringBuilder wiersz = new StringBuilder();
        int colCount = rsmd.getColumnCount();

        for (int i = 1; i <= colCount; i++) {
            wiersz.append(rsmd.getColumnName(i)).append(" \t| ");
        }

        wiersz.append("\r\n");

        while (rs.next()) {
            System.out.println("");
            for (int i = 1; i <= colCount; i++) {
                wiersz.append(rs.getString(i)).append(" \t| ");
            }
            wiersz.append("\r\n");
        }

        if (stat != null) {
            stat.close();
        }

        return wiersz.toString();
    }
 private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
     // Use JOptionPane to get user input
    String name = JOptionPane.showInputDialog("Enter name:");
    String albumNumberStr = JOptionPane.showInputDialog("Enter album number:");

    // Check if the user canceled the input
    if (name != null && albumNumberStr != null) {
        try {
            // Parse album number to an integer
            int albumNumber = Integer.parseInt(albumNumberStr);

            // Call insertData method to add data to the database
            boolean success = IND_DbManager.insertData(name, albumNumber);

            // Display appropriate message based on the success of the operation
            if (success) {
                jTextArea1.append("\nData added!");
            } else {
                jTextArea1.append("\nFailed to add data.");
            }
        } catch (NumberFormatException ex) {
            jTextArea1.append("\nError: Invalid album number format.");
        }
    }
}
 


    public static void main(String[] args) {
        try {
            if (Connect()) {
                System.out.println("Connected");
                System.out.println(getData());
                Disconnect();
                System.out.println("Disconnected");
            } else {
                System.out.println("Failed to connect");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
