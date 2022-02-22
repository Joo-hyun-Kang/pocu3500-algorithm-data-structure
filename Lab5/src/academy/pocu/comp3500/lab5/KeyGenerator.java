package academy.pocu.comp3500.lab5;

import java.math.BigInteger;
import java.util.ArrayList;

public class KeyGenerator {

    public static boolean isPrime(final BigInteger number) {
        if (number.compareTo(BigInteger.TWO) == -1) {
            return false;
        }

        if (number.compareTo(BigInteger.TWO) == 0) {
            return true;
        }

        if (number.remainder(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        BigInteger even = number.subtract(BigInteger.ONE);
        int exponent = 0;

        while (even.remainder(BigInteger.TWO).equals(BigInteger.ZERO)) {
            exponent++;
            even = even.divide(BigInteger.TWO);
        }

        BigInteger factorPrime = even;

        BigInteger testNumber[] = new BigInteger[1];
        testNumber[0] = BigInteger.valueOf(2);

        for (int i = 0; i < testNumber.length; i++) {
            if (isPrimeByMillerRabin(number, factorPrime, exponent, testNumber[i]) == false) {
                return false;
            }
        }

        return true;
    }

    private static boolean isPrimeByMillerRabin(BigInteger number, BigInteger factorPrime, int exponent, BigInteger testNumber) {
        BigInteger numberMinusOne = number.subtract(BigInteger.ONE);
        if (testNumber.modPow(factorPrime, number).equals(BigInteger.ONE) == false) {
            for (int r = 0; r < exponent; r++) {
                if (testNumber.modPow(factorPrime.multiply(BigInteger.TWO.pow(r)), number).equals(numberMinusOne) == true) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /* 밀러-라빈 알고리즘 int 구현 */
    /* https://man-25-1.tistory.com/99 */
    /* https://casterian.net/archives/396 */
    /* -1 mod n = n - 1 */
    public static boolean isPrime(final int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        long even = number - 1;

        long twoExponent = 0;
        while (even % 2 == 0) {
            twoExponent++;
            even /= 2;
        }

        long factorPrime = even;

        long testNumbers[] = new long[] {2};

        for (int i = 0; i < testNumbers.length; i++) {
            if (isPrimeByMillerRabin(number, factorPrime, twoExponent, testNumbers[i]) == false) {
                return false;
            }
        }

        return true;
    }

    private static boolean isPrimeByMillerRabin(final int number, long factorPrime, long twoExponet, long testNumber) {
        if (Math.pow(testNumber, factorPrime) % number != 1) {
            for (int r = 0; r < twoExponet; r++) {
                if (Math.pow(testNumber, Math.pow(2, r) * factorPrime) % number == number - 1) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /* 과제와 상관 없는 범위 내에서 소수 구하기 */
    /* Using aeratosthenese sieve */
    public static void printPrime(final int max) {
        boolean prime[] = new boolean[max + 1];

        for (int i = 0; i < max + 1; i++) {
            prime[i] = true;
        }

        prime[0] = false;
        prime[1] = false;

        for (int i = 2; i * i < max + 1; i++) {
            if (prime[i] == true) {
                for (int j = i * 2; j < max + 1; j += i) {
                    prime[j] = false;
                }
            }
        }

        int j = 0;
        for (int i = 0; i < max + 1; i++) {
            if (prime[i] == true) {
                System.out.printf("%d  ", i);

                j++;
                if (j / 5 == 1) {
                    System.out.println();
                    j = 0;
                }
            }
        }

        System.out.println();
    }

    /* 과제와 상관 없는 범위 내에서 소인수 분해 구하기 */
    public static void printFctorization(int number) {
        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                System.out.printf("%d  ", i);
                number /= i;
            }
        }

        if (number > 1) {
            System.out.printf("%d  ", number);
        }
    }
}