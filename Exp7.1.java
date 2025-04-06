import java.sql.*;

public class EmployeeManager {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CompanyDB"; // Your DB name
        String user = "root"; // Your username
        String password = "New@0001"; // Your password

        Connection conn = null;
        PreparedStatement insertStmt = null;
        Statement fetchStmt = null;
        ResultSet rs = null;

        try {
            // 1. Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish Connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");

            // 3. Insert Data
           // 3. Insert Data
String insertQuery = "INSERT INTO Employee (EmpID, Name, Salary) VALUES (?, ?, ?)";
insertStmt = conn.prepareStatement(insertQuery);

// New unique employees
insertStmt.setInt(1, 201);
insertStmt.setString(2, "Neha");
insertStmt.setDouble(3, 56000.00);
insertStmt.executeUpdate();

insertStmt.setInt(1, 202);
insertStmt.setString(2, "Ravi");
insertStmt.setDouble(3, 60000.00);
insertStmt.executeUpdate();

insertStmt.setInt(1, 203);
insertStmt.setString(2, "Anjali");
insertStmt.setDouble(3, 75000.00);
insertStmt.executeUpdate();

insertStmt.setInt(1, 204);
insertStmt.setString(2, "Vikram");
insertStmt.setDouble(3, 80000.00);
insertStmt.executeUpdate();

insertStmt.setInt(1, 205);
insertStmt.setString(2, "Tanvi");
insertStmt.setDouble(3, 68000.00);
insertStmt.executeUpdate();

System.out.println("Different values inserted successfully.");


            // 4. Fetch and Display Data
            fetchStmt = conn.createStatement();
            rs = fetchStmt.executeQuery("SELECT * FROM Employee");

            System.out.println("\nEmpID\tName\t\tSalary");
            System.out.println("-----\t----\t\t------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.printf("%d\t%-10s\t%.2f%n", id, name, salary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (fetchStmt != null) fetchStmt.close(); } catch (Exception e) {}
            try { if (insertStmt != null) insertStmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
Connected to database!
Different values inserted successfully.

EmpID   Name            Salary
-----   ----            ------
101     Alice           55000.00
102     Bob             62000.00
201     Neha            56000.00
202     Ravi            60000.00
203     Anjali          75000.00
204     Vikram          80000.00
