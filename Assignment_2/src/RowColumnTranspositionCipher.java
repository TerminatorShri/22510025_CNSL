import java.util.*;

public class RowColumnTranspositionCipher {

    public static String encrypt(String plaintext, String key) {
        plaintext = plaintext.replaceAll("\\s", "").toUpperCase();
        int cols = key.length();
        int rows = (int) Math.ceil((double) plaintext.length() / cols);

        char[][] matrix = new char[rows][cols];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < plaintext.length())
                    matrix[i][j] = plaintext.charAt(k++);
                else
                    matrix[i][j] = 'X';
            }
        }

        Integer[] order = getOrder(key);

        StringBuilder cipher = new StringBuilder();
        for (int col : order) {
            for (int i = 0; i < rows; i++) {
                cipher.append(matrix[i][col]);
            }
        }
        return cipher.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / cols);

        char[][] matrix = new char[rows][cols];

        Integer[] order = getOrder(key);

        int k = 0;
        for (int col : order) {
            for (int i = 0; i < rows; i++) {
                if (k < ciphertext.length())
                    matrix[i][col] = ciphertext.charAt(k++);
            }
        }

        StringBuilder plain = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plain.append(matrix[i][j]);
            }
        }
        return plain.toString().replace("X", "");
    }

    private static Integer[] getOrder(String key) {
        key = key.toUpperCase();
        Character[] keyChars = new Character[key.length()];
        for (int i = 0; i < key.length(); i++) keyChars[i] = key.charAt(i);

        Integer[] order = new Integer[key.length()];
        for (int i = 0; i < key.length(); i++) order[i] = i;

        Arrays.sort(order, (a, b) -> keyChars[a].compareTo(keyChars[b]));
        return order;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Row and Column Transposition Cipher");
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
