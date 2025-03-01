package dao;

import dto.UserDTO;
import model.User;
import model.enums.UserRole;
import utils.JDBCUtil;
import utils.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Profile;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public int insert(UserDTO userDTO) {
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // Insert into Users table
            String userSql = "INSERT INTO Users (UserId, Username, Password, Role, CreatedAt, UpdatedAt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement userPs = con.prepareStatement(userSql)) {
                userPs.setString(1, userDTO.getUserID());
                userPs.setString(2, userDTO.getUserName());
                userPs.setString(3, Util.encryptPassword(userDTO.getPassword()));
                userPs.setString(4, userDTO.getRole().name());
                userPs.setDate(5, userDTO.getCreatedAt());
                userPs.setDate(6, userDTO.getUpdatedAt());
                userPs.executeUpdate();
            }

            // Insert into Profiles table
            String profileSql = "INSERT INTO Profiles (UserId, FirstName, LastName, Email, Phone, DateOfBirth, CreatedAt, UpdatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement profilePs = con.prepareStatement(profileSql)) {
                profilePs.setString(1, userDTO.getUserID());
                profilePs.setString(2, userDTO.getFirstName());
                profilePs.setString(3, userDTO.getLastName());
                profilePs.setString(4, userDTO.getEmail());
                profilePs.setString(5, userDTO.getPhone());
                profilePs.setDate(6, userDTO.getDob());
                profilePs.setDate(7, userDTO.getCreatedAt());
                profilePs.setDate(8, userDTO.getUpdatedAt());
                profilePs.executeUpdate();
            }

            con.commit();
            return 1;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return 0;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User selectById(String userId) {
        String sql = "SELECT u.*, p.FirstName, p.LastName, p.Email, p.Phone, p.DateOfBirth " +
                "FROM Users u " +
                "JOIN Profiles p ON u.UserId = p.UserId " +
                "WHERE u.UserId = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getString("UserId"));
                    user.setUserName(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                    user.setRole(UserRole.fromString(rs.getString("Role")));
                    user.setCreatedAt(rs.getDate("CreatedAt"));
                    user.setUpdatedAt(rs.getDate("UpdatedAt"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    user.setDob(rs.getDate("DateOfBirth"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(User user) {
        return 0; // Implement update logic if needed
    }

    public int updateProfile(User user, boolean upPassword) {
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // Update Users table - Use parameterized GETDATE()
            String userSql = "UPDATE Users SET UpdatedAt = ? ";
            if (upPassword) {
                userSql += ", Password = ?";
            }
            userSql += " WHERE UserId = ?";

            try (PreparedStatement userPs = con.prepareStatement(userSql)) {
                int paramIndex = 1;
                userPs.setDate(paramIndex++, new Date(System.currentTimeMillis()));
                if (upPassword) {
                    userPs.setString(paramIndex++, Util.encryptPassword(user.getPassword()));
                }
                userPs.setString(paramIndex, user.getUserID());
                userPs.executeUpdate();
            }

            // Update Profiles table - Use parameterized GETDATE()
            String profileSql = "UPDATE Profiles SET FirstName = ?, LastName = ?, " +
                    "DateOfBirth = ?, Phone = ?, Email = ?, UpdatedAt = ? " +
                    "WHERE UserId = ?";

            try (PreparedStatement profilePs = con.prepareStatement(profileSql)) {
                profilePs.setString(1, user.getFirstName());
                profilePs.setString(2, user.getLastName());
                profilePs.setDate(3, user.getDob());
                profilePs.setString(4, user.getPhone());
                profilePs.setString(5, user.getEmail());
                profilePs.setDate(6, new Date(System.currentTimeMillis()));
                profilePs.setString(7, user.getUserID());
                profilePs.executeUpdate();
            }

            con.commit();
            return 1;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return 0;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User login(String userName, String password) {
        String sql = "SELECT u.*, p.FirstName, p.LastName, p.Email, p.Phone, p.DateOfBirth " +
                "FROM Users u " +
                "JOIN Profiles p ON u.UserID = p.UserID " +
                "WHERE u.Username = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.INFO,
                    "Attempting login for username: {0}", userName);

            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("Password");
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO,
                        "Found user, comparing passwords. Stored hash: {0}", hashedPassword);

                // Verify plain password với hashedPassword từ DB
                boolean passwordMatch = Util.verifyPassword(password, hashedPassword);
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO,
                        "Password verification result: {0}", passwordMatch);

                if (passwordMatch) {
                    User user = new User();
                    user.setUserID(rs.getString("UserID"));
                    user.setUserName(userName);
                    user.setPassword(hashedPassword);
                    user.setRole(UserRole.valueOf(rs.getString("Role")));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    user.setDob(rs.getDate("DateOfBirth"));
                    return user;
                }
            } else {
                Logger.getLogger(UserDAO.class.getName()).log(Level.INFO,
                        "No user found with username: {0}", userName);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE,
                    "Database error during login", e);
        }
        return null;
    }

    public User checkAccessToHome(String id) {
        User res = null;
        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (Util.encryptPassword(user.getUserID()).equals(id)) {
                res = user;
            }
        }
        return res;
    }

    public boolean checkDuplicateEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Profiles WHERE Email = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkDuplicatePhone(String phone) {
        String sql = "SELECT COUNT(*) FROM Profiles WHERE Phone = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add method to check if username exists
    public boolean checkDuplicateUsername(String userName) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(User user) {
        String sql = "UPDATE Users SET Password = ?, UpdatedAt = GETDATE() WHERE UserId = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUserID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT u.*, p.FirstName, p.LastName, p.Email, p.Phone, p.DateOfBirth " +
                "FROM Users u " +
                "JOIN Profiles p ON u.UserId = p.UserId";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("UserId"));
                user.setUserName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setRole(UserRole.fromString(rs.getString("Role")));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setUpdatedAt(rs.getDate("UpdatedAt"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setDob(rs.getDate("DateOfBirth"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users u JOIN Profiles p ON u.UserID = p.UserID WHERE p.Email = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("UserID"));
                user.setUserName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String email, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE Email = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Util.encryptPassword(newPassword));
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getString("UserID"));
        user.setUserName(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setRole(UserRole.fromString(rs.getString("Role")));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setEmail(rs.getString("Email"));
        user.setPhone(rs.getString("Phone"));
        user.setDob(rs.getDate("DateOfBirth"));
        user.setCreatedAt(rs.getDate("CreatedAt"));
        user.setUpdatedAt(rs.getDate("UpdatedAt"));
        return user;
    }
}