import java.util.Scanner;

public class RailFenceCipher {

    private static String encrypt(String text, int rails) {
        text = text.replaceAll("\\s+", "");
        char[][] rail = new char[rails][text.length()];

        for (int i = 0; i < rails; i++)
            for (int j = 0; j < text.length(); j++)
                rail[i][j] = '\n';

        boolean dirDown = false;
        int row = 0, col = 0;

        for (char c : text.toCharArray()) {
            if (row == 0 || row == rails - 1) dirDown = !dirDown;
            rail[row][col++] = c;
            row += dirDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < text.length(); j++)
                if (rail[i][j] != '\n')
                    result.append(rail[i][j]);

        return result.toString();
    }

    private static String decrypt(String cipher, int rails) {
        char[][] rail = new char[rails][cipher.length()];

        for (int i = 0; i < rails; i++)
            for (int j = 0; j < cipher.length(); j++)
                rail[i][j] = '\n';

        boolean dirDown = false;
        int row = 0, col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == rails - 1) dirDown = !dirDown;
            rail[row][col++] = '*';
            row += dirDown ? 1 : -1;
        }

        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                if (rail[i][j] == '*' && index < cipher.length()) {
                    rail[i][j] = cipher.charAt(index++);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        row = 0; col = 0;
        dirDown = false;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == rails - 1) dirDown = !dirDown;
            result.append(rail[row][col++]);
            row += dirDown ? 1 : -1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Rail Fence Cipher");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Choose an option (1 or 2): ");
        int choice = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter the number of rails: ");
        int rails = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter the text: ");
        String text = sc.nextLine();

        String result;
        if (choice == 1) {
            result = encrypt(text, rails);
            System.out.println("Encrypted Text: " + result);
        } else if (choice == 2) {
            result = decrypt(text, rails);
            System.out.println("Decrypted Text: " + result);
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
