import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DESExample {
    private static final Logger logger = Logger.getLogger(DESExample.class.getName());

    public static SecretKey generateKey(String keyStr) throws Exception {
        byte[] keyBytes = keyStr.getBytes();
        if (keyBytes.length != 8) {
            throw new IllegalArgumentException("Key must be exactly 8 characters (64 bits).");
        }

        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(desKeySpec);
    }

    public static String encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("==== DES Encryption/Decryption ====");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter 8-character key: ");
            String keyStr = scanner.nextLine();
            SecretKey key = generateKey(keyStr);

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
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid Key: {0}", e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during DES operation", e);
        }
    }
}
