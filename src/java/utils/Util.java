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
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /*
     * Method to verify if a password matches its hashed version
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static void testPasswordHash(String plainPassword, String hashedPassword) {
        System.out.println("Plain password: " + plainPassword);
        System.out.println("Hashed password from DB: " + hashedPassword);
        System.out.println("Verification result: " + verifyPassword(plainPassword, hashedPassword));

        // Test tạo hash mới
        String newHash = encryptPassword(plainPassword);
        System.out.println("New hash generated: " + newHash);
        System.out.println("New hash verification: " + verifyPassword(plainPassword, newHash));
    }
}