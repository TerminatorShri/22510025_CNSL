import java.util.*;

public class HillCipher {

    private static int charToInt(char c) {
        return c - 'A';
    }

    private static char intToChar(int n) {
        return (char) (n + 'A');
    }

    private static int[] multiplyMatrix(int[][] key, int[] vector) {
        int[] result = new int[2];
        for (int i = 0; i < 2; i++) {
            result[i] = (key[i][0] * vector[0] + key[i][1] * vector[1]) % 26;
            if (result[i] < 0) result[i] += 26;
        }
        return result;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return -1;
    }

    private static int[][] inverseMatrix(int[][] key) {
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        det = ((det % 26) + 26) % 26;
        int invDet = modInverse(det, 26);
        if (invDet == -1) {
            throw new IllegalArgumentException("Key matrix is not invertible mod 26.");
        }

        int[][] invKey = new int[2][2];
        invKey[0][0] = (key[1][1] * invDet) % 26;
        invKey[0][1] = (-key[0][1] * invDet) % 26;
        invKey[1][0] = (-key[1][0] * invDet) % 26;
        invKey[1][1] = (key[0][0] * invDet) % 26;

        // Ensure positive values
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                invKey[i][j] = (invKey[i][j] + 26) % 26;

        return invKey;
    }

    private static String encrypt(String plaintext, int[][] key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        if (plaintext.length() % 2 != 0) plaintext += "X";

        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] vector = {charToInt(plaintext.charAt(i)), charToInt(plaintext.charAt(i + 1))};
            int[] result = multiplyMatrix(key, vector);
            cipher.append(intToChar(result[0])).append(intToChar(result[1]));
        }
        return cipher.toString();
    }

    private static String decrypt(String ciphertext, int[][] key) {
        int[][] invKey = inverseMatrix(key);
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");

        StringBuilder plain = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int[] vector = {charToInt(ciphertext.charAt(i)), charToInt(ciphertext.charAt(i + 1))};
            int[] result = multiplyMatrix(invKey, vector);
            plain.append(intToChar(result[0])).append(intToChar(result[1]));
        }
        return plain.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hill Cipher (2x2 Matrix)");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Choose an option (1 or 2): ");
        int choice = sc.nextInt();
        sc.nextLine();

        int[][] key = new int[2][2];
        System.out.println("Enter 2x2 key matrix (integers only, space-separated):");
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                key[i][j] = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the text: ");
        String text = sc.nextLine();

        String result;
        if (choice == 1) {
            result = encrypt(text, key);
            System.out.println("Encrypted Text: " + result);
        } else if (choice == 2) {
            try {
                result = decrypt(text, key);
                System.out.println("Decrypted Text: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
