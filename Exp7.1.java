

// 1. **Setup MySQL Database**  
//    - Ensure MySQL is installed and running.  
//    - Create a database and an `Employee` table with columns `EmpID`, `Name`, and `Salary`.

// 2. **Update Database Credentials**  
//    - Replace `your_database`, `your_username`, and `your_password` in the code with actual database credentials.

// 3. **Add MySQL JDBC Driver**  
//    - Download and add `mysql-connector-java.jar` to your project’s classpath.

// 4. **Compile and Run the Program**  
//    - Compile: `javac MySQLConnection.java`  
//    - Run: `java MySQLConnection`

// 5. **Verify Output**  
//    - Ensure that employee records are displayed correctly from the database.

IN MY SQL:

CREATE DATABASE EmployeeDB;
USE EmployeeDB;
CREATE TABLE Employee ( EmpID INT PRIMARY KEY
AUTO_INCREMENT, Name VARCHAR(100) NOT NULL,
Salary DECIMAL(10,2) NOT NULL
);
INSERT INTO Employee (Name, Salary) VALUES ('PRINCE', 50000.00),
('SKY', 60000.00),
('SUKHU', 70000.00);

SELECT * FROM Employee;


IN IDE

package src; import java.sql.*;

public class EmployeeDB {
public static void main(String[] args) {
String url = "jdbc:mysql://localhost:3306/EmployeeDB"; String user = "root";
String password = "50125"; try {

Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection(url, user, password); System.out.println("Connected to MySQL!");

String query = "SELECT * FROM Employee"; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query); System.out.println("Employee Data:"); while (rs.next())

{
int empID = rs.getInt("EmpID"); String name = rs.getString("Name");
double salary = rs.getDouble("Salary");
 
COMPUTER SCIENCE AND ENGINEERING


System.out.println(empID + " | " + name + " | $" + salary);
}
rs.close();
stmt.close();
conn.close(); System.out.println("Connection closed.");
} catch (Exception e) { e.printStackTrace();
}
}
}



