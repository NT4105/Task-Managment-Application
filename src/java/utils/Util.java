package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

public class Util {

    /*
     *
     * encryptPassword use for encrypting password before save to db
     * encryptPassword use for ecrypting id before assgin to cookie.Value();
     */
    public static String encryptPassword(String password) {
        if (password == null) {
            return null;
        }

        try {
            // Use BCrypt to hash the password with a randomly generated salt
            return BCrypt.hashpw(password, BCrypt.gensalt());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * Method to verify if a password matches its hashed version
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}