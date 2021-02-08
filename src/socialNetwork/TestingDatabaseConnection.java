package socialNetwork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestingDatabaseConnection {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main() {
        String query = "select name, surname from users where name like '%ов%' OR surname like '%ов%'";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String count = rs.getString("name") + " " + rs.getString("surname");
                System.out.println(count);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}
