/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

/**
 *
 * @author ChanRoi
 */
import model.User;
import JDBC.ConnectJDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class AllInfo {
    Connection connect = ConnectJDBC.getConnection();
    PreparedStatement pre = null;
    public void saveUser(User user){
        String query = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?, ?)";
        try{
            pre = connect.prepareStatement(query);
            pre.setString(0, user.getUserID());
            pre.setString(1, user.getFirstName());
            pre.setString(2, user.getLastName());
            pre.setString(3, user.getUserName());
            pre.setString(4, user.getEmail());
            pre.setString(5, user.getPassword());
            pre.setString(6, user.getRole());
        }catch(SQLException e){
            System.out.println(">>Error: " + e.getMessage());
        }
    }
}
