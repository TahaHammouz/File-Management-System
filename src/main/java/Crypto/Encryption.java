package Crypto;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import static Constants.Constants.*;

public class Encryption {

    public static String encrypt(String fileName) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(KEY_DERIVATION_ALGORITHM);
        byte[] key = digest.digest(PASSWORD.getBytes());
        key = Arrays.copyOf(key, KEY_LENGTH);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
        cipher.init(1, keySpec);
        byte[] encryptedFileName = cipher.doFinal(fileName.getBytes());
        return Base64.getEncoder().encodeToString(encryptedFileName);
    }
}
