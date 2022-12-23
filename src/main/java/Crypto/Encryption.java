//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Crypto;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private static final String ALGORITHM = "AES";
    private static final String KEY_DERIVATION_ALGORITHM = "SHA-256";
    private static final int KEY_LENGTH = 32;
    private static final String PASSWORD = "teamE";

    public Encryption() {
    }

    public static String encrypt(String fileName) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] key = digest.digest("teamE".getBytes());
        key = Arrays.copyOf(key, 32);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(1, keySpec);
        byte[] encryptedFileName = cipher.doFinal(fileName.getBytes());
        return Base64.getEncoder().encodeToString(encryptedFileName);
    }
}
