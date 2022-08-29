package example;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

class Encryption {

    private final String digestName = "md5";
    private final String digestPassword = "HG58YZ3CR9";
    private final SecretKey key = setupSecretKey();
    private final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

    private SecretKey setupSecretKey() {
      try {
        final MessageDigest md = MessageDigest.getInstance(digestName);
        final byte[] digestOfPassword = md.digest(digestPassword
                .getBytes("utf-8"));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        return new SecretKeySpec(keyBytes, "DESede");

      } catch(Exception e) {

      }
      return null;
    }

    private Cipher setupCipher(int optMode) throws Exception {
      Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      cipher.init(optMode, key, iv);
      return cipher;
    }

    public String encryptPasswordBased(String plainText) throws Exception
    {
        Cipher cipher = setupCipher(Cipher.ENCRYPT_MODE);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public String decryptPasswordBased(String cipherText) throws Exception{
      Cipher cipher = setupCipher(Cipher.DECRYPT_MODE);
      return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }

}
