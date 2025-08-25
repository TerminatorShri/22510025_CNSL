import java.util.Scanner;

public class ChineseRemainderTheorem {

    public static int[] extendedEuclidean(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0};
        }
        int[] vals = extendedEuclidean(b, a % b);
        int gcd = vals[0];
        int x1 = vals[2];
        int y1 = vals[1] - (a / b) * vals[2];
        return new int[]{gcd, x1, y1};
    }

    public static int modInverse(int a, int m) {
        int[] vals = extendedEuclidean(a, m);
        int gcd = vals[0];
        int x = vals[1];
        if (gcd != 1) {
            throw new ArithmeticException("Modular inverse does not exist for " + a + " mod " + m);
        } else {
            return (x % m + m) % m;
        }
    }

    public static int chineseRemainder(int[] a, int[] n) {
        int k = a.length;
        int N = 1;

        for (int ni : n) {
            N *= ni;
        }

        int result = 0;

        for (int i = 0; i < k; i++) {
            int Ni = N / n[i];
            int Mi = modInverse(Ni, n[i]);
            result += a[i] * Ni * Mi;
        }

        return ((result % N) + N) % N;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of congruences: ");
        int k = sc.nextInt();

        int[] a = new int[k];
        int[] n = new int[k];

        System.out.println("Enter congruences in the form: x ≡ ai (mod ni)");

        for (int i = 0; i < k; i++) {
            System.out.print("Enter ai: ");
            a[i] = sc.nextInt();
            System.out.print("Enter ni: ");
            n[i] = sc.nextInt();
        }

        try {
            int x = chineseRemainder(a, n);
            System.out.println("The smallest non-negative solution is: " + x);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
