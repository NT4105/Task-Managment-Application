/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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

/**
 *
 * @author Nguyen Huy Hoang
 */
public class UserDAO {

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    public int insert(User user) {
        if (!checkDuplicateEmail(user.getEmail()) || !checkDuplicatePhone(user.getPhone())) {
            return 0;
        }

        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO user(UserId, firstName, lastName, userName, dob, phone, email, password, role) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);

            // Generate UUID for userId
            String userId = java.util.UUID.randomUUID().toString();

            pst.setString(1, userId);
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getUserName());
            pst.setDate(5, user.getDob());
            pst.setString(6, user.getPhone());
            pst.setString(7, user.getEmail());
            pst.setString(8, Util.encryptPassword(user.getPassword()));
            pst.setString(9, user.getRole().toString());

            result = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (con != null)
                    JDBCUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int update(User user) {
        return 0; // Implement update logic if needed
    }

    public int updateProfile(User user, boolean upPassword) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE user SET firstName = ?, lastName = ?, dob = ?, phone = ?, email = ?, password = ? WHERE UserId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setDate(3, user.getDob());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getEmail());
            if (upPassword) {
                pst.setString(6, Util.encryptPassword(user.getPassword()));
            } else {
                pst.setString(6, user.getPassword());
            }
            pst.setString(7, user.getUserID());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userName = rs.getString("userName");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");

                User user = new User(id, firstName, lastName, userName, dob, phone, email, password,
                        UserRole.fromString(role));
                res.add(user);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public User selectById(String id) {
        User result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user WHERE UserId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userName = rs.getString("userName");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");

                result = new User(userId, firstName, lastName, userName, dob, phone, email, password,
                        UserRole.fromString(role));
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<User> selectByCondition(String condition) {
        ArrayList<User> res = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user WHERE (firstName LIKE ? OR lastName LIKE ?) OR CONCAT(firstName, ' ', lastName) LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + condition.trim() + "%");
            pst.setString(2, "%" + condition.trim() + "%");
            pst.setString(3, condition.trim());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String userName = rs.getString("userName");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");

                User user = new User(userId, firstName, lastName, userName, dob, phone, email, password,
                        UserRole.fromString(role));
                res.add(user);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String login(String email, String password) {
        String result = "";
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, Util.encryptPassword(password)); // mã hóa mật khẩu để so sánh với mật khẩu có trong db

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getString("email").equals(email)
                        && rs.getString("password").equals(Util.encryptPassword(password))) {
                    result = rs.getString("UserId");
                }
            }
        } catch (SQLException e) {
            result = "";
        }
        return result;
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
        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (email.trim().equals(user.getEmail())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDuplicatePhone(String phone) {
        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (phone.trim().equals(user.getPhone())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateField(String userId, String fieldName, String value) {
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE user SET " + fieldName + "=? WHERE UserId=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, value);
            pst.setString(2, userId);

            result = pst.executeUpdate() > 0;
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Specific update methods
    public boolean updateFirstName(String userId, String firstName) {
        return updateField(userId, "firstName", firstName);
    }

    public boolean updateLastName(String userId, String lastName) {
        return updateField(userId, "lastName", lastName);
    }

    public boolean updateEmail(String userId, String email) {
        return updateField(userId, "email", email);
    }

    public boolean updatePhone(String userId, String phone) {
        return updateField(userId, "phone", phone);
    }

    public boolean updatePassword(String userId, String encryptedPassword) {
        return updateField(userId, "password", encryptedPassword);
    }

    // Add method to check if username exists
    public boolean checkDuplicateUsername(String username) {
        ArrayList<User> users = selectAll();
        return users.stream().noneMatch(user -> username.equals(user.getUserName()));
    }
}