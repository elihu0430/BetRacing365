package betracing365;

/**
 * Utility class containing the Bubble Sort Version 3 algorithm.
 * Operates on HorseJockeyPair arrays and produces step-by-step logs of its operations.
 */
public class Sorter {

    public interface LogCallback {
        void onStep(String message);
    }

    /**
     * Helper to compare two pairs based on the target field and sort direction.
     */
    private static int compare(HorseJockeyPair p1, HorseJockeyPair p2, boolean sortByHorse, boolean ascending) {
        String val1 = sortByHorse ? p1.getHorseName() : p1.getJockeyName();
        String val2 = sortByHorse ? p2.getHorseName() : p2.getJockeyName();
        int cmp = val1.compareToIgnoreCase(val2);
        return ascending ? cmp : -cmp;
    }

    /**
     * BUBBLE SORT - VERSION 3
     * 
     * Optimizations:
     * 1. Boolean flag check: Stops early if a pass completes with zero swaps (array is already sorted).
     * 2. Last Swap Tracking: Tracks the index of the last swapped element. Because elements 
     *    beyond the last swap are guaranteed to be sorted, the outer loop boundary shrinks 
     *    directly to the last swap index, avoiding redundant comparisons.
     */
    public static void bubbleSortV3(HorseJockeyPair[] array, boolean sortByHorse, boolean ascending, LogCallback callback) {
        int n = array.length;
        callback.onStep("Starting Bubble Sort Version 3 (Optimized with Last Swap Index).");
        callback.onStep("Target: " + (sortByHorse ? "Horse" : "Jockey") + " Names, Order: " + (ascending ? "Ascending" : "Descending") + ".");
        
        int pass = 1;
        while (n > 1) {
            int newn = 0;
            callback.onStep("--- Pass " + pass + " (Scanning up to index " + (n - 1) + ") ---");
            for (int i = 1; i < n; i++) {
                String name1 = sortByHorse ? array[i - 1].getHorseName() : array[i - 1].getJockeyName();
                String name2 = sortByHorse ? array[i].getHorseName() : array[i].getJockeyName();
                
                callback.onStep(String.format("Comparing index %d ('%s') and index %d ('%s')", i - 1, name1, i, name2));
                
                if (compare(array[i - 1], array[i], sortByHorse, ascending) > 0) {
                    callback.onStep(String.format("  -> Swap: '%s' and '%s' are out of order.", name1, name2));
                    HorseJockeyPair temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    newn = i; // track the position of the last swap
                }
            }
            if (newn == 0) {
                callback.onStep("No swaps occurred in this pass. Array is fully sorted!");
                break;
            }
            callback.onStep(String.format("Pass %d complete. Last swap was at index %d. Next pass will only scan up to index %d.", pass, newn, newn - 1));
            n = newn; // shrink the array size for the next pass
            pass++;
        }
        callback.onStep("Bubble Sort Version 3 completed successfully.");
    }
}
