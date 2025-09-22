import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AESExample {
    private static final Logger logger = Logger.getLogger(AESExample.class.getName());

    public static SecretKey generateKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

    public static SecretKey buildKeyFromString(String keyStr, int keySize) {
        byte[] keyBytes = keyStr.getBytes();
        byte[] keyBytesPadded = new byte[keySize / 8];
        System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, keyBytesPadded.length));
        return new SecretKeySpec(keyBytesPadded, "AES");
    }

    public static String encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("==== AES Encryption/Decryption ====");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter key (any string): ");
            String keyStr = scanner.nextLine();

            System.out.print("Enter key size (128, 192, 256): ");
            int keySize = scanner.nextInt();
            scanner.nextLine();

            SecretKey key = buildKeyFromString(keyStr, keySize);

            if (choice == 1) {
                System.out.print("Enter plaintext message: ");
                String plaintext = scanner.nextLine();
                String encryptedText = encrypt(plaintext, key);
                System.out.println("Encrypted (Ciphertext): " + encryptedText);
            } else if (choice == 2) {
                System.out.print("Enter ciphertext (Base64): ");
                String ciphertext = scanner.nextLine();
                String decryptedText = decrypt(ciphertext, key);
                System.out.println("Decrypted (Plaintext): " + decryptedText);
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in AES operation", e);
        }
    }
}
