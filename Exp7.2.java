import java.sql.*;
import java.util.Scanner;

public class ProductManager {
    static final String URL = "jdbc:mysql://localhost:3306/ShopDB";
    static final String USER = "root";
    static final String PASS = "New@0001";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn.setAutoCommit(false);  // Enable transaction control

            int choice;

            do {
                System.out.println("\n--- Product Management System ---");
                System.out.println("1. Create Product");
                System.out.println("2. Read All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        createProduct(conn, sc);
                        break;
                    case 2:
                        readProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, sc);
                        break;
                    case 4:
                        deleteProduct(conn, sc);
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create
    public static void createProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setDouble(3, price);
                ps.setInt(4, qty);
                ps.executeUpdate();
                conn.commit();
                System.out.println("Product inserted successfully.");
            }
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            System.out.println("Error during insertion.");
            e.printStackTrace();
        }
    }

    // Read
    public static void readProducts(Connection conn) {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Product")) {

            System.out.println("\nProductID\tName\t\tPrice\tQuantity");
            System.out.println("---------------------------------------------");

            while (rs.next()) {
                System.out.printf("%d\t\t%-10s\t%.2f\t%d\n",
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity"));
            }

        } catch (Exception e) {
            System.out.println("Error fetching data.");
            e.printStackTrace();
        }
    }

    // Update
    public static void updateProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();

            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();

            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            String sql = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, price);
                ps.setInt(2, qty);
                ps.setInt(3, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    System.out.println("Product updated successfully.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            System.out.println("Error during update.");
            e.printStackTrace();
        }
    }

    // Delete
    public static void deleteProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    System.out.println("Product deleted successfully.");
                } else {
                    System.out.println("Product not found.");
                }
            }
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            System.out.println("Error during deletion.");
            e.printStackTrace();
        }
    }
}
// --- Product Management System ---
// 1. Create Product
// 2. Read All Products
// 3. Update Product
// 4. Delete Product
// 5. Exit
// Enter your choice: 1
// Enter Product ID: 101
// Enter Product Name: sa
// Enter Price: 100
// Enter Quantity: 1
// Product inserted successfully.
