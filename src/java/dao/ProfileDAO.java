package dao;

import model.Profile;
import model.User;
import utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

public class ProfileDAO {
    private static ProfileDAO instance;

    public static ProfileDAO getInstance() {
        if (instance == null) {
            instance = new ProfileDAO();
        }
        return instance;
    }

    public Profile getProfileByUserId(String userId) {
        String sql = "SELECT * FROM Profiles WHERE UserId = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Profile profile = new Profile();
                    profile.setProfileId(rs.getString("ProfileId"));
                    profile.setUserId(rs.getString("UserId"));
                    profile.setFirstName(rs.getString("FirstName"));
                    profile.setLastName(rs.getString("LastName"));
                    profile.setEmail(rs.getString("Email"));
                    profile.setPhone(rs.getString("Phone"));
                    profile.setDateOfBirth(rs.getDate("DateOfBirth"));
                    profile.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    profile.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    return profile;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(Profile profile) {
        String sql = "UPDATE Profiles SET FirstName = ?, LastName = ?, Email = ?, " +
                "Phone = ?, DateOfBirth = ?, UpdatedAt = GETDATE() " +
                "WHERE UserId = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setString(3, profile.getEmail());
            ps.setString(4, profile.getPhone());
            ps.setDate(5, profile.getDateOfBirth());
            ps.setString(6, profile.getUserId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String userId, String newPassword) {
        String sql = "UPDATE Users SET Password = ?, UpdatedAt = GETDATE() WHERE UserId = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkDuplicateEmail(String email, String userId) {
        String sql = "SELECT COUNT(*) FROM Profiles WHERE Email = ? AND UserId != ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, userId);

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

    public boolean checkDuplicatePhone(String phone, String userId) {
        String sql = "SELECT COUNT(*) FROM Profiles WHERE Phone = ? AND UserId != ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, userId);

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
}