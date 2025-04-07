// student.java

public class Student {
    private int studentID;
    private String name;
    private String department;
    private float marks;

    public Student(int studentID, String name, String department, float marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    // Getters and Setters
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public float getMarks() { return marks; }
    public void setMarks(float marks) { this.marks = marks; }
}

// StudentDOA.java
  

import java.sql.*;
import java.util.*;

public class StudentDAO {
    private Connection conn;

    public StudentDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "New@0001");
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, student.getStudentID());
        stmt.setString(2, student.getName());
        stmt.setString(3, student.getDepartment());
        stmt.setFloat(4, student.getMarks());
        stmt.executeUpdate();
    }

    public Student getStudent(int id) throws SQLException {
        String query = "SELECT * FROM Student WHERE StudentID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Student(rs.getInt("StudentID"), rs.getString("Name"),
                               rs.getString("Department"), rs.getFloat("Marks"));
        }
        return null;
    }

    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getDepartment());
        stmt.setFloat(3, student.getMarks());
        stmt.setInt(4, student.getStudentID());
        stmt.executeUpdate();
    }

    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"),
                                     rs.getString("Department"), rs.getFloat("Marks")));
        }
        return students;
    }
}
  // Main.java
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            StudentDAO dao = new StudentDAO();
            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n1. Add Student");
                System.out.println("2. View Student");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. View All Students");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();  // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        float marks = sc.nextFloat();
                        dao.addStudent(new Student(id, name, dept, marks));
                        System.out.println("Student added.");
                        break;

                    case 2:
                        System.out.print("Enter Student ID: ");
                        id = sc.nextInt();
                        Student s = dao.getStudent(id);
                        if (s != null) {
                            System.out.println("Name: " + s.getName());
                            System.out.println("Department: " + s.getDepartment());
                            System.out.println("Marks: " + s.getMarks());
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID to update: ");
                        id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        name = sc.nextLine();
                        System.out.print("Enter New Department: ");
                        dept = sc.nextLine();
                        System.out.print("Enter New Marks: ");
                        marks = sc.nextFloat();
                        dao.updateStudent(new Student(id, name, dept, marks));
                        System.out.println("Student updated.");
                        break;

                    case 4:
                        System.out.print("Enter ID to delete: ");
                        id = sc.nextInt();
                        dao.deleteStudent(id);
                        System.out.println("Student deleted.");
                        break;

                    case 5:
                        for (Student stu : dao.getAllStudents()) {
                            System.out.println(stu.getStudentID() + " | " +
                                               stu.getName() + " | " +
                                               stu.getDepartment() + " | " +
                                               stu.getMarks());
                        }
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 6);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
// OUTPUT


. Add Student
2. View Student
3. Update Student
4. Delete Student
5. View All Students
6. Exit
Enter your choice: 1
Enter ID: 16222
Enter Name: Sarthak Rana
Enter Department: CSE
Enter Marks: 99
Student added.

1. Add Student
2. View Student
3. Update Student
4. Delete Student
5. View All Students
6. Exit
Enter your choice: 2
Enter Student ID: 16222
Name: Sarthak Rana
Department: CSE
Marks: 99.0
