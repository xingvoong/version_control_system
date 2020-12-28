import java.util.Arrays;
import java.util.ArrayList;

public class DistributionSorts {

    /*
     * Destructively sorts ARR using counting sort. Assumes that ARR contains only
     * 0, 1, ..., 9.
     */
    public static void countingSort(int[] arr) {
        int[] count = new int[10];
        int[] sorted = new int[arr.length];
        int[] start = new int[10];

        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        start[0] = 0;
        start[1] = count[0];
        for (int i = 2; i < 10; i++) {
            if (count[i] != 0) {
                int j = i;
                while (j > 0) {
                    start[i] += count[j - 1];
                    j--;
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            int d = arr[i];
            sorted[start[d]] = arr[i];
            start[d]++;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sorted[i];
        }
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {

        int maxDigit = mostDigitsIn(arr);
        // int maxDigit = 1;

        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }

    }

    /*
     * A helper method for radix sort. Modifies ARR to be sorted according to
     * DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the rightmost
     * digit of each number.
     */
    private static void countingSortOnDigit(int[] arr, int digit) {
        int[] count = new int[10];
        for (int i = 0; i < arr.length; i++) {
            int d = (int) ((arr[i] / Math.pow(10, digit)) % 10);
            count[d] += 1;

        }
        int[] sorted = new int[arr.length];
        int[] start = new int[count.length];
        start[0] = 0;
        start[1] = count[0];
        for (int i = 2; i < 10; i++) {
            start[i] = start[i - 1] + count[i - 1];
        }
        for (int i = 0; i < arr.length; i++) {
            int d = (int) ((arr[i] / Math.pow(10, digit)) % 10);
            sorted[start[d]] = arr[i];
            start[d]++;
        }
        /*
         * for (int i = 0; i < arr.length; i++) { arr[i] = sorted[i]; }
         */

        System.arraycopy(sorted, 0, arr, 0, arr.length);
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }

        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    private static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];

        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    public static void main(String[] args) {
        runCountingSort(20);

        // runLSDRadixSort(8);
        runLSDRadixSort(3);
        runLSDRadixSort(30);
    }
}