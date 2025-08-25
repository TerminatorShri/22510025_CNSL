import java.util.*;

public class PlayfairCipher {

    private static final char[][] matrix = new char[5][5];

    private static void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();

        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                sb.append(c);
                used[c - 'A'] = true;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                sb.append(c);
                used[c - 'A'] = true;
            }
        }

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = sb.charAt(k++);
            }
        }
    }

    private static int[] getPosition(char c) {
        if (c == 'J') c = 'I';
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == c)
                    return new int[]{i, j};
        return null;
    }

    private static List<String> formPairs(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        List<String> pairs = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';

            if (a == b) {
                pairs.add("" + a + 'X');
            } else {
                pairs.add("" + a + b);
                i++; // skip next char
            }
        }

        if (pairs.getLast().length() == 1) {
            pairs.set(pairs.size() - 1, pairs.getLast() + "X");
        }

        return pairs;
    }

    private static String encrypt(String plaintext, String key) {
        generateMatrix(key);
        List<String> pairs = formPairs(plaintext);
        StringBuilder cipher = new StringBuilder();

        for (String pair : pairs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            int[] pos1 = getPosition(a);
            int[] pos2 = getPosition(b);

            assert pos1 != null;
            assert pos2 != null;
            if (pos1[0] == pos2[0]) {
                cipher.append(matrix[pos1[0]][(pos1[1] + 1) % 5]);
                cipher.append(matrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                cipher.append(matrix[(pos1[0] + 1) % 5][pos1[1]]);
                cipher.append(matrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                cipher.append(matrix[pos1[0]][pos2[1]]);
                cipher.append(matrix[pos2[0]][pos1[1]]);
            }
        }

        return cipher.toString();
    }

    private static String decrypt(String ciphertext, String key) {
        generateMatrix(key);
        List<String> pairs = formPairs(ciphertext);
        StringBuilder plain = new StringBuilder();

        for (String pair : pairs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);
            int[] pos1 = getPosition(a);
            int[] pos2 = getPosition(b);

            assert pos1 != null;
            assert pos2 != null;
            if (pos1[0] == pos2[0]) {
                plain.append(matrix[pos1[0]][(pos1[1] + 4) % 5]);
                plain.append(matrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                plain.append(matrix[(pos1[0] + 4) % 5][pos1[1]]);
                plain.append(matrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                plain.append(matrix[pos1[0]][pos2[1]]);
                plain.append(matrix[pos2[0]][pos1[1]]);
            }
        }

        return plain.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Playfair Cipher");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Choose an option (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

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

        scanner.close();
    }
}
