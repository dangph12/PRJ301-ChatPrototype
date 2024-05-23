package utility;

import java.sql.*;
import model.User;
import dao.AccessDB;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utility {

    private AccessDB connector = new AccessDB();
    private static final String QUERY = "SELECT id, first, last, age FROM Employees";
    private static final String SELECT_QUERY = "SELECT id FROM User";

    public void insertEmployee(String username, String password) throws SQLException {
        if (checkForDuplicateUserName(username)) {
            System.out.println("Can not insert to the table because of the duplicate ID!");
            return;
        }

        String insertSQL = "INSERT INTO [User] (username, password) VALUES (?, ?)";

        try (Connection conn = connector.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User add successfully!");
            } else {
                System.out.println("User addtion failed!");
            }
            connector.closeQuietly(conn, pstmt);
        } catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error occurred while inserting Employee: " + e.getMessage());
        }
    }

    private boolean checkForDuplicateUserName(String nameOfUser) throws SQLException {
        boolean flag = false;
        String selectSQL = "SELECT username FROM [User] WHERE username = ?";

        try (Connection conn = connector.getConnection(); Statement stmt = conn.createStatement(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, nameOfUser);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    flag = true; // Duplicate ID found
                }
            }
        }
        return flag; // No duplicate ID found
    }

    private User getUserByNameUser(String nameOfUser) {
        String selectSQL = "SELECT * FROM [User] WHERE username = ?";
        try (Connection conn = connector.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            // Set the username parameter
            pstmt.setString(1, nameOfUser);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                // generate String salt = HashedPassword.getSalt();
                // generate String hashedPassword = HashedPassword.getHashedPassword();
                User user = new User(userUID, username, password, email, salt);
                return user;
            }
        } catch (SQLException e) {
            // Log the exception or handle it in some way
            System.err.println("Error in getUserByNameUser: " + e.getMessage());
        }
        return null;
    }

    public User authenticateUser(String nameOfUser, String password) {
        String hashPassword = this.encodePassword(password);
        User user = this.getUserByNameUser(nameOfUser);
        if (user != null && user.getPassword().equals(hashPassword)) {
            return user;
        }
        return null;
    }

    private boolean isExistID(int newId) {
        boolean flag = false;
        try (Connection conn = connector.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(SELECT_QUERY)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id == newId) {
                    flag = true;
                    return flag;
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        return flag;
    }
    public String generateSalt() {
        
    }
    public String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
