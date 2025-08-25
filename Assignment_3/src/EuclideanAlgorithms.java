import java.util.Scanner;

public class EuclideanAlgorithms {

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public static int[] extendedGcd(int a, int b) {
        if (b == 0) {
            return new int[] { a, 1, 0 };
        }
        int[] vals = extendedGcd(b, a % b);
        int gcd = vals[0];
        int x1 = vals[1];
        int y1 = vals[2];

        int x = y1;
        int y = x1 - (a / b) * y1;

        return new int[] { gcd, x, y };
    }

    public static Integer modInverse(int a, int m) {
        int[] vals = extendedGcd(a, m);
        int gcd = vals[0];
        int x = vals[1];

        if (gcd != 1) {
            return null;
        } else {
            return (x % m + m) % m;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first integer (a): ");
        int a = sc.nextInt();
        System.out.print("Enter second integer (b): ");
        int b = sc.nextInt();

        int gcdVal = gcd(a, b);
        System.out.println("\nUsing Euclidean Algorithm:");
        System.out.println("GCD(" + a + ", " + b + ") = " + gcdVal);

        int[] result = extendedGcd(a, b);
        System.out.println("\nUsing Extended Euclidean Algorithm:");
        System.out.println("GCD = " + result[0]);
        System.out.println("Coefficients (x, y): x = " + result[1] + ", y = " + result[2]);
        System.out.println("Verification: " + a + "*" + result[1] + " + " + b + "*" + result[2] + " = " + result[0]);

        if (result[0] == 1) {
            Integer inv = modInverse(a, b);
            System.out.println("\nModular Inverse of " + a + " modulo " + b + " = " + inv);
        } else {
            System.out.println("\nModular inverse does not exist since GCD ≠ 1.");
        }

        sc.close();
    }
}
