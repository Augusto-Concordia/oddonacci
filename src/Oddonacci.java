//COMP 352 - Assignment #1
//Due Date: October 7th
//Written by: Augusto Mota Pinheiro (40208080)

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements an Oddonacci sequence calculator.
 */
public class Oddonacci {

    /**
     *  Driver method
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        File oddoOut = new File("OddoOut.txt"); //results output file
        FileWriter oddoOutWriter; //writer to output file

        try {
            oddoOutWriter = new FileWriter(oddoOut);
        } catch (FileNotFoundException e) {
            System.out.println("Output file not found, exiting...");
            return;
        } catch (IOException e) {
            System.out.println("Output file could not be created, read or another error occurred, exiting...");
            return;
        }

        System.out.println("Calculating... Please check output file, when completed, for results.\n");

        try {
            //calculates 20 Oddonacci numbers in 5-increments
            for (int i = 1; i <= 20; i++) {
                final HashMap<Integer, Long> trinaryCache = new HashMap<>(Map.of(1, 1L, 2, 1L, 3, 1L)); //hashmap used to cache CachedTrinaryOddonacci's results
                var adjustedIndex = i * 5;

                //inform user of progress
                System.out.println("Currently at Oddonacci #" + adjustedIndex);

                oddoOutWriter.write("---------------------\n\n");

                oddoOutWriter.write("For Oddonacci #" + adjustedIndex + ": \n");

                //starts the timer, used to calculate total execution time for each method; identical to below
                var startTime = System.nanoTime();

                var result = TrinaryOddonacci(adjustedIndex);

                //outputs the execution time and the result; identical to below
                oddoOutWriter.write("TrinaryOddonacci took " + getFormattedNanoseconds(System.nanoTime() - startTime) + ", with result: " + result + "\n");

                startTime = System.nanoTime();
                result = CachedTrinaryOddonacci(adjustedIndex, trinaryCache);
                oddoOutWriter.write("CachedTrinaryOddonacci took " + getFormattedNanoseconds(System.nanoTime() - startTime) + ", with result: " + result + "\n");

                startTime = System.nanoTime();
                result = LinearOddonacci(adjustedIndex)[0];
                oddoOutWriter.write("LinearOddonacci took " + getFormattedNanoseconds(System.nanoTime() - startTime) + ", with result: " + result + "\n");

                startTime = System.nanoTime();
                result = TailOddonacci(adjustedIndex, 1, 1, 1);
                oddoOutWriter.write("TailOddonacci took " + getFormattedNanoseconds(System.nanoTime() - startTime) + ", with result: " + result + "\n\n");

                oddoOutWriter.flush(); //to make sure we have an output after each number, in case it runs for too long and the user kills the program
            }

            oddoOutWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write to output file, exiting...");
        }
    }

    /**
     * Recursively calculates the kth Oddonacci number (trinary recursion).
     *
     * @param k The index of the wanted Oddonacci number.
     * @return The kth Oddonacci number.
     */
    public static long TrinaryOddonacci(int k) {
        if (k == 1 || k == 2 || k == 3)
            return 1;

        return TrinaryOddonacci(k - 1) + TrinaryOddonacci(k - 2) + TrinaryOddonacci(k - 3);
    }

    /**
     * Recursively calculates the kth Oddonacci number (trinary recursion with result caching).
     *
     * @param k The index of the wanted Oddonacci number.
     * @param cache The cache hashmap used to accelerate computation.
     * @return The kth Oddonacci number.
     */
    public static long CachedTrinaryOddonacci(int k, HashMap<Integer, Long> cache) {
        if (cache.containsKey(k)) //if the result has already been calculated in the past, return it
            return cache.get(k);

        var valueForK = CachedTrinaryOddonacci(k - 1, cache) + CachedTrinaryOddonacci(k - 2, cache) + CachedTrinaryOddonacci(k - 3, cache);

        cache.put(k, valueForK); //puts the calculated result in the cache

        return valueForK;
    }

    /**
     * Recursively calculates the kth Oddonacci number (linear recursion).
     *
     * @param k The index of the wanted Oddonacci number.
     * @return The kth Oddonacci number.
     */
    public static long[] LinearOddonacci(int k) {
        long[] A = new long[3];

        if (k == 1) {
            A[0] = 1;

            return A;
        }

        if (k == 2) {
            A[0] = 1;
            A[1] = 1;

            return A;
        }

        if (k == 3) {
            A[0] = 1;
            A[1] = 1;
            A[2] = 1;

            return A;
        }

        //gets the previous Oddonacci number components
        A = LinearOddonacci(k - 1);

        long i = A[0], j = A[1];

        //sets the kth number's components to the sum of (k - 1)th number's components
        A[0] = i + j + A[2];
        A[1] = i;
        A[2] = j;

        return A;
    }

    /**
     * Recursively calculates the kth Oddonacci number (tail recursion).
     * @param k The index of the wanted Oddonacci number.
     * @param thirdPrev The first Oddonacci number. During recursive execution, it is also the first smallest-index Oddonacci number.
     * @param secondPrev The second Oddonacci number. During recursive execution, it is also the second smallest-index Oddonacci number.
     * @param prev The third Oddonacci number. During recursive execution, it is also the third smallest-index Oddonacci number.
     * @return The kth Oddonacci number.
     */
    public static long TailOddonacci(int k, long thirdPrev, long secondPrev, long prev)
    {
        if (k == 1) return thirdPrev;
        if (k == 2) return secondPrev;
        if (k == 3) return prev;

        return TailOddonacci(k - 1, secondPrev, prev, secondPrev + thirdPrev + prev);
    }

    /**
     * Utility method that formats the supplied number of nanoseconds into milliseconds, seconds and minutes.
     *
     * @param nanos Number of nanoseconds to format.
     * @return Formatted string representing the number of nanoseconds.
     */
    public static String getFormattedNanoseconds(long nanos) {
        float rawMs = nanos / 1000000f;

        float ms = (rawMs % 1000);

        rawMs /= 1000;
        int s = (int) (rawMs % 60);

        rawMs /= 60;
        int m = (int) (rawMs % 60);

        return m + "M " + s + "S " + ms + "MS";
    }
}