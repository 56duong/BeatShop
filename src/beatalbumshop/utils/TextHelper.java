package beatalbumshop.utils;

import com.lambdaworks.crypto.SCryptUtil;

/**
 * The TextHelper class provides utility methods for working with passwords.
 */
public class TextHelper {
    
    /**
     * Hashes a password using the SCrypt hashing algorithm.
     *
     * @param password the password to be hashed
     * @return the hashed password
     */
    public static String HashPassword(String password) {
        String generatedSecuredPasswordHash = SCryptUtil.scrypt(password, 16, 16, 16);
        return generatedSecuredPasswordHash;
    }
    
    
    
    /**
     * Authenticates a password by checking it against a hashed password.
     *
     * @param password the password to be authenticated
     * @param hashPassword the hashed password to be compared against
     * @return true if the password is authenticated, false otherwise
     */
    public static boolean authenticationPasswordHash(String password, String hashPassword) {
        return SCryptUtil.check(password, hashPassword);
    }
}
