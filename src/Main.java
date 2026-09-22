import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        Drone[] copy = drones.clone();

        Arrays.sort(copy, Comparator.comparingInt(d -> d.x));

        return closest(copy, 0, copy.length);
    }

    /**
     * Closest pair among sorted[from] .. sorted[to - 1], where sorted is already sorted by x.
     * Everything below is squared: delta is a squared distance, so compare dx*dx and dy*dy with it.
     */
    private static Drone[] closest(Drone[] sorted, int from, int to) {
        int n = to - from;

        // BASE CASE:
        if (to - from <= 3) {
            Drone[] small = Arrays.copyOfRange(sorted, from, to);
            return findClosestPairBruteForce(small);
        }

        // DIVIDE: split in the middle BY INDEX; midX is the x of the middle drone
        int mid = from + (to - from) / 2;
        int midX = sorted[mid].x;

        Drone[] leftPair = closest(sorted, from, mid);
        Drone[] rightPair = closest(sorted, mid, to);

        // RECURSIVE CASE: solve the left half and the right half, keep the better pair.
        //                 delta = the smaller of the two squared distances.
        long leftDx = (long) leftPair[0].x - leftPair[1].x;
        long leftDy = (long) leftPair[0].y - leftPair[1].y;
        long leftDistance = leftDx * leftDx + leftDy * leftDy;

        long rightDx = (long) rightPair[0].x - rightPair[1].x;
        long rightDy = (long) rightPair[0].y - rightPair[1].y;
        long rightDistance = rightDx * rightDx + rightDy * rightDy;

        Drone[] bestPair;
        long delta;

        if (leftDistance <= rightDistance) {
            bestPair = leftPair;
            delta = leftDistance;
        } else {
            bestPair = rightPair;
            delta = rightDistance;
        }

        // COMBINE: the closest pair may have one drone on each side.
        //          1) strip = the drones with (x - midX) * (x - midX) < delta
        //          2) sort the strip by y
        //          3) for each drone in the strip, compare it with the next ones and
        //             stop as soon as (y difference) * (y difference) >= delta
        List<Drone> strip = new ArrayList<>();

        for (int i = from; i < to; i++) {
            long dx = (long) sorted[i].x - midX;

            if (dx * dx < delta) {
                strip.add(sorted[i]);
            }
        }

        strip.sort(Comparator.comparingInt(d -> d.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size(); j++) {

                long dy = (long) strip.get(j).y - strip.get(i).y;

                if (dy * dy >= delta) {
                    break;
                }

                long distance = Drone.distSq(strip.get(i), strip.get(j));

                if (distance < delta) {
                    delta = distance;
                    bestPair = new Drone[]{
                            strip.get(i),
                            strip.get(j)
                    };
                }
            }
        }

        return bestPair;
    }

    // ======================== Part C: Safety limit ========================

    /**
     * Drones join the show one by one in order of id: #1, #2, #3, ...
     * Returns the smallest N such that the first N drones already have a pair
     * closer than Drone.SAFE_DISTANCE_CM.
     */
    static int findFirstUnsafeN(Drone[] drones) {
        int low = 2;
        int high = drones.length;

        long safeDistanceSq =
                (long) Drone.SAFE_DISTANCE_CM * Drone.SAFE_DISTANCE_CM;

        while (low < high) {
            int mid = low + (high - low) / 2;

            Drone[] current = Arrays.copyOfRange(drones, 0, mid);

            Drone[] pair = findClosestPairDivideAndConquer(current);

            long dx = (long) pair[0].x - pair[1].x;
            long dy = (long) pair[0].y - pair[1].y;
            long distance = dx * dx + dy * dy;

            if (distance < safeDistanceSq) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
}
