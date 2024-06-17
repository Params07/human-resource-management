package bean;

import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.generators.SCrypt;

public class hashing {
	private static final int costParameter = 16384; 
	private static final  int blockSize = 8;
	private static final int parallelization = 1; 
	private static final int hashLength = 64; 
	public static  byte[] getHashedPassword(String password, byte[] salt) {
		 return SCrypt.generate(password.getBytes(), salt, costParameter, blockSize, parallelization, hashLength);
	}
	public static byte[] generateSalt() {
	    byte[] salt = new byte[16];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(salt);
	    return salt;
	}
	public static boolean verifyPassword(String password, byte[] passwordStored , byte[] storedSalt) {
		 return Arrays.equals(getHashedPassword(password,storedSalt), passwordStored);
	    }
	
}
