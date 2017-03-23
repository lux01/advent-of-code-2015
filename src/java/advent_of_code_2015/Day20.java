package advent_of_code_2015;

public class Day20 {
    /// Calculates the sum of the divisors of n.
    public static int sigma(int n) {
        int total = 1;

        for(int d = 2; d <= n; d++) {
            if (n % d == 0) {
                total += d;
            }
        }

        return total;
    }

    public static int sigma_2(int n) {
        int total = 0;

        for(int d = n; d > 0; d--) {
            if (n / d > 50) {
                break;
            }
            if (n % d == 0) {
                total += d;
            }
        }

        return total;
    }
}
