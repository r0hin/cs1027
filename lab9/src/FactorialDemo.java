
import java.io.*;

/**
 * Class FactorialDemo demonstrates iterative vs recursive methods that
 * return the value of n! (Factorial). 
 * @author CS1027a
 */
public class FactorialDemo {

    // Counts the number of calls to a given method
    private static long methodCalls;

    /** Method to calculate n Factorial recursively.
     * @param n an integer >= 1
     * @return value of n!
     * precondition: n >= 115
     */
    public static long rfact(long n) {

        methodCalls++;

        if (n == 1) {
            return 1;
        } else {
            return n * rfact(n - 1);
        }
    }

    /** Method to calculate n Factorial iteratively.
     * @param n an integer >= 1
     * @return nth value of n!
     * precondition: n >= 1
     */
    public static long ifact(long n) {

        methodCalls++;

        if (n == 1) {
            return 1;
        } else {
            long product = 1;
            for (int i = 1; i <= n; i++) {
            	product = product * i;
            }
            return product;
        }
    }

    /** Main method to call the recursive and iterative Factorial methods.
     */
    public static void main(String args[]) throws Exception {

        long startTime;     // The start time of a method call
        long elapsed;       // The execution time of a method call
        long n;             // The Factorial number to compute

        System.out.println("Enter an integer n (enter the number 0 to quit): ");

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
        System.out.print("Enter an integer: ");
        n = Integer.parseInt(keyboard.readLine());

        while (n > 0) {

            // Call the iterative Factorial method and measure the elapsed time and
            // the number of method calls
            methodCalls = 0;
            startTime = System.currentTimeMillis();
            System.out.println("\nIterative Factorial: the value of "+n+"! (Factorial) is " + ifact(n));
            elapsed = (System.currentTimeMillis() - startTime);
            System.out.println("Elapsed time: " + elapsed + "ms; number of calls to the algorithm: " + methodCalls);

            // Call the recursive Factorial method and measure the elapsed time and
            // the number of method calls
            methodCalls = 0;
            startTime = System.currentTimeMillis();
            System.out.println("Recursive Factorial: the value of "+n+"! (Factorial) is "+rfact(n));
            elapsed = (System.currentTimeMillis() - startTime);
            System.out.println("Elapsed time: " + elapsed + "ms; number of calls to the algorithm: " + methodCalls);

            System.out.print("\nEnter an integer: ");
            n = Integer.parseInt(keyboard.readLine());
        }

        System.out.println("End of program");
    }
}