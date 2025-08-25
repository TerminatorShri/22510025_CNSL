import java.util.Scanner;

public class VigenereCipher {

    private static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = key.charAt(j) - 'A';
            char encryptedChar = (char) ((c - 'A' + shift) % 26 + 'A');
            result.append(encryptedChar);
            j = (j + 1) % key.length();
        }
        return result.toString();
    }

    private static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = key.charAt(j) - 'A';
            char decryptedChar = (char) ((c - 'A' - shift + 26) % 26 + 'A');
            result.append(decryptedChar);
            j = (j + 1) % key.length();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Vigenère Cipher");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Choose an option (1 or 2): ");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the key: ");
        String key = sc.nextLine();

        System.out.print("Enter the text: ");
        String text = sc.nextLine();

        String result;
        if (choice == 1) {
            result = encrypt(text, key);
            System.out.println("Encrypted Text: " + result);
        } else if (choice == 2) {
            result = decrypt(text, key);
            System.out.println("Decrypted Text: " + result);
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
