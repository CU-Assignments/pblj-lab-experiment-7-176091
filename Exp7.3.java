// ### Instructions for Java JDBC MVC Student Management System  

// 1. **Setup MySQL Database:**  
//    - Install and start MySQL.  
//    - Create a database (e.g., `StudentDB`).  
//    - Create a table:  
//      ```sql
//      CREATE TABLE Student (
//          StudentID INT PRIMARY KEY,
//          Name VARCHAR(100),
//          Department VARCHAR(50),
//          Marks DOUBLE
//      );
//      ```

// 2. **Update Database Credentials:**  
//    - Modify `URL`, `USER`, and `PASSWORD` in the code to match your MySQL database credentials.

// 3. **Add MySQL JDBC Driver:**  
//    - Download and add `mysql-connector-java.jar` to your project's classpath.

// 4. **Compile and Run the Program:**  
//    - Compile: `javac StudentManagementApp.java`  
//    - Run: `java StudentManagementApp`  

// 5. **Menu-Driven Operations:**  
//    - **Add Student:** Enter StudentID, Name, Department, and Marks.  
//    - **View Students:** Displays all students in the table.  
//    - **Update Student:** Modify Name, Department, or Marks using StudentID.  
//    - **Delete Student:** Remove a student using StudentID.  
//    - **Exit:** Quit the program.

// 6. **Transaction Handling:**  
//    - Ensures data integrity by using `conn.setAutoCommit(false)` and `conn.commit()`.  
//    - Rolls back changes in case of errors.

// 7. **Verify Database Changes:**  
//    - Use `SELECT * FROM Student;` in MySQL to confirm modifications.



IN MY SQL:
CREATE DATABASE StudentDB;
USE StudentDB;
CREATE TABLE Student (
StudentID INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(100),
Department VARCHAR(50), Marks INT
);

IN IDE:

WE need Such Folder Directory

•	src/main/ → Contains the Main.java file, which starts the application.
•	src/model/ → Defines Java classes that map to database tables (like Student.java).
•	src/controller/ → Contains logic for handling CRUD operations.
•	src/view/ → Handles user input and output (menu-driven interface).
•	src/utils/ → Stores utility/helper classes like DBConnection.java for database connectivity.

Model.java:-> package model; public class Student {
private int studentID; private String name;
private String department; private int marks;
public Student(int studentID, String name, String department, int marks) { this.studentID = studentID;
this.name = name; this.department = department;
this.marks = marks;
}
public int getStudentID() { return studentID; }

public String getName() { return name; }
public String getDepartment() { return department;
} public int getMarks() { return marks; }
}

Student.java:

package model; public class Student {
private int studentID; private String name; private String department; private int marks;
public Student(int studentID, String name, String department, int marks) { this.studentID = studentID;
this.name = name; this.department = department; this.marks = marks;
}
public int getStudentID() { return studentID; } public String getName() { return name; }
public String getDepartment() { return department;
} public int getMarks() { return marks; }
}

StudentView.java

package view; import
controller.StudentController; import model.Student; import java.util.List;
import java.util.Scanner;
public class StudentView {
public static void main(String[] args) { Scanner scanner = new Scanner(System.in); while (true) {
System.out.println("\n===== Student Management System ====="); System.out.println("1. Add Student");
System.out.println("2. View Students"); System.out.println("3. Update Student"); System.out.println("4. Delete Student");
System.out.println("5. Exit"); System.out.print("Choose an option: ");
int choice =
scanner.nextInt(); scanner.nextLine(); switch (choice) {
case 1:
System.out.print("Enter Name: ");

String name = scanner.nextLine(); System.out.print("Enter Department:
"); String dept = scanner.nextLine(); System.out.print("Enter Marks: "); int marks = scanner.nextInt();
StudentController.addStudent(name, dept, marks); break;
case 2:
List<Student> students = StudentController.getAllStudents(); System.out.println("\nStudent List:");
for (Student s : students) {
System.out.println(s.getStudentID() + " | " + s.getName() + " | " + s.getDepartment() + " | " + s.getMarks());
}
br eak; case 3:
System.out.print("Enter Student ID to update: "); int updateID = scanner.nextInt(); scanner.nextLine();
System.out.print("Enter New Name: "); String newName = scanner.nextLine(); System.out.print("Enter New Department:
"); String newDept = scanner.nextLine(); System.out.print("Enter New Marks: "); int newMarks = scanner.nextInt();
StudentController.updateStudent(updateID, newName, newDept, newMarks); break;
case 4:
System.out.print("Enter Student ID to delete: "); int deleteID = scanner.nextInt(); StudentController.deleteStudent(deleteID); break;
case 5:
System.out.println("Exiting..."); scanner.close();
return;
default:
System.out.println("Invalid choice! Try again.");
}
}
}
}

Student Controller.java

package controller; import model.Student; import
utils.DBConnection; import java.sql.*; import java.util.ArrayList; import java.util.List;

public class StudentController {
public static void addStudent(String name, String department, int marks) {
String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?,
?)"; try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setString(1, name);
pstmt.setString(2, department); pstmt.setInt(3, marks); pstmt.executeUpdate();
System.out.println("Student added successfully.");
} catch (Exception e) { e.printStackTrace();
}
}
public static List<Student> getAllStudents() { List<Student> students = new ArrayList<>(); String sql = "SELECT * FROM Student";
try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql)) { while (rs.next()) {
students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getInt("Marks")));
}
} catch (Exception e) { e.printStackTrace();
}
return students;

}
public static void updateStudent(int studentID, String name, String department, int marks) {
String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setString(1, name);
pstmt.setString(2, department); pstmt.setInt(3, marks); pstmt.setInt(4, studentID); pstmt.executeUpdate();
System.out.println("Student updated successfully.");
} catch (Exception e) { e.printStackTrace();
}
}
public static void deleteStudent(int studentID) {
String sql = "DELETE FROM Student WHERE StudentID =
?"; try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setInt(1, studentID);
pstmt.executeUpdate(); System.out.println("Student deleted successfully.");
} catch (Exception e) { e.printStackTrace();
}
}
}
DBConnection.jav a package controller; import model.Student; import
utils.DBConnection; import java.sql.*; import java.util.ArrayList; import java.util.List;

public class StudentController {
public static void addStudent(String name, String department, int marks) {
String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?,
?)"; try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setString(1, name);
pstmt.setString(2, department); pstmt.setInt(3, marks);
pstmt.executeUpdate(); System.out.println("Student added successfully.");
} catch (Exception e) { e.printStackTrace();
}
}
public static List<Student> getAllStudents() { List<Student> students = new ArrayList<>();

String sql = "SELECT * FROM Student"; try (Connection conn =
DBConnection.getConnection(); Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql)) { while (rs.next()) {
students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getInt("Marks")));
}
} catch (Exception e) { e.printStackTrace();
}
return students;
}
public static void updateStudent(int studentID, String name, String department, int marks) {
String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setString(1, name);
pstmt.setString(2, department); pstmt.setInt(3, marks); pstmt.setInt(4, studentID); pstmt.executeUpdate();
System.out.println("Student updated successfully.");
} catch (Exception e) { e.printStackTrace();
}
}
public static void deleteStudent(int studentID) {
String sql = "DELETE FROM Student WHERE StudentID =
?"; try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { pstmt.setInt(1, studentID);
pstmt.executeUpdate(); System.out.println("Student deleted successfully.");
} catch (Exception e) {
e.printStackTrace();
}
}
}


