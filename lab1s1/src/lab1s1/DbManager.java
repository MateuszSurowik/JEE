/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1s1;

import java.sql.DriverManager;
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

public final class DbManager {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String JDBC_URL = "jdbc:derby:./db/ludzie";
    public static final String QUERY = "select * from app.ludzie";

    private static java.sql.Connection conn;

 

 public static boolean insertData(String fName, String lastName) {
    try {
        // Check if the connection is initialized
        if (conn == null) {
            // You may want to handle this situation differently based on your application requirements
            System.err.println("Connection is not initialized. Please call Connect() before inserting data.");
            return false;
        }

        Statement stat = conn.createStatement();

        // Get the next available ID
        int nextId = getNextId(stat);

        // Insert data with the next available ID
        String insertQuery = "INSERT INTO app.ludzie (id, first_name, last_name) VALUES (" + nextId + ", '" + fName + "', '" + lastName + "')";

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
 private static int getNextId(Statement stat) throws SQLException {
    String getIdQuery = "SELECT MAX(id) + 1 FROM app.ludzie";
    ResultSet idResultSet = stat.executeQuery(getIdQuery);
    int nextId = 1; // Default to 1 if no records exist yet

    if (idResultSet.next()) {
        nextId = idResultSet.getInt(1);
    }

    return nextId;
}

    private DbManager() {
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
    try {
        // Connect to the database
        if (DbManager.Connect()) {
            // Use JOptionPane to get user input
            String fName = JOptionPane.showInputDialog("Enter first name:");
            String lName = JOptionPane.showInputDialog("Enter last name:");

            // Check if the user canceled the input
            if (fName != null && lName != null) {
                // Parse age to an integer
                String lastName = lName;

                // Call insertData method to add data to the database
                boolean success = DbManager.insertData(fName, lastName);

                // Display appropriate message based on the success of the operation
                if (success) {
                    jTextArea1.append("\nDane dodane!");
                } else {
                    jTextArea1.append("\nNie udało się dodać danych.");
                }
            }

            // Disconnect from the database
            DbManager.Disconnect();
        } 
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
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
