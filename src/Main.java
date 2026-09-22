import java.util.Arrays;
import java.util.Comparator;

/**
 * SkyShow Collision Alert - Assignment 1.
 * This is the only file you change.
 */
public class Main {

    static final String BARCODE = "251567";   // <-- put your student barcode here

    public static void main(String[] args) {
        // Runs your methods, checks them and prints the results.
        Checker.run(BARCODE, Main::findClosestPairBruteForce, Main::findClosestPairDivideAndConquer, Main::findFirstUnsafeN);
    }

    // ======================== Part A: Brute Force ========================

    /** Returns the two closest drones as new Drone[] {a, b}. */
    static Drone[] findClosestPairBruteForce(Drone[] drones) {
        long bestDistance = Long.MAX_VALUE;

        Drone first = drones[0];
        Drone second = drones[1];

        for (int i = 0; i < drones.length; i++) {
            for (int j = i + 1; j < drones.length; j++) {

                long distance = Drone.distSq(drones[i], drones[j]);

                if (distance < bestDistance) {
                    bestDistance = distance;
                    first = drones[i];
                    second = drones[j];
                }
            }
        }

        return new Drone[]{first, second};
    }

    // ====================== Part B: Divide & Conquer ======================

    /** Returns the two closest drones as new Drone[] {a, b}. */
    static Drone[] findClosestPairDivideAndConquer(Drone[] drones) {
        // TODO: copy the array (drones.clone()), sort the copy by x, then call closest(...).
        //       Sorting by x:  Arrays.sort(copy, Comparator.comparingInt(d -> d.x));
        throw new UnsupportedOperationException("Part B (Divide & Conquer) is not written yet");
    }

    /**
     * Closest pair among sorted[from] .. sorted[to - 1], where sorted is already sorted by x.
     * Everything below is squared: delta is a squared distance, so compare dx*dx and dy*dy with it.
     */
    private static Drone[] closest(Drone[] sorted, int from, int to) {
        int n = to - from;

        // BASE CASE: 3 drones or fewer - just check all pairs
        // TODO

        // DIVIDE: split in the middle BY INDEX; midX is the x of the middle drone
        // TODO

        // RECURSIVE CASE: solve the left half and the right half, keep the better pair.
        //                 delta = the smaller of the two squared distances.
        // TODO

        // COMBINE: the closest pair may have one drone on each side.
        //          1) strip = the drones with (x - midX) * (x - midX) < delta
        //          2) sort the strip by y
        //          3) for each drone in the strip, compare it with the next ones and
        //             stop as soon as (y difference) * (y difference) >= delta
        // TODO

        throw new UnsupportedOperationException("Part B (Divide & Conquer) is not written yet");
    }

    // ======================== Part C: Safety limit ========================

    /**
     * Drones join the show one by one in order of id: #1, #2, #3, ...
     * Returns the smallest N such that the first N drones already have a pair
     * closer than Drone.SAFE_DISTANCE_CM.
     */
    static int findFirstUnsafeN(Drone[] drones) {
        // TODO: binary search on N, exactly like binary search in an array.
        //       low = 2, high = drones.length.
        //       For a middle N: take Arrays.copyOfRange(drones, 0, mid), run your Part B on it,
        //       and ask whether that pair is closer than SAFE_DISTANCE_CM (compare SQUARED values).
        //       Unsafe -> the answer is mid or smaller. Safe -> the answer is bigger.
        throw new UnsupportedOperationException("Part C (Safety limit) is not written yet");
    }
}
